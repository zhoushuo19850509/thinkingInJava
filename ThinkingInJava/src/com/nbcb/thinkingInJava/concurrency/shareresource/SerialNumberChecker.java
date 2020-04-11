package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个文件要和SerialNumberGenerator.java结合起来一起看
 * 主要是为了验证序列号生成器(SerialNumberGenerator)是不是线程安全的.
 * 检测原理其实非常直接，就是启动若干个线程，每个线程分别调用序列号生成器生成一个序列
 * 然后放到一个共同的容器中，最后判断是否有重复的序列号
 *
 * 从这个例子来看，显然是不安全的。
 * 怎么解决呢？最简单的方法当然是在生成序列号的时候加上synchronized关键字：
 *
 * 以后我们如果碰到有序列号生成的需求，要特别关注线程是否安全。可以用这个模型来进行检测。
 *

 */

/**
 * CircularSet主要是用于生成一个环形的数据
 */
class CircularSet{
    private int[] array;
    private int len;
    private int index;

    public CircularSet(int size) {
        array = new int[size];
        this.len = size;

        /**
         * 把整个数组初始化一下
         */
        for(int i = 0 ; i < size; i++){
            array[i] = -1;
        }
    }

    public synchronized void add(int i){
        array[index] = i;
        index = ++index % len;
    }

    public synchronized boolean contains(int val ){


        for(int i = 0 ; i < len; i++){
            if(array[i] == val){
                return true;
            }
        }
        return false;
    }

}

/**
 * 这个类模拟一个独立的线程
 * 线程做的事情就是往一个环形数组(CircularSet，全局的实例)中不断push 序列号
 * 序列号由SerialNumberGenerator生层
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;   // 需要启动的SerialNumberChecker线程数
    private static CircularSet serails = new CircularSet(1000);

    private static ExecutorService exec = Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable{

        @Override
        public void run() {
            while(true){
                int serail = SerialNumberGenerator.nextSerialNumber();
                if(serails.contains(serail)){
                    System.out.println("Duplicate: " + serail);
                    System.exit(0);   // 当前线程退出
                }
                serails.add(serail);   // 如果没有重复数据，就往环形数组中插入数据
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < SIZE ; i++){
            exec.execute(new SerialChecker());
            Thread.sleep(1000);
        }
        while(true){
            Thread.sleep(5000);
            System.out.println("no duplicate detected!");
        }
    }


}
