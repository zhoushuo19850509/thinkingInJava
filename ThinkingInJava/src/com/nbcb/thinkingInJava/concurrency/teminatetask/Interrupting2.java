package com.nbcb.thinkingInJava.concurrency.teminatetask;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �������Ϊ��˵��Lock.lockInterruptibly()���÷���
 * ���ͨ��lockInterruptibly()�ķ�ʽ��ȡ����
 *
 * �������������ܼ򵥣������ڳ�ʼ��ʵ����ʱ�򣬾�����
 * Ȼ������ͨ��һ�����߳�(Blocked2)���߳�run()�����е���f()����ȡ�������
 * ������˵��Ӧ����û����ȡ������ģ���ΪBlockMutexʵ��������ʱ���Ѿ������ˡ�
 *
 * ������Ϊ������f()��ͨ��lock.lockInterruptibly()������ȡ����
 * ��ˣ������ж�Blocked2�߳�ʱ���ܹ���ȡ��BlockMutex����.
 *
 * ��Ϊ�Աȣ����Ǵ������·���:BlockMutex.g()
 * �����������һ����ͨ��lock()����
 * ��ʱ�������ж����̣߳����ּ����ж������̣߳�Ҳ�޷���ȡ��BlockMutex����
 */
class BlockMutex{

    private Lock lock = new ReentrantLock();

    /**
     * constructor
     * ˵�������һ����ʼ������Ҫ��ȡ��
     */
    public BlockMutex() {
        lock.lock();
    }

    /**
     * ͨ������f()�������жϵķ�ʽ��ȡ����
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
 * ����߳̾���һ���£�����Ҫʵ����һ��BlockMutex���󣬲��һ�ȡBlockMutex�е���
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
