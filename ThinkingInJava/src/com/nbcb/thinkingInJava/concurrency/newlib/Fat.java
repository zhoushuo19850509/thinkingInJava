package com.nbcb.thinkingInJava.concurrency.newlib;

import com.nbcb.thinkingInJava.util.IdGenerator;

/**
 *
 * Fat这个对象，主要是模拟object pool中的一个对象资源
 * 这个资源在创建的时候，比较慢
 * 所以，创建一个资源池就比较有意义了
 *
 */
public class Fat {
    private volatile double d;
//    private static int counter = 0 ;
    private int id = 0;

    /**
     * constructor 主要是添加一个比较耗费资源的操作
     * 模拟该对象创建的时候比较慢
     */
    public Fat() {
        for (int i = 0; i < 10000; i++) {
            d += ( Math.PI + Math.E ) / (double)i;
        }
        this.id = IdGenerator.generateId();
    }

    public void operate(){
        System.out.println(this);

    }

    @Override
    public String toString() {
        return "Fat{" +
                "id=" + id +
                '}';
    }
}
