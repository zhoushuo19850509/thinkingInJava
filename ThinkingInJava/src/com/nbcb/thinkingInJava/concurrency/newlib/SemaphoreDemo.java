package com.nbcb.thinkingInJava.concurrency.newlib;


/**
 * 这个文件主要是调用Pool，验证我们通过Semaphore管理object pool是否成功
 *
 * 后续我们拿这个自己写的object pool 和apache commons pool比对一下，看性能是否有所不同
 */


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个类主要是模拟通过一个单独线程，从object pool 中取出一个对象
 * 然后使用一段时间，最后把这个对象放回objecct pool
 */
class CheckoutTask<T> implements Runnable{

    private static int counter = 0 ;
    private final int id = counter++;

    private Pool<T> pool;

    /**
     * constructor
     * @param pool
     */
    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            /**
             * checkout an object from pool
             */
            T item = pool.checkOut();
            System.out.println(this + " has checkouted : " + item);

            /**
             * do sth
             */
            Thread.sleep(5000);

            /**
             * check in the object back to the pool
             */
            System.out.println(this + " start checkin : " + item);
            pool.checkIn(item);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "CheckoutTask{" +
                "id=" + id +
                '}';
    }
}

public class SemaphoreDemo {

    /**
     * 并发获取object pool资源的线程数
     */
    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {



        /**
         * 先创建一个object pool of Fat
         */
        Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);

        /**
         * 通过CheckoutTask 模拟各个线程从object pool取资源
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE ; i++) {
            exec.execute(new CheckoutTask<Fat>(pool));
        }
        System.out.println("all CheckoutTask started!");
        System.out.println("current available objects in the pool: " + pool.getCurrentAvailableObjectCount());


        /**
         * 模拟主线程从object pool获取资源
         */
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Fat fat = pool.checkOut();
            System.out.println(i + " main thread checked out!");
            fat.operate();
            list.add(fat);
        }
        System.out.println("current available objects in the pool: " + pool.getCurrentAvailableObjectCount());


        /**
         * 5秒钟之后，main thread checkin the object in the list
         */
        Thread.sleep(5000);
        System.out.println("check in objects in: " + list);
        System.out.println("current size of the list: " + list.size());

        System.out.println("try check in objects in the list");
        for(Fat f: list){
            pool.checkIn(f);
        }
        System.out.println("current available objects in the pool: " + pool.getCurrentAvailableObjectCount());

        System.out.println("try check in objects in the list again");
        for(Fat f: list){
            pool.checkIn(f);
        }
        System.out.println("current available objects in the pool: " + pool.getCurrentAvailableObjectCount());

        exec.shutdownNow();


    }
}
