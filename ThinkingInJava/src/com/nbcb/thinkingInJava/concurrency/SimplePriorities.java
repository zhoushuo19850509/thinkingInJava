package com.nbcb.thinkingInJava.concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个类主要为了说明如何设置线程的优先级
 */
public class SimplePriorities implements Runnable{
    private int priority;   // 优先级
    private int countDown = 5;  //
    private volatile double d;

    public SimplePriorities(int priority){
        this.priority = priority;
    }

    public String toString(){
        return Thread.currentThread() + ":" + countDown;
    }


    @Override
    public void run() {

        // 设置当前线程的优先级
        Thread.currentThread().setPriority(priority);

        try{
            while(true){
                // 做一个耗时的操作，以显示显示线程优先级的作用
//            for(int i = 0; i < 10000000; i++){
//                d += ( Math.PI + Math.E ) / (double)i;
//                if( (i % 1000) == 0 ){
//                    Thread.yield();    //
//                }
//            }

                for(int i = 0 ; i < 1000; i++){
                    Thread.sleep(20);
                    if( (i % 10) == 0 ){
                    Thread.yield();    //
                    }

                }
//                Thread.sleep(2000);
                System.out.println(this);
                if(--countDown == 0){
                    return;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        for( int i = 0 ; i < 5 ; i++){
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        System.out.println("All the low priority threads has started!!!");

//        for( int i = 0 ; i < 5 ; i++){
//            exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
//        }
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        System.out.println("All the high priority threads has started!!!");

        exec.shutdown();

    }



}
