package com.nbcb.thinkingInJava.concurrency.shareresource;

import com.nbcb.thinkingInJava.concurrency.shareresource.IntGenerator;

/**
 * 这个文件作为和EvenGenerator.java的对比
 * 主要是为了说明如何通过synchronized关键字，保证我们某个方法的线程安全
 * 最终效果还是通过EvenChecker进行验证
 */
public class SynchronizedEvenGenerator extends IntGenerator{

    private int currentEventValue = 0;

    @Override
    public synchronized int next() {
        ++currentEventValue;
        ++currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args){
        EvenChecker.test(new SynchronizedEvenGenerator());

    }
}
