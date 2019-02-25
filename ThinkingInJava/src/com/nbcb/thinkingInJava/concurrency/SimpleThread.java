package com.nbcb.thinkingInJava.concurrency;

/**
 * 这个文件主要是说明通过继承Thread类，使用线程的用法
 */
public class SimpleThread extends Thread{

    /**
     * 这个私有的变量用来控制当前线程的循环打印次数
     */
    private int countDown = 5;
    /**
     * 这个全局的计数变量用来标识类的名称
     */
    private static int threadCount = 0;

    /**
     * constructor
     */
    public SimpleThread(){

        /**
         * 通过调用父类Thread 的constructor，设置类的名称
         */
        super(Integer.toString(++threadCount));
        start();
    }

    public String toString(){
        return "#" + getName() + "(" + countDown + ") ";
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
            new SimpleThread();
        }

    }
}
