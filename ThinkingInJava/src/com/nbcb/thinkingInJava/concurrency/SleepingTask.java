package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SleepingTask extends LiftOff{

    // 重载LiftOff.run()的方法
    // 和之前的run()方法不同的是，当前线程每次打印status()之后，sleep一段时间
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
