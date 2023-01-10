package com.nbcb.thinkingInJava.concurrency.coperatingtasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * ���������Ҫ�ǽ������������������������������߳�
 * ˵���̼߳����ͨ�š��ؼ�����wait()/notifyAll()
 * ��Ϊ��ʱ���߳�֮��ִ�е�ʱ�������Ⱥ�˳��
 * ���������Ҳ��һ����WaxOff����������̣߳�������WaxOn�߳�ִ����ɺ����ִ��
 *
 * @ҵ�񳡾�
 * �������˵��ҵ�񳡾���������
 * �������������첽�̣߳�
 * WaxOn/WaxOff
 * ����WasOn�߳��Ǹ���������
 * WaxOff�߳��Ǹ������׹�
 *
 * �����첽�߳���Ȼ�Ƕ����ģ��������Ⱥ��ϵ��
 * ������ִ����WaxOn����������ִ��WaxOff�̸߳������׹�
 * WaxOff�߳�ִ����󣬲�ִ��WaxOn�߳�
 * ��������ѭ����ֱ�����߳��˳�
 *
 * @������
 * �������ļ��������wait()/notifyAll()
 * ����wait()�ǵȴ������̣߳�����ĳ���ֶ�
 * notifyAll()�ǵ�ǰ�̸߳���ĳ���ֶ�֮��֪ͨ�����߳�
 *
 * ����״̬λ
 * wax ����
 * buff ��������(�׹�)
 *
 * Car������Ҫ�ļ�������
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
                 * ����������ϣ�
                 * 1.����״̬λwaxOn
                 * 2.Ȼ�����notifyAll()����֪ͨ�����̣߳�waxOn״̬�Ѿ�����
                 */
                car.waxed();

                /**
                 * ����������ϣ��ȴ�����
                 *
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
                 * ����ǰ�������ȵȴ�����
                 * waitingFofWaxed()��Ҫ��ͨ������wait()����
                 * ����waxOn�ֶε�״̬��һ��waxOn�ֶ���Ϊtrue�������ͽ���
                 */
                car.waitingFofWaxed();
                System.out.println("Wax off�� ������ϣ�׼�����ã�");

                /**
                 * �̵߳ȴ�һ��ʱ�䣬ģ���������õĹ���
                 */
                Thread.sleep(1000);
                /**
                 * ����������ϣ�����״̬λ
                 * buffed()ִ�еĶ���������
                 * 1.��waxOn��Ϊfalse
                 * 2.����notifyAll()����֪ͨ�����̣߳�waxOn״̬��Ϊfalse�ˣ����Լ���������
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

/**
 * main class
 */
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
         * sleepһ��ʱ����̳߳عر�
         */
        Thread.sleep(5000);
        exec.shutdownNow();
    }

}
