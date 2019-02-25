package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * 这个文件主要是为了说明线程中finally{}内的代码有可能不被执行
 * 模拟的方法主要是：
 * 1.主进程创建一个子线程，这个子线程为daemon thread
 * 2.主进程马上结束，，逼迫子线程也马上停止，
 * 3.以观察子线程的finally是被执行
 *
 */
public class DaemonDontRunFinally {
    public static void main(String[] args){
        Thread t = new Thread(new ADaomon());

        /**
         * 将我们启动的ADaemon设置为Daemon thread
         */
        t.setDaemon(true);

        t.start();

        /**
         * 然后主线程马上停止，逼迫子线程也马上停止，
         * 以观察子线程的finally是被执行
         */
    }

}

class ADaomon implements Runnable{

    @Override
    public void run() {
        try{
            System.out.println("Start ADaemon!");
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally executed!");
        }
    }
}

