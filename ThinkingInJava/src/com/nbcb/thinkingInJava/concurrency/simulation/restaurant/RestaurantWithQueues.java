package com.nbcb.thinkingInJava.concurrency.simulation.restaurant;


/**
 * 这个代码综合了之前提到的各种Concurrency相关的类
 * 比如BlockingQueue，用于各个类之间的通信
 *
 * @业务场景：
 * 模拟一个餐厅的实际场景
 *
 * @各个主要的类说明
 * 1.Customer
 *   下订单、吃菜
 * 2.WaitPerson
 *   提交订单、把做好的菜交给Customer
 * 3.Chef
 *   取出订单、做菜、把做完的菜交给WaitPerson
 * 4.Restaurant
 *   创建Customer，分配WaitPerson
 *
 * 备注以上这些类都实现了Runnable接口，都是异步线程
 *
 */


import com.nbcb.thinkingInJava.enumeratedTypes.menu.Course;
import com.nbcb.thinkingInJava.enumeratedTypes.menu.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Order 客户下的订单
 */
class Order{

    private static int counter = 0;
    private final int id = counter++;

    private Customer customer;  // 指定哪个客户下的订单
    private WaitPerson waitPerson;  // 这个订单交给哪个waiter
    private Food food;  // 这个订单点的食物是什么

    public Order(Customer customer, WaitPerson waitPerson, Food food) {
        this.customer = customer;
        this.waitPerson = waitPerson;
        this.food = food;
    }

    // getters
    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", waitPerson=" + waitPerson +
                ", food=" + food +
                '}';
    }
}



/**
 * Plate 厨师做完一道菜之后，以Plate的形式提供给客户食用
 */
class Plate{
    // 这道菜对应的是哪个订单
    private final Order order;
    // 厨师做完的菜
    private final Food food;

    public Plate(Order order, Food food) {
        this.order = order;
        this.food = food;
    }

    // getters
    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "food=" + food +
                '}';
    }
}

/**
 * Customer 客户
 */
class Customer implements Runnable{

    private static int counter = 0;
    private final int id = counter++;

    // 会给每个客户分配一个waiter，专门服务这个客户
    private final WaitPerson waitPerson;

    /**
     * 这个客户点的菜如果做好了，就放到placeSetting这个队列中，供客户食用
     *
     * 那么SynchronousQueue是啥意思呢？
     * 首先，从字面理解，这个队列是线程安全的。但是，这个类虽然名字是queue队列
     * 但是这个队列的容量是0，也就是说，这个队列啥元素都没法保存
     * 每次调用队列的take()方法，必须wait for put()方法完成后才能执行；
     * 反过来也是一样：每次调用队列的put()方法，必须wait for take()方法完成后才能执行
     *
     * 这就相当于一个中转站，用于不同线程之间(比如Producer/Consumer)交换元素，
     * 这就有点像是cyclic barrier。具体用法请参考：SynchronousQueueTest.java
     *
     * 在Restaraunt这个场景中，我们使用SynchronousQueue想要实现的效果就是：
     * placeSetting就相当于Customer前面那个唯一的餐盘，
     * 1.WaitPerson要往餐盘里放做好的菜(调用placeSetting.put())，
     *   必须等Customer把餐盘里现有的菜吃完(placeSetting.take())
     * 2.反过来也一样，Customer要开始吃餐盘里现有的菜(placeSetting.take())，
     *   也必须等WaitPerson往餐盘里放做好的菜(调用placeSetting.put())
     *
     * 总而言之，SynchronousQueue保证了一个Customer前面只有一个餐盘
     */
    private SynchronousQueue<Plate> placeSetting =
            new SynchronousQueue();

    public Customer(WaitPerson waitPerson) {
        this.waitPerson = waitPerson;
    }

