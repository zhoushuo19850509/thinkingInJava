package com.nbcb.thinkingInJava.concurrency;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * �����Ҫ��DaemonThreadFactory��������һ��
 * ��Ҫ��ʾ�����ͨ������һ��newCachedThreadPool
 * Ȼ��ͨ�����thread pool������thread�Զ���Ϊdaemon thread
 *
 * ����֪����һ��main thread�����������main thread������daemon thread���Զ�����
 * ��ˣ�����������ഴ����newCachedThreadPool�ͺ��������ˡ�
 */
public class DaemonFromFactory implements Runnable{
    @Override
    public void run() {
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
             *����һ���̳߳�
             * ͨ������̳߳ش������̣߳�����daemon thread
             */
            ExecutorService exec = Executors.newCachedThreadPool(
                    new DaemonThreadFactory());

//            ExecutorService exec = Executors.newCachedThreadPool();

            /**
             * ����ȥ���������ɸ�DaemonFromFactory�߳�
             * ע��Ŷ��ͨ��exec�̳߳��������̣߳����Զ�ת��Ϊdaemon thread��Ŷ
             */
            for(int i = 0 ; i < 10 ; i++){
                exec.execute(new DaemonFromFactory());

            }
            System.out.println("all daemons started!");
            TimeUnit.MILLISECONDS.sleep(250);

            System.out.println("main thread finished!");
        }catch (InterruptedException e){
            e.printStackTrace();
        }




    }
}
