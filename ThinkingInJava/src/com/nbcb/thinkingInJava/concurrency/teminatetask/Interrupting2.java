package com.nbcb.thinkingInJava.concurrency.teminatetask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockMutex{

    private Lock lock = new ReentrantLock();

    /**
     * constructor
     * 说明这个类一旦初始化，就要获取锁
     */
    public BlockMutex() {
        lock.lock();
    }

    public void f(){
        lock.lockInterruptibly();
    }
}

class Blocked2 implements Runnable{

    @Override
    public void run() {

    }
}


public class Interrupting2 {

    public static void main(String[] args) {

    }
}
