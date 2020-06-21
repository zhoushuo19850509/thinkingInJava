package com.nbcb.thinkingInJava.concurrency.newlib;


/**
 * ����ļ���Ҫ�ǵ���Pool����֤����ͨ��Semaphore����object pool�Ƿ�ɹ�
 *
 * ��������������Լ�д��object pool ��apache commons pool�ȶ�һ�£��������Ƿ�������ͬ
 */


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �������Ҫ��ģ��ͨ��һ�������̣߳���object pool ��ȡ��һ������
 * Ȼ��ʹ��һ��ʱ�䣬�����������Ż�objecct pool
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
     * ������ȡobject pool��Դ���߳���
     */
    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {



        /**
         * �ȴ���һ��object pool of Fat
         */
        Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);

        /**
         * ͨ��CheckoutTask ģ������̴߳�object poolȡ��Դ
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE ; i++) {
            exec.execute(new CheckoutTask<Fat>(pool));
        }
        System.out.println("all CheckoutTask started!");
        System.out.println("current available objects in the pool: " + pool.getCurrentAvailableObjectCount());


        /**
         * ģ�����̴߳�object pool��ȡ��Դ
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
         * 5����֮��main thread checkin the object in the list
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
