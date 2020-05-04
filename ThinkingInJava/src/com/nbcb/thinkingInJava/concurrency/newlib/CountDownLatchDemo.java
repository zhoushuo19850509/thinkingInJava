package com.nbcb.thinkingInJava.concurrency.newlib;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 这个文件主要是为了说明CountDownLatch这个用于并发的类库
 * CountDownLatch是啥意思呢？
 *
 * 现在有这样一个场景：
 * 任务A和任务B
 * 任务A有很多独立的线程，任务B也有很多独立的线程
 * 现在要求任务B的线程必须等待任务A全部做完后，才能开始做
 *
 * CountDownLatch就是解决这个场景的：
 * 比如任务A有100个独立线程，那么CountDownLatch就会维持一个计数：100
 * 任务A每完成一个线程，就会使得CountDownLatch计数减一，直到CountDownLatch计数减到0
 * 任务B才能开始执行
 *
 */

/**
 * 这个类模拟主要的任务线程
 */
class TaskPortion implements Runnable{
    private CountDownLatch countDownLatch;
    private static Random random = new Random(47);
    private static int counter = 0;
    private static final int id = counter++;  // 这个id标识当前这个线程

    public TaskPortion(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            /**
             * 先干活
             */
            doWork();

            /**
             * 干完活，就将CountDownLatch计数减1
             */
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这个方法模拟主任务干活
     * 其实就是Sleep一段时间
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
 * 这个类模拟等待的线程
 * 需要等待TaskPortion做完后才开始做
 */
class WaitingTask implements Runnable{
    private static int counter = 0;
    private static final int id = counter++;  // 这个id标识当前这个线程
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
         * 需要启动的TaskPortion任务数
         */
        final int SIZE = 100;

        /**
         * 定义一个CountDownLatch实例，
         * count数量和我们要启动的TaskPortion任务数量一致
         */
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);

        /**
         * 先启动10个WaitingTask
         * 虽然看起来是WaitingTask先启动，但是WaitingTask也只能等待
         * 直到主任务TaskPortion全部做完才能开始真正做事情
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(countDownLatch));
        }

        /**
         * 然后再启动100个主任务：TaskPortion
         */
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(countDownLatch));
        }



    }
}
