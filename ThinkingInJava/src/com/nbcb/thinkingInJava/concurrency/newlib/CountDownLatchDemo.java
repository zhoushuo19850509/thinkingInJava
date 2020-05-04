package com.nbcb.thinkingInJava.concurrency.newlib;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ����ļ���Ҫ��Ϊ��˵��CountDownLatch������ڲ��������
 * CountDownLatch��ɶ��˼�أ�
 *
 * ����������һ��������
 * ����A������B
 * ����A�кܶ�������̣߳�����BҲ�кܶ�������߳�
 * ����Ҫ������B���̱߳���ȴ�����Aȫ������󣬲��ܿ�ʼ��
 *
 * CountDownLatch���ǽ����������ģ�
 * ��������A��100�������̣߳���ôCountDownLatch�ͻ�ά��һ��������100
 * ����Aÿ���һ���̣߳��ͻ�ʹ��CountDownLatch������һ��ֱ��CountDownLatch��������0
 * ����B���ܿ�ʼִ��
 *
 */

/**
 * �����ģ����Ҫ�������߳�
 */
class TaskPortion implements Runnable{
    private CountDownLatch countDownLatch;
    private static Random random = new Random(47);
    private static int counter = 0;
    private static final int id = counter++;  // ���id��ʶ��ǰ����߳�

    public TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            /**
             * �ȸɻ�
             */
            doWork();

            /**
             * �����ͽ�CountDownLatch������1
             */
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * �������ģ��������ɻ�
     * ��ʵ����Sleepһ��ʱ��
     */
    public void doWork() throws InterruptedException {
        int sleepTime = random.nextInt(2000);
        Thread.sleep(sleepTime);
        System.out.println(this + " complete and sleep for : " + sleepTime + " ms" );
    }

    @Override
    public String toString() {
        return "TaskPortion{" +
                "id=" + this.id +
                '}';
    }
}

/**
 * �����ģ��ȴ����߳�
 * ��Ҫ�ȴ�TaskPortion�����ſ�ʼ��
 */
class WaitingTask implements Runnable{
    private static int counter = 0;
    private static final int id = counter++;  // ���id��ʶ��ǰ����߳�
    private CountDownLatch countDownLatch;

    public WaitingTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println("WaitingTask get control: " + this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "WaitingTask{" +
                "id=" + this.id +
                '}';
    }
}


public class CountDownLatchDemo {
    public static void main(String[] args) {

        /**
         * ��Ҫ������TaskPortion������
         */
        final int SIZE = 100;

        /**
         * ����һ��CountDownLatchʵ����
         * count����������Ҫ������TaskPortion��������һ��
         */
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        /**
         * ������10��WaitingTask
         * ��Ȼ��������WaitingTask������������WaitingTaskҲֻ�ܵȴ�
         * ֱ��������TaskPortionȫ��������ܿ�ʼ����������
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(countDownLatch));
        }

        /**
         * Ȼ��������100��������TaskPortion
         */
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(countDownLatch));
        }



    }
}
