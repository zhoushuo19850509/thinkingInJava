package com.nbcb.thinkingInJava.concurrency;

/**
 * 这个文件主要是说明通过实现Runnable，使用线程的用法
 * 建议和SimpleThread.java一起配合看
 */
public class SelfManaged implements Runnable{

    private int countDown = 5;

    /**
     * constructor
     * 在constructor中，启动当前线程
     */
    public SelfManaged(){
        Thread t = new Thread(this);
        t.start();
    }

    public String toString(){
        return Thread.currentThread().getName() + "(" + countDown + ") ";
    }

    public void run(){
        while(true){
            System.out.print(this);
            if(--countDown == 0){
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args){
        for(int i = 0 ; i < 5 ;i++){
            new SelfManaged();
        }

    }

}
