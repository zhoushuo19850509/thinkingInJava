package com.nbcb.thinkingInJava.concurrency.teminatetask;


import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 这个代码文件主要为了说明如何终端一个线程。
 * SleepBlocked/IOBlocked/SynchronizedBlocked分别代表三种类型的线程
 * 其中：
 * 1.SleepBlocked线程做的事情是Thread.sleep()，能够成功中断线程
 * 2.IOBlocked线程做的事情是等待IO，无法结束线程(只能通过system.exit(0)强制退出)
 * 3.SynchronizedBlocked线程做的事情是等待获取synchronize方法，无法结束线程(只能通过system.exit(0)强制退出)
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
     * 先定义一个方法，这个方法做的事情很简单，就是保持不断的存在
     */
    public synchronized void f(){
        while(true){
            Thread.yield();
        }
    }

    /**
     * 然后在constructor中，启动一个新的线程来调用这个方法
     */
    public SynchronizedBlocked() {
        new Thread(){
            public void run(){
                f();
            }
        }.start();
    }

    /**
     * 如果SynchronizedBlocked的新线程启动，就通过run()方法尝试访问f()
     * 而f()这个synchronized fucntion已经被constructor占用了
     * 因此，新线程启动后，就不断等待着f()释放，当然这是不可能的，因为f()不断被占用着
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
     * 这个方法主要是等待100ms之后，中断某个线程类
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
