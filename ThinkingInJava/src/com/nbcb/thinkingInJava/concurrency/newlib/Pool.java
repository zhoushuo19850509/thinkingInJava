package com.nbcb.thinkingInJava.concurrency.newlib;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * ���������Ҫ��ͨ��һ��object pool(�����)�ĸ��˵��Semaphore���÷�
 * Semaphore�����������"�ź���"��ɶ��˼�أ�
 * ���������ڲ��������£������̻߳�ͬʱ����ĳ��������Դ��
 * ����������Pool.java�У�������Դ����Pool�еĸ�������
 * ����֪���������̷߳��ʹ�����Դ��ʱ�򣬿��ܻ���ɸ������⡣
 * ��ô����أ�Semaphore����һ�ֽ��������
 * ���粢���߳�Ҫ��Pool�л�ȡһ������Ϊ�˱��Ⲣ�����⣬
 * ���ȵ���Semaphore.acquire()��������ȡһ����Դ��
 * ����Դ�Ż�Pool֮���ٵ���Semaphore.release()�����ͷ���Դ��
 *
 * �������Ͱ�����ҵ�񳡾��еĲ������⣬ת��Ϊ��Semaphore�Ĳ���
 */
public class Pool<T> {
    private int size; // pool size
    // ���List�������object pool�����еĶ���
    private List<T> items = new ArrayList<T>();
    // ���е�ǰ�������Դ
    private volatile boolean[] checkOut;
    // ���õ�Semaphore��Դ
    private Semaphore available;

    /**
     * constructor ��Ҫ����һЩobject pool�ĳ�ʼ������
     * @param classObject
     * @param size
     */
    public Pool(Class<T> classObject, int size) {
        this.size = size;

        /**
         * Ĭ�����е�checkOut״̬����false(δ���)
         */
        checkOut = new boolean[size];

        /**
         * ��ʼ��Semaphore����
         */
        available = new Semaphore(size, true);

        /**
         * ��ʼ��object pool
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
     * ���һ����Դ
     * �Ȼ�ȡSemaphore��Դ���ٵ���getItem()��object pool�л�ȡһ������
     * @return
     */
    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }


    /**
     * ����ĳ����Դ
     * һ���ͷ���Դ�ɹ������ͷ�Semaphore
     * @param item
     */
    public void checkIn(T item){
        if(releaseItem(item)){
            available.release();
        }

    }


    /**
     * ��ȡһ��������Դ(�ڲ�ʹ��)
     * @return
     */
    private synchronized T getItem(){
        /**
         * ʵ��˼·�������ģ�����object pool ��һ��������δ����Ķ��󣬾�ȡ����
         */
        for (int i = 0; i < this.size; i++) {
            if(!checkOut[i]){
                checkOut[i] = true;
                return items.get(i);
            }
        }
        return null;  // ��object pool��û��ȡ�����еĶ���

    }

    /**
     * �ͷ�һ��������Դ(�ڲ�ʹ��)
     * @param item
     * @return �ͷŶ����Ƿ�ɹ�
     */
    private synchronized boolean releaseItem(T item){
        /**
         * ʵ��˼·�������ģ��Ȼ�ȡҪ�ͷŵĶ�����object pool�е����
         * Ȼ��checkOut״̬��Ϊfalse
         */
        int index = items.indexOf(item);
        if(index == -1){
            return false; // û�ҵ�Ҫ�ͷŵĶ���
        }
        /**
         * һ������Ҫ�ͷŵĶ����ڼ��״̬���ͰѼ��״̬��Ϊfalse
         */
        if(checkOut[index]){
            checkOut[index] = false;
            return true;
        }
        return false;
    }

    /**
     * ���ص�ǰobject pool�п��еĶ�������(checkout״̬Ϊfalse)
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
