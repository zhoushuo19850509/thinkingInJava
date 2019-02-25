package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 这个文件主要是为了说明两件事情：
 * 1.Thread.isDaemon()方法可以检验thread是否为daemon thread
 * 2.如果父线程为daemon thread，那么子线程也是daemon thread！
 */


/**
 * main class
 */
public class Daemons {
    public static  void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Daemon());
        t.setDaemon(true);
        t.start();
        System.out.println("t.isDaemon() = " + t.isDaemon());

        /**
         * 主线程等待1秒种以后再停止嘛，先看看子线程的表现
         */
        TimeUnit.SECONDS.sleep(1);
    }
}


/**
 * 启动10个DaemonSpawn 线程，并且通过isDaemon()方法来检测这些线程是否为daemon thread
 */
class Daemon implements  Runnable{
    private Thread[] t = new Thread[10];

    @Override
    public void run() {
        for( int i = 0 ; i < t.length; i++){
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("Spawn thread " + i + " started!");
        }

        for(int i = 0 ; i < t.length; i++ ){
            System.out.println("Spawn thread " + i + " .isDaemon() = " + t[i].isDaemon());

        }
        while (true){
            Thread.yield();
        }
    }
}

/**
 * 线程一旦启动，马上让出资源
 */
class DaemonSpawn implements Runnable{

    @Override
    public void run() {
        while(true){
            Thread.yield();
        }

    }
}

