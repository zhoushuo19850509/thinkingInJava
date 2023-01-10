package com.nbcb.thinkingInJava.concurrency.coperatingtasks;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 这个代码文件为了说明notify()和notifyAll()的区别
 *
 * 有两个异步线程Task1/Task2
 * 这两个异步线程各自拥有一个Blocker对象
 *
 * 1.我们启动5个Task1的异步线程
 * (1)这时，如果调用Task1.blocker.prod() 执行notify()方法
 * 效果是只会通知到一个Task1线程，这个线程就是初始化Blocker对象(static)的那个线程
 *
 * (2)如果调用Task1.blocker.prodAll() 执行notifyAll()方法
 * 效果是会通知到所有共享blocker对象的Task1线程
 *
 * 2.我们启动1个Task2的异步线程
 * 因为Task2线程只有一个，所以无论是调用Task2.blocker.prod()
 * 还是调用Task2.blocker.prodAll()，效果都一样
 *
 * 总结一下
 * notify()/notifyAll()的区别在于
 * 如果多个线程，共享一个static对象，然后这些线程都wait on the static object
 * 这时，如果调用notify()，就只会通知一个线程；
 * 如果调用notifyAll()，会通知所有线程，
 * 所以，从整体来看，notify()/notifyAll()的区别在于通知线程的范围有所不同
 *
 * 我们看到，wait()/notify()/notifyAll()还是比较复杂的.
 * 所有,后续java会有一系列语法糖，来解决各种并发场景，
 * 从而尽量避免开发人员手工调用wait()/notify()/notifyAll()
 */

class Blocker{

    /**
     * 方法1 wait for other thread ...
     */
    synchronized void waitingCall(){
        try{
            while(!Thread.interrupted()){
                wait();
                System.out.println(Thread.currentThread() + " waiting ...");
            }
        }catch (InterruptedException e){

        }
    }

    synchronized void prod(){
        notify();
    }
    synchronized void prodAll(){
        notifyAll();
    }
}

/**
 * 异步线程1 Task1
 * 这个异步线程的工作，就是wait
 */
class Task1 implements Runnable{

    /**
     * 特别备注：
     * 因为Blocker对象是static的，所以，如果我们启动了多个Task1并发线程
     * 这些Task1线程会共享一个Blocker对象
     * 至于Blocker对象是哪个Task1并发线程创建的，
     * 就看哪个线程初始化Blocker对象比较快了
     */
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " task1 running ...");
        blocker.waitingCall();
    }
}

/**
 * 异步线程2 Task2
 * 这个异步线程的工作也是wait
 */
class Task2 implements Runnable{
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " task2 running ...");
        blocker.waitingCall();
    }
}

public class NotifyVsNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        /**
         * 启动5个Task1并发线程
         * 意思就是有5个Task1并发线程在waiting...
         */
        for (int i = 0; i < 5; i++) {
            executorService.execute(new Task1());
        }

        /**
         * 启动1个Task2线程
         * 意思就是有1个Task2并发线程在waiting...
         */
        executorService.execute(new Task2());

        /**
         * 启动一个定时任务，每隔400ms就运行一次
         * 定时任务执行什么逻辑呢？
         * 就是定期调用Task1.blocker对象，一次调用prod()，一次调用prodAll()
         */
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;

            @Override
            public void run() {

                if(prod){
                    System.out.println("notify ...");
                    Task1.blocker.prod();
                    prod = false;
                }else{
                    System.out.println("notifyAll ...");
                    Task1.blocker.prodAll();
                    prod = true;
                }

            }
        },400,400);

        // 5秒之后，定时任务停止
        Thread.sleep(5 * 1000);
        timer.cancel();
        System.out.println("timer canceled ..");

        System.out.println("Task2.blocker.notifyAll() ...");
        Task2.blocker.prod();

        Thread.sleep(2 * 1000);
        System.out.println("thread pool shutting down ...");
        executorService.shutdownNow();



    }
}
