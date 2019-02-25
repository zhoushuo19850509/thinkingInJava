package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个文件采用的加锁机制之前的SynchronizedEvenGenerator.java有所不同
 * 这个文件采用Lock类来加锁
 * 必须要注意的是，我们调用lock.lock()加锁之后
 * 必须要通过try{} finally{ lock.unlock()； }的形式，进行解锁
 */
public class MutexEvenGenerator extends IntGenerator {
    private int currentEventValue = 0;
    private Lock lock = new ReentrantLock();

    @Override
    public int next() {
        lock.lock();
        try{
            ++currentEventValue;
            ++currentEventValue;
            return currentEventValue;
        }finally {
            lock.unlock();
        }

    }

    public static void main(String[] args){
        EvenChecker.test(new MutexEvenGenerator());

    }
}
