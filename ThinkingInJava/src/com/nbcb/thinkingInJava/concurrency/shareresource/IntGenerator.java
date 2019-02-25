package com.nbcb.thinkingInJava.concurrency.shareresource;


/**
 * 这只是一个抽象类
 */
public abstract class IntGenerator {
    private volatile boolean cancled = false;

    public abstract int next(); // 抽象方法，子类中可以具体实现

    public boolean isCancled(){
        return cancled;
    }

    public void cancle(){
        cancled = true;
    }

}
