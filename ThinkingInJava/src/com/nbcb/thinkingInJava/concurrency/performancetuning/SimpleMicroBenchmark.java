package com.nbcb.thinkingInJava.concurrency.performancetuning;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这是一个简单的，用来评测线程之间同步效率的程序
 *  * 主要评测的对象是两种同步方案：
 *  * 1.synchronized
 *  * 2.Lock
 *  *
 *  * 实际测试下来效率差不多
 */

abstract class Incrementable{
    protected int counter = 0;
    public abstract void inrement();
}

class SynchronizingTest extends Incrementable{

    @Override
    public synchronized void inrement() {
        ++counter;
    }
}

class LockingTest extends Incrementable{
    private Lock lock = new ReentrantLock();

    @Override
    public void inrement() {
        lock.lock();
        try{
            ++counter;
        }finally {
            lock.unlock();
        }
    }
}

public class SimpleMicroBenchmark {

    /**
     * 这个static方法，用来统计耗时
     * @param incrementable
     * @return 返回耗时时间(纳秒ns)
     */
    public static long test(Incrementable incrementable){
        long start = System.nanoTime();
        for (int i = 0; i < 10000000; i++) {
             incrementable.inrement();
        }
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        SynchronizingTest synchronizingTest = new SynchronizingTest();
        LockingTest lockingTest = new LockingTest();
        long synchronizeTime = test(synchronizingTest);
        long lockTime = test(lockingTest);
        System.out.println("synchronizeTime : " + synchronizeTime);
        System.out.println("lockTime : " + lockTime);

    }
}
