package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �������˵�����ͨ��Thread local�ķ�ʽ
 * ��֤������Դ�ڸ����߳��ж��Ƕ��������
 * �ӳ������н�����Կ�����5���߳��е�Integer�����Ƿֱ��ۼӵ�
 * ��Ȼ�����ǵ����߳�Ҳ��һ���������̣߳���5�����̲߳�һ��
 * ����ͨ��ThreadLocalVariableHolder.get()��ȡ�ı�����ȻҲ�Ƕ�����
 */

/**
 * Accessor�ǵ����Ľ���
 * ÿ�����̶��᲻��ѭ������ThreadLocalVariableHolder
 * ���ϵ������Ǳ�����Thread local��Integer
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
 * �����˵�������ͨ��Thread local������֤���ǵ�Integer���̰߳�ȫ��
 * һ��ʼ�����Integer��һ�������
 * ������ͨ��increment()�������ϼ�1��Ȼ��ͨ��get()������ȡ��ǰInteger��ֵ
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
         * ����5��Accesoor����
         */
        for(int i = 0 ; i < 5; i++){
            exec.execute(new Accessor(i));
        }

        /**
         * һ��ʱ���Ժ��ٰѸ������̹ر�
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
