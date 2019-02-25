package com.nbcb.thinkingInJava.concurrency.excep;


/**
 * ����ļ�Ҫ��ExceptionThread.java�������һ��
 * ֮ǰExceptionThread.java�������ǣ����߳�û����׽���߳��׳����쳣
 * ����ļ����ǽ����������
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * ģ���׳��쳣�����߳�
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
 * ���߳������������߳��쳣��handler
 * ���handlerֻ��Ҫʵ��Thread.UncaughtExceptionHandler�Ľӿڷ�����
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
 * Դ��ThreadFactory�ӿڣ��ο�DaemonFromFactory.java��DaemonThreadFactory.java
 * ��Ҫ�����Ǻ�������ͨ��ExecutorService�̳߳ش����̵߳�ʱ�򣬶�����´������߳���һЩ����
 * �ڵ�ǰ���newThread()�����У�����������Ƕ��´������߳�����һ��
 */
class HandlerThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(" thread factory creating new thread: " + this);
        Thread t = new Thread(r);
        System.out.println(" created " + t);
        /**
         * ����´�����thread������handler��ר�����������´�����thread���׳����쳣
         */
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println(" eh2 = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

/**
 * �ٳ���һ�£��������߳��Ƿ��ܲ����´��������߳��׳����쳣
 */
public class CaptureUncaughtException {
    public static void main(String[] args){

        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }

}
