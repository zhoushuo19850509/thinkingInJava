package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * ����ļ���Ҫ��Ϊ��˵���߳���finally{}�ڵĴ����п��ܲ���ִ��
 * ģ��ķ�����Ҫ�ǣ�
 * 1.�����̴���һ�����̣߳�������߳�Ϊdaemon thread
 * 2.���������Ͻ��������������߳�Ҳ����ֹͣ��
 * 3.�Թ۲����̵߳�finally�Ǳ�ִ��
 *
 */
public class DaemonDontRunFinally {
    public static void main(String[] args){
        Thread t = new Thread(new ADaomon());

        /**
         * ������������ADaemon����ΪDaemon thread
         */
        t.setDaemon(true);

        t.start();

        /**
         * Ȼ�����߳�����ֹͣ���������߳�Ҳ����ֹͣ��
         * �Թ۲����̵߳�finally�Ǳ�ִ��
         */
    }

}

class ADaomon implements Runnable{

    @Override
    public void run() {
        try{
            System.out.println("Start ADaemon!");
            TimeUnit.SECONDS.sleep(1);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("finally executed!");
        }
    }
}

