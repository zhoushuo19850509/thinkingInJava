package com.nbcb.thinkingInJava.concurrency.newlib;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * 这个例子主要是通过一个object pool(对象池)的概念，说明Semaphore的用法
 * Semaphore的意思是：
 */
public class Pool<T> {
    private int size; // pool size
    private List<T> items = new ArrayList<T>(); // 这个List保存的是object pool中所有的对象
    private volatile boolean[] checkOut; // 所有当前检出的资源
    private Semaphore available;  // 可用的Semaphore资源

    /**
     * constructor 主要是做一些object pool的初始化动作
     * @param classObject
     * @param size
     */
    public Pool(Class<T> classObject, int size) {
        this.size = size;

        /**
         * 默认所有的checkOut状态都是false
         */
        checkOut = new boolean[size];

        /**
         * 初始化Semaphore对象
         */
        available = new Semaphore(size, true);

        /**
         * 初始化object pool
         */
        for(int i = 0 ; i < size; i++){
            try {
                items.add(classObject.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检出一个资源
     * 先获取Semaphore资源，再调用getItem()从object pool中获取一个对象
     * @return
     */
    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }


    /**
     * 检入某个资源
     * 一旦释放资源成功，就释放Semaphore
     * @param item
     */
    public void checkIn(T item){
        if(releaseItem(item)){
            available.release();
        }

    }


    /**
     * 获取一个对象资源(内部使用)
     * @return
     */
    private synchronized T getItem(){
        /**
         * 实现思路是这样的：遍历object pool ，一旦发现有未检出的对象，就取出来
         */
        for (int i = 0; i < this.size; i++) {
            if(!checkOut[i]){
                checkOut[i] = true;
                return items.get(i);
            }
        }
        return null;  // 从object pool中没法取到空闲的对象

    }

    /**
     * 释放一个对象资源(内部使用)
     * @param item
     * @return 释放对象是否成功
     */
    private synchronized boolean releaseItem(T item){
        /**
         * 实现思路是这样的：先获取要释放的对象在object pool中的序号
         * 然后将checkOut状态置为false
         */
        int index = items.indexOf(item);
        if(index == -1){
            return false; // 没找到要释放的对象
        }
        /**
         * 一旦发现要释放的对象处于检出状态，就把检出状态改为false
         */
        if(checkOut[index]){
            checkOut[index] = false;
            return true;
        }
        return false;
    }

    /**
     * 返回当前object pool中空闲的对象数量(checkout状态为false)
     * @return
     */
    public int getCurrentAvailableObjectCount(){
        int count = 0;
        for (int i = 0; i < checkOut.length; i++) {
            if(!checkOut[i]){
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        System.out.println("hello");
        int size = 10;
        boolean[] checkOut = new boolean[size];
        for (int i = 0; i < size; i++) {
            System.out.println(checkOut[i]);

        }
    }

}
