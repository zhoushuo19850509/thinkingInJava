package com.nbcb.thinkingInJava.concurrency.coperatingtasks.waxomatic2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WaxOMatic2.java��Ҫ����WaxOMatic.java�����Ͻ������Ż�
 * ��Car��������Щʹ����synchronized�ؼ��ֵķ�����
 * ��Ϊ1.Lock���м������� 2.condition�����̵߳ȴ�����;3.finally�м��������ͷ�
 * �����ĺô����Զ��׼��ġ����ǲ��ö�������������ͬ���ˡ�
 * ֻ��Ҫ����Ҫͬ���Ĵ����(��ͬ�̻߳�ͬʱ����ı�waxOn)����������
 */


class Car{
    private boolean waxOn = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * ������ϵĶ���
     * ��waxOn����Ϊtrue
     * ͬʱ�����״̬֪ͨ���е��߳�
     */
    public void waxed(){
        lock.lock();
        try{
            waxOn = true;
            condition.signal();
        }finally {
            lock.unlock();
        }

    }

    /**
     * ������ϵĶ���
     * ��waxOff����Ϊfalse
     * ͬʱ�����״̬֪ͨ���е��߳�
     */
    public void buffed(){
        lock.lock();
        try{
            waxOn = false;
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * WaxOff�ȴ�WaxOn�������
     * @throws InterruptedException
     */
    public void waitingFofWaxed() throws InterruptedException {
        lock.lock();
        try{
            while(waxOn == false){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * WaxOn��ɴ����󣬵ȴ�WaxOff�������
     * @throws InterruptedException
     */
    public void waitingForBuffed() throws InterruptedException {
        lock.lock();
        try{
            while(waxOn == true){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }
}


/**
 * �������̣߳�����̸߳������������
 */
class WaxOn implements Runnable{
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                System.out.println("Wax on: ������������");

                /**
                 * �̵߳ȴ�һ��ʱ�䣬ģ�����������Ĺ���
                 */
                Thread.sleep(1000);

                /**
                 * ����������ϣ�����״̬λ
                 */
                car.waxed();

                /**
                 * ����������ϣ��ȴ�����
                 */
                car.waitingForBuffed();

            }
        } catch (InterruptedException e) {
//                e.printStackTrace();
            System.out.println("interrupted while wax on !");
        }

        System.out.println("finish wax on");
    }
}


/**
 * �������̣߳�����̸߳������������
 * �ر�ע�⣬����̱߳���ȴ�WaxOn��ɲ��ú����ִ��
 */
class WaxOff implements Runnable{
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {

            while(!Thread.interrupted()){
                /**
                 * ����Ǯ�������ȵȴ�����
                 */
                car.waitingFofWaxed();
                System.out.println("Wax off�� ������ϣ�׼�����ã�");

                /**
                 * �̵߳ȴ�һ��ʱ�䣬ģ���������õĹ���
                 */
                Thread.sleep(1000);
                /**
                 * ����������ϣ�����״̬λ
                 */
                car.buffed();
            }
        } catch (InterruptedException e) {
//                e.printStackTrace();
            System.out.println("interrupted while wax off !");
        }

        System.out.println("finish wax off");
    }
}


public class WaxOMatic2 {

    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        WaxOn waxOn = new WaxOn(car);
        WaxOff waxOff = new WaxOff(car);

        /**
         * ���������������߳�
         */
        exec.execute(waxOff);
        exec.execute(waxOn);

        /**
         * һ��ʱ����̳߳عر�
         */
        Thread.sleep(5000);
        exec.shutdownNow();

    }

}
