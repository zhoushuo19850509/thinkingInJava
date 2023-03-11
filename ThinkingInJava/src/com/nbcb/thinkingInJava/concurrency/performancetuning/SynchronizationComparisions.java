package com.nbcb.thinkingInJava.concurrency.performancetuning;


import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */


abstract class Accumulator{


    protected volatile int index = 0;
    protected volatile long value = 0;
    protected final static int SIZE = 100000;
    protected static int[] preload = new int[SIZE];


    // static block中做的事情，就是往数组中添加随机的数据
    static {
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preload[i] = random.nextInt();
        }
    }


    /**
     * 这两个抽象方法，交给子类去实现
     * 方法1 accumulate()
     * 从数组中preload不断循环取元素，累加到value中
     */
    public abstract void accumulate();

    /**
     * 方法2 读取value
     * @return
     */
    public abstract long read();

    /**
     * 内部类1
     */
    private class Modifier implements Runnable{

        @Override
        public void run() {

        }
    }

    /**
     * 内部类2
     */
    private class Reader implements Runnable{

        @Override
        public void run() {

        }
    }


}

class BaseLine extends Accumulator{



    @Override
    public void accumulate() {
        value += preload[index++];
        if(index > SIZE){
            index = 0;
        }

    }

    @Override
    public long read() {
        return value;
    }


}

class SynchronizeTest extends Accumulator{

    @Override
    public synchronized void accumulate() {
        value += preload[index++];
        if(index > SIZE){
            index = 0;
        }
    }

    @Override
    public synchronized long read() {
        return value;
    }
}

class LockTest extends Accumulator{

    private Lock lock = new ReentrantLock();

    @Override
    public void accumulate() {
        lock.lock();
        try{
            value += preload[index++];
            if(index > SIZE){
                index = 0;
            }
        }finally {
            lock.unlock();
        }


    }

    @Override
    public long read() {
        lock.lock();
        try{
            return value;
        }finally {
            lock.unlock();
        }
    }
}




public class SynchronizationComparisions {


    public static void main(String[] args) {

    }
}