    /**
     * 这个客户点的菜如果做好了，就会调用这个方法，
     * 把这个做好的菜交给这个客户
     * @param plate
     */
    public void deliver(Plate plate){
        try {
            this.placeSetting.put(plate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Customer的异步线程，要做的事情就是
     * 1.点单：随机挑选要点的食物，吩咐waiter去做
     * 2.等待厨师制作食物
     * 3.等食物制作好以后，然后开始吃这份食物
     * 4.吃完之后才能再点下一个食物
     *
     * 备注：从demo程序来看，对于某个客户来说，只能是点一道，吃一道，吃完之后再点
     * 不能一次性点很多个食物
     */
    @Override
    public void run() {
        /**
         * 这个For循环的意思就是从前菜、主食、甜品、咖啡饮料
         *  (APPETIZER/MAINCOURCE/DESSERT/COFFEE)依次随机取出一个食物
         *  最终组成一道完整的大餐，具体用法参考Meal.java/Course.java
         */
        for(Course course : Course.values()){
            Food food = course.randomSelection();
            try {
                // 点单
                this.waitPerson.placeOrder(this, food);

                // 从准备好的食物中挑选一个开始吃
                System.out.println(this + " eating : " + this.placeSetting.take());
            } catch (InterruptedException e) {
                System.out.println(this + " waiting for "+ food +"Interrupted ..");
            }
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}

/**
 * WaitPerson Waiter
 */
class WaitPerson implements Runnable{

    private static int counter = 0;
    private final int id = counter++;

    /**
     * 餐厅厨师每做好一道菜，就把这道菜放到这个队列中
     * 后续waiting会从这个队列中取一道菜，分发给点这道菜的客户
     */
    BlockingQueue<Plate> filledOrders =
            new LinkedBlockingQueue<>();

    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * 客户调用waiter.placeOrder()的这个方法点单
     * @param customer
     * @param food
     */
    public void placeOrder(Customer customer, Food food){
        try {
            this.restaurant.orders.put(new Order(customer,this, food));
        } catch (InterruptedException e) {
            System.out.println(this + " place order Interrupted ... ");
        }
    }

    /**
     * Waiter异步线程做的事情，就是访问filledOrders队列，
     * 把厨师做好的菜一道一道分发给各个客户
     */
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                Plate plate = filledOrders.take();
                System.out.println(this + " received : " + plate +
                        " deliver to " + plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " Interrupted ... ");
        }
    }

    @Override
    public String toString() {
        return "WaitPerson{" +
                "id=" + id +
                '}';
    }
}

/**
 * 厨师
 */
class Chef implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private Random random = new Random(47);

    private final Restaurant restaurant;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * 这个异步线程的功能：从Restaurant中带处理的order队列中
     * 取order进行处理，处理完成后，放到一个队列中，等待waiter教到客户手上
     */
    @Override
    public void run() {

        try {
            while(!Thread.interrupted()){

                // 先访问Restaurant客户的点单队列
                Order order = restaurant.orders.take();

                // sleep一段随机的时间，模拟厨师正在做菜
                Thread.sleep(random.nextInt(500));

                // 做完菜之后，new一个Plate对象，交给负责该order的waiter
                Food food = order.getFood();
                Plate plate = new Plate(order, food);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " Interrupted ...");
        }
    }

    @Override
    public String toString() {
        return "Chef{" +
                "id=" + id +
                '}';
    }
}

/**
 * Restaurant 餐厅
 */
class Restaurant implements Runnable{
    private List<WaitPerson> waitPersonList = new ArrayList<>();
    private List<Chef> chefList = new ArrayList<>();
    private ExecutorService executorService;
    private Random random = new Random(47);

    /**
     * 这个队列保存所有客户下的点餐订单
     */
    BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    /**
     * constructor of Restaurant
     *
     * 初始化要做的事情：
     * 1.初始化一批waiter，等待给客户服务
     * 2.初始化一批chef，等着做菜
     * @param nwaitPerson
     * @param nChef
     * @param exec
     */
    public Restaurant(int nwaitPerson, int nChef, ExecutorService exec) {
        this.executorService = exec;
        for (int i = 0; i < nwaitPerson; i++) {
             WaitPerson waitPerson = new WaitPerson(this);
             this.waitPersonList.add(waitPerson);
             executorService.execute(waitPerson);
        }
        for (int i = 0; i < nChef; i++) {
             Chef chef = new Chef(this);
             this.chefList.add(chef);
             executorService.execute(chef);
        }
    }

    /**
     * Restaurant异步线程做的事情，就是模拟定期有新的客户进入餐厅
     * 然后分配一个随机的waiter来(一对一)服务这个客户
     */
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                WaitPerson waitPerson = waitPersonList.get(random.nextInt(waitPersonList.size()));

                Customer customer = new Customer(waitPerson);

                executorService.execute(customer);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant Interrupted ...");
        }
    }
}

/**
 * main class
 */
public class RestaurantWithQueues {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(5,2,executorService);
        executorService.execute(restaurant);

        // 等待n秒钟之后，关闭餐厅，所有相关对象的异步线程都中断
        Thread.sleep( 5 * 1000);
        executorService.shutdownNow();
    }

}
