package com.nbcb.thinkingInJava.concurrency;

import java.io.IOException;

/**
 * ����ļ���Ҫ��Ϊ��˵��
 * ������Ӧʽ�Ľ��棬ʵ��ԭ�����Thread
 * ����������������У�����d��ֵ����������һ���̵߳�������
 * �����Ͳ���Ӱ�쵽�����������������
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
