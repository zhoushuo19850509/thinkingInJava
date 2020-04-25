package com.nbcb.thinkingInJava.concurrency.coperatingtasks;


/**
 * 这个文件代码，主要是通过生产者、消费者模型，说明进程间是如何通信的
 * 关键字是notifyAll()
 *
 * 消费者、生产者模型的运行原理是这样的：
 * 生产者(Chef)(作为单独的线程)不停地做菜(new Meal对象)
 * 消费者(waitPerson)(作为单独的线程)不停地把菜端走(Meal对象置为null)
 *
 * 后续我们用到这个模型的时候，要特别注意
 */

import com.sun.xml.internal.ws.util.ReadAllStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 当前的点餐信息
 */
class Meal{

    /**
     * orderNum
     * 当前是第几道菜
     */
    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "orderNum=" + orderNum +
                '}';
    }
}

/**
 * 服务员
 */
class WaitPerson implements Runnable{
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){

                /**
                 * 对于waitPerson来说，如果meal没有准备好，就一直等着
                 */
                synchronized (this){
                    while(restaurant.meal == null){
                        wait();
                    }
                }

                /**
                 * 一旦跳出while循环，说明Meal准备好了！
                 */
                System.out.println("WaitPerson is Consuming the meal : " + restaurant.meal);

                /**
                 * 下面waitPerson开始处理Meal
                 * 消费Meal成功后，将restaurant.meal对象置为空
                 * 处理完成后，通知Chef可以开始下一道菜了
                 */
                synchronized (restaurant.chef){
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }


            }
        }catch (InterruptedException e){
            System.out.println("wait person interrupted");
        }

    }
}

/**
 * 大厨
 */
class Chef implements Runnable{
    private Restaurant restaurant;

    /**
     * 这个count的意思是，Chef一天最多能做10道菜。
     * 一旦做满了10道菜，Chef的任务就算完成了，餐厅没有多余的材料可以做菜了
     */
    private int count = 0;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){

                /**
                 * 如果当前这道菜还没有被WaitPerson取走，
                 * 那么Chef就一直等着，不做下一道菜
                 */
                synchronized (this){
                    while(restaurant.meal != null){
                        wait();
                    }
                }

                /**
                 * 一旦做满10道菜，餐厅就关闭了
                 */
                if(++count == Restaurant.MAX_COUNT){
                    System.out.println("out of the food , closing the restaurant");
                    restaurant.exec.shutdownNow();
                }

                /**
                 * 然后开始做菜，并通知waitPerson来取
                 */
                System.out.println("Chef is producing the meal!");
                synchronized (restaurant.waitPerson){
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }

                /**
                 * 做完一道菜，Chef就休息一会儿
                 */
                Thread.sleep(1000);

            }


        } catch (InterruptedException e) {
            System.out.println("order up ! chef finish today's task!");
        }


    }
}


/**
 * 餐厅
 * 餐厅类的实现很简单，就是new一个WaitPerson对象和一个Chef对象，然后从线程池捞出可用的线程资源
 * 分别启动WaitPerson线程和一个Chef线程
 */
public class Restaurant {

    /**
     * 这个餐厅一天最多能做几道菜
     */
    public final static int MAX_COUNT = 10;

    /**
     * 当前正在做的一道菜
     */
    Meal meal;

    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(waitPerson);
        exec.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
