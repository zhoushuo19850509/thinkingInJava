package com.nbcb.thinkingInJava.concurrency.shareresource;


/**
 * ��ֻ��һ��������
 */
public abstract class IntGenerator {
    private volatile boolean cancled = false;

    public abstract int next(); // ���󷽷��������п��Ծ���ʵ��

    public boolean isCancled(){
        return cancled;
    }

    public void cancle(){
        cancled = true;
    }

}
