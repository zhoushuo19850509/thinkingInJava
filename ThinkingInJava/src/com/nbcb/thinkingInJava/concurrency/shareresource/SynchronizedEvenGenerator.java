package com.nbcb.thinkingInJava.concurrency.shareresource;

import com.nbcb.thinkingInJava.concurrency.shareresource.IntGenerator;

/**
 * ����ļ���Ϊ��EvenGenerator.java�ĶԱ�
 * ��Ҫ��Ϊ��˵�����ͨ��synchronized�ؼ��֣���֤����ĳ���������̰߳�ȫ
 * ����Ч������ͨ��EvenChecker������֤
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
