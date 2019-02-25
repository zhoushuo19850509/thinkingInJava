package com.nbcb.thinkingInJava.concurrency.shareresource;

/**
 * 生成偶数的实现类v1.0
 * 具体的生成偶数的方法为next()
 * 我们通过EvenChecker.test()检测出来，这个生成偶数的方法不是线程安全的
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
