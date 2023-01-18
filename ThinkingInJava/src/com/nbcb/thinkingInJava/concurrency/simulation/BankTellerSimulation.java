package com.nbcb.thinkingInJava.concurrency.simulation;


import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个代码，从总体来说就是模拟并发场景：银行柜员处理客户业务
 *
 * 有一个客户队列(CustomerLine)，队列中有若干个客户(Customer)在排队
 * 然后有若干个柜员(Teller)在处理客户业务
 * 同时，有一个运营经理(TellerManager)，针对当前客户队列的长度，对柜员的人数进行调整
 *
 * 后续优化：Teller的数量是固定的，不可能根据客户数量，无限增长
 */

/**
 * 排队来柜台办业务的客户
 */
class Customer{

    /**
     * 这个私有变量说明当前客户办理业务花费的时间
     */
    private final int serviceTime;

    public Customer(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "serviceTime=" + serviceTime +
                '}';
    }
}

/**
 * 维护一个客户排队的队列
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {

    /**
     * constructor
     * 指定ArrayBlockingQueue最大的长度
     * @param maxLineSize
     */
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    /**
     * toString()
     * 主要是打印ArrayBlockingQueue中各个元素(Customer)
     * @return
     */
    @Override
    public String toString() {

        if(this.size() == 0){
            return "[Empty line]";
        }else{
            StringBuilder sb = new StringBuilder();
            for(Customer customer : this){
                sb.append(customer.toString());
            }
            return "current customer size[" + this.size() + "]" +sb.toString();
        }
    }
}

/**
 * 这个类主要是启动一个单独的线程，往一个客户队列中，添加客户对象
 * 这个类模拟的场景是，客户随机走进网点，排队处理业务
 */
class CustomerGenerator implements Runnable{

    /**
     * 这个私有变量是当前客户队列
     */
    private CustomerLine customers;

    private static Random rand = new Random(47);

    public CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public void run() {

        try{
            while(!Thread.interrupted()){
                Thread.sleep(rand.nextInt(300));
                customers.add(new Customer(rand.nextInt(1000)));
            }
        }catch (InterruptedException e){
            System.out.println("CustomerGenerator interrupted! The bank is closing new !!!!!!!");
//            e.printStackTrace();
        }
    }
}



/**
 * 模拟柜员，就是能够持续为排队的客户服务
 */
class Teller implements Runnable , Comparable<Teller>{

    private static int counter = 0;
    private final int id = counter++;

    /**
     * 当前柜员要处理的客户队列
     */
    private CustomerLine customers;

    /**
     * (这一轮)处理了多少客户
     */
    private int customerServed = 0;

    /**
     * 当前柜员是否在处理客户
     */
    private boolean servingCustomerLine = true;


    /**
     * constructor
     * @param customers
     */
    public Teller(CustomerLine customers) {
        this.customers = customers;
    }


    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                /**
                 * 从客户队列中取出一个客户，并处理
                 */
                Customer customer = customers.take();
                Thread.sleep(customer.getServiceTime());

                synchronized (this){
                    customerServed++;
                    while(!servingCustomerLine){
                        wait();
                    }
                }

            }
        }catch (InterruptedException e){
            System.out.println("CustomerGenerator interrupted!");
            e.printStackTrace();
        }
        System.out.println(this + "Terminating");
    }


    /**
     * 当前柜员处理完这轮客户，没啥事情可做时，就调用这个方法
     */
    public synchronized void doSomethingElse(){
        /**
         * 把客户处理数清零
         */
        this.customerServed = 0;

        /**
         * "当前是否正在处理客户"的标志置为false
         */
        this.servingCustomerLine = false;
    }

    /**
     * 一旦当前柜员要开始处理客户，就调用这个方法
     */
    public synchronized void serveCustomerLine(){
        /**
         * "当前是否正在处理客户"的标志置为true
         */
        servingCustomerLine = true;
        /**
         * 通知所有的柜员线程
         */
        notifyAll();
    }


    @Override
    public String toString() {
        return "Teller{" +
                "id=" + id +
                '}';
    }

    public String shrotString() {
        return "T" +
                "id=" + id +
                '}';
    }

    /**
     * 主要是实现了Comparable接口
     * 根据本次处理的客户数(customerServed),和其他柜员进行比较，
     * @param o
     * @return
     */
    @Override
    public synchronized int compareTo(Teller o) {
        return this.customerServed < o.customerServed ? -1: (this.customerServed == o.customerServed? 0 : 1);
    }

}

/**
 * 柜员经理
 * 针对当前客户队列的长度，对柜员的人数进行调整
 */
class TellerManager implements Runnable{

    /**
     * 当前正在排队的客户
     */
    private CustomerLine customers;
    /**
     * 维护两个队列：
     * 队列1 当前正在工作的柜员的队列
     */
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();

