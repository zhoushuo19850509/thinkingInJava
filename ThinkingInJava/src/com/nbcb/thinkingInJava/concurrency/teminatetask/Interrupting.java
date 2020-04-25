package com.nbcb.thinkingInJava.concurrency.teminatetask;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 这个代码文件主要为了说明如何中断一个线程。
 * SleepBlocked/IOBlocked/SynchronizedBlocked分别代表三种类型的线程
 * 其中：
 * 1.SleepBlocked线程做的事情是Thread.sleep()，能够成功中断线程
 * 2.IOBlocked线程做的事情是等待IO，无法结束线程(只能通过system.exit(0)强制退出)
 * 3.SynchronizedBlocked线程做的事情是等待获取synchronize方法，无法结束线程(只能通过system.exit(0)强制退出)
 */

/**
 * 这个Thread类做的事情，就是Sleep一段时间
 * 如果在Sleep的时间段内，线程被中断了，那么这个中断是能够成功的
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

/**
 * 这个Thread类做的事情，就是等待IO输入
 * 如果如果在等待IO输入的时间段内，线程被中断了，那么这个中断是没法成功的
 * 只有等主线程退出来，这个等待IO输入的动作才会结束
 */
class IOBlocked implements Runnable{

    private InputStream in;

    /**
     * constructor
     * @param in inputstream 既可以是SystemOut.in，也可是通过socket等待网络上的输入流
     *           总之，将线程结束并不能结束这个inputstream。
     */
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


/**
 * 这个Thread类做的事情，就是等待获取synchronize方法
 * 如果如果在等待获取synchronize方法的时间段内，线程被中断了，那么这个中断是没法成功的
 * 只有等主线程退出来，这个等待获取synchronize方法的动作才会结束
 */
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

    public static void main(String[] args) throws InterruptedException, IOException {

        /**
         * 验证场景1 (书本上的场景)
         */
//        test(new SleepBlocked());
//        test(new IOBlocked(System.in));
//        test(new SynchronizedBlocked());


        /**
         * 验证场景2 (尝试将socket inputstream作为IOBlocked中的输入流)
         * 显然，关闭Future也没办法中断IOBlocked
         */
//        ServerSocket server =new ServerSocket(8080);
//        InputStream in = new Socket("localhost",8080).getInputStream();
//        test(new IOBlocked(in));

        /**
         * 验证场景3 我们尝试通过Interrupting2.java中的中断(线程)方式，
         * 看能否获取SynchronizedBlocked中的同步方法
         * 从实际运行结果来看，显然也是不行。
         */
//        Thread t = new Thread(new SynchronizedBlocked());
//        t.start();
//        Thread.sleep(5000);
//        System.out.println("start interrupt !");
//        t.interrupt();

        Thread.sleep(5000);
        System.out.println("aborting system.exit(0)");
        System.exit(0);

    }
}
