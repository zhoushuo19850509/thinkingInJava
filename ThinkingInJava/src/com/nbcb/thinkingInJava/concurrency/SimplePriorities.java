package com.nbcb.thinkingInJava.concurrency;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �������ҪΪ��˵����������̵߳����ȼ�
 */
public class SimplePriorities implements Runnable{
    private int priority;   // ���ȼ�
    private int countDown = 5;  //
    private volatile double d;

    public SimplePriorities(int priority){
        this.priority = priority;
    }

    public String toString(){
        return Thread.currentThread() + ":" + countDown + " with priority: [" + Thread.currentThread().getPriority() + "]";
    }


    @Override
    public void run() {

        // ���õ�ǰ�̵߳����ȼ�
        Thread.currentThread().setPriority(priority);

        try{
            while(true){
                // ��һ����ʱ�Ĳ���������ʾ��ʾ�߳����ȼ�������
            for(int i = 0; i < 100000000; i++){
                d += ( Math.PI + Math.E ) / (double)i;
                if( (i % 1000) == 0 ){
                    Thread.yield();    //
                }
            }

//                for(int i = 0 ; i < 500; i++){
//                    Thread.sleep(20);
//                    if( (i % 10) == 0 ){
//                    Thread.yield();    //
//                    }
//
//                }
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
