package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ����ļ����õļ�������֮ǰ��SynchronizedEvenGenerator.java������ͬ
 * ����ļ�����Lock��������
 * ����Ҫע����ǣ����ǵ���lock.lock()����֮��
 * ����Ҫͨ��try{} finally{ lock.unlock()�� }����ʽ�����н���
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
