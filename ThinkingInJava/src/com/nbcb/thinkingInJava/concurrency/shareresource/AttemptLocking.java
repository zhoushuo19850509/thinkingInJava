package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个文件为了说明lock.tryLock()的用法
 * 一种是即时获取锁：lock.tryLock()
 * 一种是设置一定的超时时间：lock.tryLock(2, TimeUnit.SECONDS)
 *
 * 我们在代码中，当前线程要调用lock.lock()方法前，
 * 最好先调用tryLock()方法试试
 * 看能不能取到锁
 */
public class AttemptLocking {

    /**
     * private lock of the class
     */
    private ReentrantLock lock = new ReentrantLock();


    /**
     * 即时获取锁
     */
    public void untimed(){
        boolean captured = lock.tryLock();
        try{
            System.out.println("trylock() : " + captured);
        }finally{
            if(captured){
                lock.unlock();
            }

        }

    }

    /**
     * 在一定超时时间内，如果还未获取到锁，就抛出异常
     */
    public void timed(){
        boolean captured = false;
        try{
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException();
        }

        try{
            System.out.println(
                    "lock.tryLock(2, TimeUnit.SECONDS) :" + captured);
        }finally{
            if(captured){
                lock.unlock();
            }
        }

    }

    public static void main(String[] args){
        final AttemptLocking al = new AttemptLocking();

        /**
         * 在没有其他线程干扰的情况下
         */
        al.untimed();  // 即时获取锁是成功的
        al.timed();    // 在超时时间内获取锁，也是成功的

        /**
         * 启动一个Daemon Thread，获取锁
         * 这时候再调用untimed()/time()方法获取锁，就失败了
         */
        new Thread(){
            {
                setDaemon(true);
            }
            public void run(){
                System.out.println("daemon thread start to run");
                al.lock.lock();
                System.out.println("daemon thread finish get lock!");
                System.out.println("get lock by daemon thread!");
                boolean captured = al.lock.tryLock();
                System.out.println("lock.tryLock() in daemon thread:" + captured);

            }
        }.start();
        Thread.yield();

        /**
         * 这个Thread.sleep()是我加的
         * 如果不加的话，达不到实验的效果，
         * 因为往往还没等到Daemon Thread获取到锁，untimed()/timed()这两个方法就执行了
         * 所以我们启动Daemon Thread之后，要稍微等一会儿再启动ntimed()/timed()
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * untimed()/timed()这两个方法都取不到锁
         * 因为我们的Daemon Thread已经取到锁了！
         */
        al.untimed();
        al.timed();

    }


}
