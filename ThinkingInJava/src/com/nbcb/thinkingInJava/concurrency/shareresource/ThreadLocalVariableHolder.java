package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个例子说明如何通过Thread local的方式
 * 保证竞争资源在各个线程中都是独立保存的
 * 从程序运行结果可以看到，5个线程中的Integer变量是分别累加的
 * 当然，我们的主线程也是一个单独的线程，和5个子线程不一样
 * 最终通过ThreadLocalVariableHolder.get()获取的变量自然也是独立的
 */

/**
 * Accessor是单独的进程
 * 每个进程都会不断循环调用ThreadLocalVariableHolder
 * 不断递增我们保存在Thread local的Integer
 */
class Accessor implements Runnable{

    private final int id;

    Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "Accessor{" +
                "id=" + id +
                '}' + " : " + ThreadLocalVariableHolder.get();
    }
}


/**
 * 这个类说明了如何通过Thread local方法保证我们的Integer是线程安全的
 * 一开始，这个Integer是一个随机数
 * 后续会通过increment()方法不断加1，然后通过get()方法获取当前Integer数值
 */
public class ThreadLocalVariableHolder {
    private static ThreadLocal<Integer> value =
            new ThreadLocal<Integer>(){
                private Random rand = new Random(47);
                protected synchronized Integer initialValue(){
                    return rand.nextInt(10000);
//                    return 0;
                }
            };

    public static void increment(){
        value.set(value.get() + 1);
    }

    public static int get(){
        return value.get();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * 启动5个Accesoor进程
         */
        for(int i = 0 ; i < 5; i++){
            exec.execute(new Accessor(i));
        }

        /**
         * 一段时间以后，再把各个进程关闭
         */
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdownNow();

        System.out.println("final value: " + ThreadLocalVariableHolder.get());

    }
}
