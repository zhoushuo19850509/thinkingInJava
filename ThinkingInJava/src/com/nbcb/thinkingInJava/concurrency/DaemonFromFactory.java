package com.nbcb.thinkingInJava.concurrency;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 这个类要和DaemonThreadFactory搭配起来一起看
 * 主要演示了如何通过创建一个newCachedThreadPool
 * 然后通过这个thread pool创建的thread自动成为daemon thread
 *
 * 我们知道，一旦main thread结束，由这个main thread创建的daemon thread会自动结束
 * 因此，我们在这个类创建的newCachedThreadPool就很有意义了。
 */
public class DaemonFromFactory implements Runnable{
    @Override
    public void run() {
        try{
            while(true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        try{
            /**
             *创建一个线程池
             * 通过这个线程池创建的线程，都是daemon thread
             */
            ExecutorService exec = Executors.newCachedThreadPool(
                    new DaemonThreadFactory());

//            ExecutorService exec = Executors.newCachedThreadPool();

            /**
             * 接下去就启动若干个DaemonFromFactory线程
             * 注意哦，通过exec线程池启动的线程，都自动转变为daemon thread了哦
             */
            for(int i = 0 ; i < 10 ; i++){
                exec.execute(new DaemonFromFactory());

            }
            System.out.println("all daemons started!");
            TimeUnit.MILLISECONDS.sleep(250);

            System.out.println("main thread finished!");
        }catch (InterruptedException e){
            e.printStackTrace();
        }




    }
}
