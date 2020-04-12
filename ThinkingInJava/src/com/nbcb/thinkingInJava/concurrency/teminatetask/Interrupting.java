package com.nbcb.thinkingInJava.concurrency.teminatetask;


import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ��������ļ���ҪΪ��˵������ն�һ���̡߳�
 * SleepBlocked/IOBlocked/SynchronizedBlocked�ֱ�����������͵��߳�
 * ���У�
 * 1.SleepBlocked�߳�����������Thread.sleep()���ܹ��ɹ��ж��߳�
 * 2.IOBlocked�߳����������ǵȴ�IO���޷������߳�(ֻ��ͨ��system.exit(0)ǿ���˳�)
 * 3.SynchronizedBlocked�߳����������ǵȴ���ȡsynchronize�������޷������߳�(ֻ��ͨ��system.exit(0)ǿ���˳�)
 */


class SleepBlocked implements Runnable{

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Existing SleepBlocked.run()");
    }
}


class IOBlocked implements Runnable{

    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            System.out.println("Print: waiting for read:");
            in.read();
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted from block IO");
            }else{
                System.out.println("Runtime exception!");
                throw new RuntimeException();
            }
        }

    }
}



class SynchronizedBlocked implements Runnable{

    /**
     * �ȶ���һ�����������������������ܼ򵥣����Ǳ��ֲ��ϵĴ���
     */
    public synchronized void f(){
        while(true){
            Thread.yield();
        }
    }

    /**
     * Ȼ����constructor�У�����һ���µ��߳��������������
     */
    public SynchronizedBlocked() {
        new Thread(){
            public void run(){
                f();
            }
        }.start();
    }

    /**
     * ���SynchronizedBlocked�����߳���������ͨ��run()�������Է���f()
     * ��f()���synchronized fucntion�Ѿ���constructorռ����
     * ��ˣ����߳������󣬾Ͳ��ϵȴ���f()�ͷţ���Ȼ���ǲ����ܵģ���Ϊf()���ϱ�ռ����
     */
    @Override
    public void run() {
        System.out.println("tryint to call f()");
        f();
        System.out.println("Existing SynchronizedBlocked.run()");

    }
}

public class Interrupting {

    private static ExecutorService exec = Executors.newCachedThreadPool();

    /**
     * ���������Ҫ�ǵȴ�100ms֮���ж�ĳ���߳���
     * @param r
     */
    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        Thread.sleep(100);
        System.out.println("Interrupting: " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to : " + r.getClass().getName());

    }

    public static void main(String[] args) throws InterruptedException {

//        test(new SleepBlocked());
//        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());

        Thread.sleep(5000);
        System.out.println("aborting system.exit(0)");
        System.exit(0);

    }
}
