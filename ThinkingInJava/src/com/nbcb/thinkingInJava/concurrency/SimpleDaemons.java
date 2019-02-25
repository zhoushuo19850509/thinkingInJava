package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * ����ļ���Ҫ��˵��Daemon thread���÷�
 * Daemon thread�����ڱ����������߳�ֹͣ��ʱ��Daemon threadҲ��ֹ֮ͣ��
 */
public class SimpleDaemons implements Runnable {

    /**
     * Daemon thread��������ǳ��򵥣�����ÿ��100�����ӡһ��
     */
    public void run(){
        try{
            while(true){
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        try{
            /**
             * ������10��daemon threads
             */
            for(int i = 0 ; i < 10 ; i++){
                Thread t = new Thread(new SimpleDaemons());

                t.setDaemon(true);
                t.start();
            }
            /**
             * Ȼ�����߳�(main())slepp һ��ʱ�䣬����daemon threads������Щɶ
             */
            TimeUnit.MILLISECONDS.sleep(175);
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
