package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * ����ļ���Ҫ��Ϊ��˵���������飺
 * 1.Thread.isDaemon()�������Լ���thread�Ƿ�Ϊdaemon thread
 * 2.������߳�Ϊdaemon thread����ô���߳�Ҳ��daemon thread��
 */


/**
 * main class
 */
public class Daemons {
    public static  void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Daemon());
        t.setDaemon(true);
        t.start();
        System.out.println("t.isDaemon() = " + t.isDaemon());

        /**
         * ���̵߳ȴ�1�����Ժ���ֹͣ��ȿ������̵߳ı���
         */
        TimeUnit.SECONDS.sleep(1);
    }
}


/**
 * ����10��DaemonSpawn �̣߳�����ͨ��isDaemon()�����������Щ�߳��Ƿ�Ϊdaemon thread
 */
class Daemon implements  Runnable{
    private Thread[] t = new Thread[10];

    @Override
    public void run() {
        for( int i = 0 ; i < t.length; i++){
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("Spawn thread " + i + " started!");
        }

        for(int i = 0 ; i < t.length; i++ ){
            System.out.println("Spawn thread " + i + " .isDaemon() = " + t[i].isDaemon());

        }
        while (true){
            Thread.yield();
        }
    }
}

/**
 * �߳�һ�������������ó���Դ
 */
class DaemonSpawn implements Runnable{

    @Override
    public void run() {
        while(true){
            Thread.yield();
        }

    }
}

