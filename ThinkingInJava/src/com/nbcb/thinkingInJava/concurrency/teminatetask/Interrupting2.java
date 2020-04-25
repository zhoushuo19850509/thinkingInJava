package com.nbcb.thinkingInJava.concurrency.teminatetask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个代码为了说明Lock.lockInterruptibly()的用法：
 * 如何通过lockInterruptibly()的方式获取到锁
 *
 * 这个类做的事情很简单，就是在初始化实例的时候，就锁上
 * 然后我们通过一个子线程(Blocked2)，线程run()方法中调用f()来获取这个锁。
 * 正常来说，应该是没法获取这个锁的，因为BlockMutex实例创建的时候已经锁上了。
 *
 * 但是因为我们在f()中通过lock.lockInterruptibly()方法获取锁，
 * 因此，我们中断Blocked2线程时，能够获取到BlockMutex的锁.
 *
 * 作为对比，我们创建了新方法:BlockMutex.g()
 * 这个方法就是一个普通的lock()方法
 * 这时我们再中断子线程，发现即便中断了子线程，也无法获取到BlockMutex的锁
 */
class BlockMutex{

    private Lock lock = new ReentrantLock();

    /**
     * constructor
     * 说明这个类一旦初始化，就要获取锁
     */
    public BlockMutex() {
        lock.lock();
    }

    /**
     * 通过调用f()就能以中断的方式获取到锁
     */
    public void f(){
        try {
            lock.lockInterruptibly();
            System.out.println("lock acquired by f()");
        } catch (InterruptedException e) {
//            e.printStackTrace();
            System.out.println("interrupt from lock acquisition in f()");
        }
    }

    public void g(){
        lock.lock();
    }
}

/**
 * 这个线程就做一件事，就是要实例化一个BlockMutex对象，并且获取BlockMutex中的锁
 */
class Blocked2 implements Runnable{
    BlockMutex blockMutex = new BlockMutex();

    @Override
    public void run() {
        System.out.println("waiting for lock f() in BlockMutex");
        blockMutex.f();
//        blockMutex.g();
        System.out.println("broken out of blocked call");
    }
}


public class Interrupting2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked2());
        t.start();
        Thread.sleep(5000);
        System.out.println("start interrupt !");
        t.interrupt();

    }
}
