package com.nbcb.thinkingInJava.concurrency.coperatingtasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * ���������Ҫ�ǽ������������������������������߳�
 * ˵���̼߳����ͨ�š��ؼ�����notifyAll()
 * ��Ϊ��ʱ���߳�֮��ִ�е�ʱ�������Ⱥ�˳��
 * ���������Ҳ��һ����WaxOff����������̣߳�������WaxOn�߳�ִ����ɺ����ִ��
 *
 * ����״̬λ
 * wax ����
 * buff ��������
 *
 * ��������
 * waxed() --�������
 * buffed() --�������
 * waitingForWaxed() --�ȴ�����
 * waitingForBuffed()  --�ȴ�����
 *
 */


class Car{
    private boolean waxOn = false;

    /**
     * ������ϵĶ���
     * ��waxOn����Ϊtrue
     * ͬʱ�����״̬֪ͨ���е��߳�
     */
    public synchronized void waxed(){
        waxOn = true;
        notifyAll();
    }

    /**
     * ������ϵĶ���
     * ��waxOff����Ϊfalse
     * ͬʱ�����״̬֪ͨ���е��߳�
     */
    public synchronized void buffed(){
        waxOn = false;
        notifyAll();
    }

    /**
     * WaxOff�ȴ�WaxOn�������
     * @throws InterruptedException
     */
    public synchronized void waitingFofWaxed() throws InterruptedException {
        while(waxOn == false){
            wait();
        }
    }

    /**
     * WaxOn��ɴ����󣬵ȴ�WaxOff�������
     * @throws InterruptedException
     */
    public synchronized void waitingForBuffed() throws InterruptedException {
        while(waxOn == true){
            wait();
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


public class WaxOMatic {

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
