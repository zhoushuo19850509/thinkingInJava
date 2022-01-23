package com.nbcb.thinkingInJava.concurrency.newlib;


import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ������룬��������˵����ģ�Ⲣ�����������й�Ա����ͻ�ҵ��
 *
 * ��һ���ͻ�����(CustomerLine)�������������ɸ��ͻ�(Customer)���Ŷ�
 * Ȼ�������ɸ���Ա(Teller)�ڴ���ͻ�ҵ��
 * ͬʱ����һ����Ӫ����(TellerManager)����Ե�ǰ�ͻ����еĳ��ȣ��Թ�Ա���������е���
 *
 * �����Ż���Teller�������ǹ̶��ģ������ܸ��ݿͻ���������������
 */

/**
 * �Ŷ�����̨��ҵ��Ŀͻ�
 */
class Customer{

    /**
     * ���˽�б���˵����ǰ�ͻ�����ҵ�񻨷ѵ�ʱ��
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
 * ά��һ���ͻ��ŶӵĶ���
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {

    /**
     * constructor
     * ָ��ArrayBlockingQueue���ĳ���
     * @param maxLineSize
     */
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    /**
     * toString()
     * ��Ҫ�Ǵ�ӡArrayBlockingQueue�и���Ԫ��(Customer)
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
 * �������Ҫ������һ���������̣߳���һ���ͻ������У���ӿͻ�����
 * �����ģ��ĳ����ǣ��ͻ�����߽����㣬�ŶӴ���ҵ��
 */
class CustomerGenerator implements Runnable{

    /**
     * ���˽�б����ǵ�ǰ�ͻ�����
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
 * ģ���Ա�������ܹ�����Ϊ�ŶӵĿͻ�����
 */
class Teller implements Runnable , Comparable<Teller>{

    private static int counter = 0;
    private final int id = counter++;

    /**
     * ��ǰ��ԱҪ����Ŀͻ�����
     */
    private CustomerLine customers;

    /**
     * (��һ��)�����˶��ٿͻ�
     */
    private int customerServed = 0;

    /**
     * ��ǰ��Ա�Ƿ��ڴ���ͻ�
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
                 * �ӿͻ�������ȡ��һ���ͻ���������
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
     * ��ǰ��Ա���������ֿͻ���ûɶ�������ʱ���͵����������
     */
    public synchronized void doSomethingElse(){
        /**
         * �ѿͻ�����������
         */
        this.customerServed = 0;

        /**
         * "��ǰ�Ƿ����ڴ���ͻ�"�ı�־��Ϊfalse
         */
        this.servingCustomerLine = false;
    }

    /**
     * һ����ǰ��ԱҪ��ʼ����ͻ����͵����������
     */
    public synchronized void serveCustomerLine(){
        /**
         * "��ǰ�Ƿ����ڴ���ͻ�"�ı�־��Ϊtrue
         */
        servingCustomerLine = true;
        /**
         * ֪ͨ���еĹ�Ա�߳�
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
     * ��Ҫ��ʵ����Comparable�ӿ�
     * ���ݱ��δ���Ŀͻ���(customerServed),��������Ա���бȽϣ�
     * @param o
     * @return
     */
    @Override
    public synchronized int compareTo(Teller o) {
        return this.customerServed < o.customerServed ? -1: (this.customerServed == o.customerServed? 0 : 1);
    }

}

/**
 * ��Ա����
 * ��Ե�ǰ�ͻ����еĳ��ȣ��Թ�Ա���������е���
 */
class TellerManager implements Runnable{

    /**
     * ��ǰ�����ŶӵĿͻ�
     */
    private CustomerLine customers;
    /**
     * ά���������У�
     * ����1 ��ǰ���ڹ����Ĺ�Ա�Ķ���
     */
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<Teller>();

    /**
     * ����2 ��ǰ������Ϣ�Ĺ�Ա�Ķ���
     */
    private Queue<Teller> tellerDoingOtherThings = new LinkedList<Teller>();

    /**
     * ÿ����ã�����һ��(ms)
     */
    private int adjustmentPeriod;