    /**
     * 队列2 当前正在休息的柜员的队列
     */
    private Queue<Teller> tellerDoingOtherThings = new LinkedList<Teller>();

    /**
     * 每隔多久，调整一次(ms)
     */
    private int adjustmentPeriod;

    /**
     * 维护一个线程池
     * 这个线程池中的对象是Teller，有若干柜员正在干活
     */
    ExecutorService executorService;

    /**
     * constructor
     * @param customers
     * @param adjustmentPeriod
     * @param executorService
     */
    public TellerManager(CustomerLine customers, int adjustmentPeriod, ExecutorService executorService) {
        this.customers = customers;
        this.adjustmentPeriod = adjustmentPeriod;
        this.executorService = executorService;

        /**
         * 初始化的时候，默认最起码有一个柜员
         */
        Teller teller = new Teller(customers);
        executorService.execute(teller); // 马上放到线程池中开始干活
        workingTellers.add(teller);  // 放到工作柜员的队列中去
    }

    /**
     * 这个TellerManager调整柜员数量的核心方法
     * 从总体来说，调整的策略是这样的：
     * 如果排队的客户太多，就安排更多的柜员；
     * 如果排队的客户慢慢减少了，就安排更少的柜员(去做别的事)
     */
    public void adjustTellerNumber(){

        /**
         * 如果客户较多，先看看有没有正在干其他事的柜员，如果有的话，过来处理柜面的客户
         * 如果没有的话，就安排新的柜员处理业务
         */
        if(customers.size() / workingTellers.size() > 2){
            if(tellerDoingOtherThings.size() > 0){
                Teller teller = tellerDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller); // 类似于add()
                return;
            }
            Teller teller = new Teller(customers);
            executorService.execute(teller);
            workingTellers.add(teller);
            return;
        }

        /**
         * 如果客户较少就重新安排一个柜员任务(调离柜面，去处理其他事情)
         */
        if(workingTellers.size() > 1 &&
                customers.size() / workingTellers.size() < 2){
            reassignOneTeller();
        }

        /**
         * 如果现在没有客户，也只要安排一个柜员就行了
         */
        if(customers.size() == 0){
            while (workingTellers.size() > 1){
                reassignOneTeller();
            }
        }


    }

    /**
     * (因为当前排队的客户较少)重新安排一个柜员的任务：调离柜面，去处理其他事情
     */
    private void reassignOneTeller(){
        Teller teller = workingTellers.poll(); // get and delete the first element from the PriorityQueue
        teller.doSomethingElse();
        tellerDoingOtherThings.offer(teller);
    }

    /**
     * run()方法主要做的事情，就是每隔一定时间，调用一下adjustTellerNumber
     * 根据排队情况调整柜员数量，然后输出当前客户队列的情况、柜员的情况
     */
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Thread.sleep(adjustmentPeriod);
                adjustTellerNumber();
                /**
                 * 调整完后，打印当前在柜面处理客户业务的柜员
                 */
                System.out.println(customers + "{");
                for(Teller teller: workingTellers){
                    System.out.println(teller.shrotString());
                }
                System.out.println("}");
            }
        }catch (InterruptedException e){
            System.out.println("CustomerGenerator interrupted!");
            e.printStackTrace();
        }
        System.out.println(this + "terminating");
    }

    @Override
    public String toString() {
        return "TellerManager{}";
    }
}


/**
 * 模拟程序入口
 *
 */
public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50; // 最多接纳50个客户
    static final int ADJUSTMENT_PERIOD = 1000; // 每隔1秒钟，TellerManager多会重新调整柜员数量
    public static void main(String[] args) throws InterruptedException {

        /**
         * 线程池1 这个线程池专门用来创建客户
         */
        ExecutorService executorServiceCustomer = Executors.newCachedThreadPool();

        /**
         * 线程池2 这个线程专门用来管理Teller
         */
        ExecutorService executorServiceTeller = Executors.newCachedThreadPool();

        /**
         * 一开始创建一个(空的)客户排队队列数据结构
         */
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);

        /**
         * 然后启动一个单独的线程，通过CustomerGenerator，模拟客户随机走进网点办理业务
         */
        executorServiceCustomer.execute(new CustomerGenerator(customers));

        /**
         * 然后启动一个单独的线程，TellerManager开始根据排队的客户数量，调整柜员数量
         */
        executorServiceTeller.execute(new TellerManager(customers,ADJUSTMENT_PERIOD,executorServiceTeller));


        /**
         * 过一段时间，比如10秒钟，就不再接纳新的客户了
         * 模拟下班时间快到了
         */
        Thread.sleep(15 * 1000);
        System.out.println("The bank is closing now >>>>>>>>>>>>>>>>>>>>>>>>>>");
        executorServiceCustomer.shutdownNow();

    }
}
