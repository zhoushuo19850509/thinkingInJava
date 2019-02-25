package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个类主要用来检测IntGenerator生成的数字是否为偶数
 * 检测的方式主要是：多线程并发检测
 * 这对IntGenerator的线程安全性有一定的要求
 * 正常来说，IntGenerator.netx()应该生成的都是偶数
 * 但是如果IntGenerator线程不安全，就有可能检测到奇数的情况
 */
public class EvenChecker implements Runnable{

    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator g ,int ident){
        generator = g;
        id = ident;
    }

    @Override
    public void run() {
        /**
         * while循环不停检测IntGenerator生成的数字是否为偶数
         * 直到IntGenerator退出为止
         */
        while(!generator.isCancled()){
            int val = generator.next();
            if( ( val % 2 ) != 0){
                System.out.println(val + " not event");
                generator.cancle();
            }
        }
    }

    /**
     * 静态测试方法
     * 启多个线程
     * @param g
     * @param count  启动的线程数
     */
    public static void test(IntGenerator g,int count){
        System.out.println("Press ctl-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for( int i = 0 ; i < count; i++){
            exec.execute(new EvenChecker(g,count));
        }
        exec.shutdown();
    }

    /**
     * default test function
     * @param g
     */
    public static void test(IntGenerator g){
        test(g,10);
    }

}