    /**
     * ά��һ���̳߳�
     * ����̳߳��еĶ�����Teller�������ɹ�Ա���ڸɻ�
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
         * ��ʼ����ʱ��Ĭ����������һ����Ա
         */
        Teller teller = new Teller(customers);
        executorService.execute(teller); // ���Ϸŵ��̳߳��п�ʼ�ɻ�
        workingTellers.add(teller);  // �ŵ�������Ա�Ķ�����ȥ
    }

    /**
     * ���TellerManager������Ա�����ĺ��ķ���
     * ��������˵�������Ĳ����������ģ�
     * ����ŶӵĿͻ�̫�࣬�Ͱ��Ÿ���Ĺ�Ա��
     * ����ŶӵĿͻ����������ˣ��Ͱ��Ÿ��ٵĹ�Ա(ȥ�������)
     */
    public void adjustTellerNumber(){

        /**
         * ����ͻ��϶࣬�ȿ�����û�����ڸ������µĹ�Ա������еĻ��������������Ŀͻ�
         * ���û�еĻ����Ͱ����µĹ�Ա����ҵ��
         */
        if(customers.size() / workingTellers.size() > 2){
            if(tellerDoingOtherThings.size() > 0){
                Teller teller = tellerDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller); // ������add()
                return;
            }
            Teller teller = new Teller(customers);
            executorService.execute(teller);
            workingTellers.add(teller);
            return;
        }

        /**
         * ����ͻ����پ����°���һ����Ա����(������棬ȥ������������)
         */
        if(workingTellers.size() > 1 &&
                customers.size() / workingTellers.size() < 2){
            reassignOneTeller();
        }

        /**
         * �������û�пͻ���ҲֻҪ����һ����Ա������
         */
        if(customers.size() == 0){
            while (workingTellers.size() > 1){
                reassignOneTeller();
            }
        }


    }

    /**
     * (��Ϊ��ǰ�ŶӵĿͻ�����)���°���һ����Ա�����񣺵�����棬ȥ������������
     */
    private void reassignOneTeller(){
        Teller teller = workingTellers.poll(); // get and delete the first element from the PriorityQueue
        teller.doSomethingElse();
        tellerDoingOtherThings.offer(teller);
    }

    /**
     * run()������Ҫ�������飬����ÿ��һ��ʱ�䣬����һ��adjustTellerNumber
     * �����Ŷ����������Ա������Ȼ�������ǰ�ͻ����е��������Ա�����
     */
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Thread.sleep(adjustmentPeriod);
                adjustTellerNumber();
                /**
                 * ������󣬴�ӡ��ǰ�ڹ��洦��ͻ�ҵ��Ĺ�Ա
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
 * ģ��������
 *
 */
public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50; // ������50���ͻ�
    static final int ADJUSTMENT_PERIOD = 1000; // ÿ��1���ӣ�TellerManager������µ�����Ա����
    public static void main(String[] args) throws InterruptedException {

        /**
         * �̳߳�1 ����̳߳�ר�����������ͻ�
         */
        ExecutorService executorServiceCustomer = Executors.newCachedThreadPool();

        /**
         * �̳߳�2 ����߳�ר����������Teller
         */
        ExecutorService executorServiceTeller = Executors.newCachedThreadPool();

        /**
         * һ��ʼ����һ��(�յ�)�ͻ��ŶӶ������ݽṹ
         */
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);

        /**
         * Ȼ������һ���������̣߳�ͨ��CustomerGenerator��ģ��ͻ�����߽��������ҵ��
         */
        executorServiceCustomer.execute(new CustomerGenerator(customers));

        /**
         * Ȼ������һ���������̣߳�TellerManager��ʼ�����ŶӵĿͻ�������������Ա����
         */
        executorServiceTeller.execute(new TellerManager(customers,ADJUSTMENT_PERIOD,executorServiceTeller));


        /**
         * ��һ��ʱ�䣬����10���ӣ��Ͳ��ٽ����µĿͻ���
         * ģ���°�ʱ��쵽��
         */
        Thread.sleep(15 * 1000);
        System.out.println("The bank is closing now >>>>>>>>>>>>>>>>>>>>>>>>>>");
        executorServiceCustomer.shutdownNow();

    }
}
