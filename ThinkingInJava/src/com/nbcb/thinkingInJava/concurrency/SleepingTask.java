package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SleepingTask extends LiftOff{

    // ����LiftOff.run()�ķ���
    // ��֮ǰ��run()������ͬ���ǣ���ǰ�߳�ÿ�δ�ӡstatus()֮��sleepһ��ʱ��
    public void run(){

        try{
            while(countDown-- > 0){
                System.out.println(status());
                Thread.sleep(100);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for( int i = 0 ; i < 5 ; i++){
            exec.execute(new SleepingTask());
        }
        exec.shutdown();

        System.out.println("All the threads has started!!!");
    }

}
