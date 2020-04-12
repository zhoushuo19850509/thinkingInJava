package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����ļ�Ҫ��SerialNumberGenerator.java�������һ��
 * ��Ҫ��Ϊ����֤���к�������(SerialNumberGenerator)�ǲ����̰߳�ȫ��.
 * ���ԭ����ʵ�ǳ�ֱ�ӣ������������ɸ��̣߳�ÿ���̷ֱ߳�������к�����������һ������
 * Ȼ��ŵ�һ����ͬ�������У�����ж��Ƿ����ظ������к�
 *
 * �����������������Ȼ�ǲ���ȫ�ġ�
 * ��ô����أ���򵥵ķ�����Ȼ�����������кŵ�ʱ�����synchronized�ؼ��֣�
 *
 * �Ժ�����������������к����ɵ�����Ҫ�ر��ע�߳��Ƿ�ȫ�����������ģ�������м�⡣
 *

 */

/**
 * CircularSet��Ҫ����������һ�����ε�����
 */
class CircularSet{
    private int[] array;
    private int len;
    private int index;

    public CircularSet(int size) {
        array = new int[size];
        this.len = size;

        /**
         * �����������ʼ��һ��
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
 * �����ģ��һ���������߳�
 * �߳��������������һ����������(CircularSet��ȫ�ֵ�ʵ��)�в���push ���к�
 * ���к���SerialNumberGenerator����
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;   // ��Ҫ������SerialNumberChecker�߳���
    private static CircularSet serails = new CircularSet(1000);

    private static ExecutorService exec = Executors.newCachedThreadPool();
    static class SerialChecker implements Runnable{

        @Override
        public void run() {
            while(true){
                int serail = SerialNumberGenerator.nextSerialNumber();
                if(serails.contains(serail)){
                    System.out.println("Duplicate: " + serail);
                    System.exit(0);   // ��ǰ�߳��˳�
                }
                serails.add(serail);   // ���û���ظ����ݣ��������������в�������
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
