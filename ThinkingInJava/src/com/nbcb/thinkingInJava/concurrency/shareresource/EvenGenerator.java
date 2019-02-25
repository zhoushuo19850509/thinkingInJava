package com.nbcb.thinkingInJava.concurrency.shareresource;

/**
 * ����ż����ʵ����v1.0
 * ���������ż���ķ���Ϊnext()
 * ����ͨ��EvenChecker.test()���������������ż���ķ��������̰߳�ȫ��
 */
public class EvenGenerator extends IntGenerator {
    private int currentEventValue = 0;

    @Override
    public int next() {
        ++currentEventValue;
        ++currentEventValue;
        return currentEventValue;
    }

    public static void main(String[] args){
        EvenChecker.test(new EvenGenerator());

    }

}
