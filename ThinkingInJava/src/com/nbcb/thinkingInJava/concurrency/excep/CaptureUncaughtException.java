package com.nbcb.thinkingInJava.concurrency.excep;


/**
 * 这个文件要和ExceptionThread.java结合起来一起看
 * 之前ExceptionThread.java的问题是：主线程没法捕捉子线程抛出的异常
 * 这个文件就是解决这个问题的
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 模拟抛出异常的子线程
 */
class ExceptionThread2 implements Runnable{

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println(" run() by " + t);
        System.out.println(" eh2 = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

/**
 * 主线程用来处理子线程异常的handler
 * 这个handler只需要实现Thread.UncaughtExceptionHandler的接口方法：
 * uncaughtException()
 */
class MyUncaughtExceptionHandler implements  Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(" caught exception:  " + e);
    }
}

/**
 *
 * 源于ThreadFactory接口，参考DaemonFromFactory.java和DaemonThreadFactory.java
 * 主要作用是后续我们通过ExecutorService线程池创建线程的时候，都会对新创建的线程做一些事情
 * 在当前这个newThread()方法中，做的事情就是对新创建的线程设置一个
 */
class HandlerThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(" thread factory creating new thread: " + this);
        Thread t = new Thread(r);
        System.out.println(" created " + t);
        /**
         * 针对新创建的thread，设置handler，专门用来处理新创建的thread中抛出的异常
         */
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println(" eh2 = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

/**
 * 再尝试一下，看看主线程是否能捕获新创建的子线程抛出的异常
 */
public class CaptureUncaughtException {
    public static void main(String[] args){

        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }

}
