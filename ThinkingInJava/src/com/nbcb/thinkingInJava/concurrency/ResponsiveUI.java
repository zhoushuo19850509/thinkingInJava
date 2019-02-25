package com.nbcb.thinkingInJava.concurrency;

import java.io.IOException;

/**
 * 这个文件主要是为了说明
 * 我们响应式的界面，实现原理就是Thread
 * 比如我们这个例子中，计算d的值，就是新起一个线程单独计算
 * 这样就不会影响到我们正常的输入输出
 */
class UnResponsiveUI {
    private volatile double d = 1;
    public UnResponsiveUI() throws IOException {

        while( d > 0){
            d = d + ( Math.PI + Math.E ) / d;
        }
        System.in.read();

    }
}


public class ResponsiveUI extends Thread{
    private static volatile double d = 1;

    public ResponsiveUI(){
        setDaemon(true);
        start();
    }
    public void run(){
        while( d > 0){
            d = d + ( Math.PI + Math.E ) / d;
        }
    }
    public static void main(String[] args) throws IOException {

        ResponsiveUI responsiveUI = new ResponsiveUI();

        while(true){
            System.out.println("show the current value : Enter!");
            System.in.read();
            System.out.println(d);
        }

    }
}
