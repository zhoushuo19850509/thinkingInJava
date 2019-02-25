package com.nbcb.thinkingInJava.concurrency;

/**
 * ����ļ���Ҫ��˵��ͨ���̳�Thread�࣬ʹ���̵߳��÷�
 */
public class SimpleThread extends Thread{

    /**
     * ���˽�еı����������Ƶ�ǰ�̵߳�ѭ����ӡ����
     */
    private int countDown = 5;
    /**
     * ���ȫ�ֵļ�������������ʶ�������
     */
    private static int threadCount = 0;

    /**
     * constructor
     */
    public SimpleThread(){

        /**
         * ͨ�����ø���Thread ��constructor�������������
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
