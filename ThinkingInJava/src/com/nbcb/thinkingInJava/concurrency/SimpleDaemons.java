package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 这个文件主要是说明Daemon thread的用法
 * Daemon thread运行在背景，当主线程停止的时候，Daemon thread也随之停止了
 */
public class SimpleDaemons implements Runnable {

    /**
     * Daemon thread做的事情非常简单，就是每隔100毫秒打印一下
     */
    public void run(){
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
             * 先启动10个daemon threads
             */
            for(int i = 0 ; i < 10 ; i++){
                Thread t = new Thread(new SimpleDaemons());

                t.setDaemon(true);
                t.start();
            }
            /**
             * 然后主线程(main())slepp 一段时间，看看daemon threads都干了些啥
             */
            TimeUnit.MILLISECONDS.sleep(175);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
