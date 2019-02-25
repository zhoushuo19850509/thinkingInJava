package com.nbcb.thinkingInJava.concurrency;

/**
 * ����ļ���Ҫ��˵��ͨ��ʵ��Runnable��ʹ���̵߳��÷�
 * �����SimpleThread.javaһ����Ͽ�
 */
public class SelfManaged implements Runnable{

    private int countDown = 5;

    /**
     * constructor
     * ��constructor�У�������ǰ�߳�
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
