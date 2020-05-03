package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.nio.channels.Pipe;

/**
 * �����������
 * ����������������
 * 1.���ӱ�����
 * 2.���ӱ�����
 */
public class Chopstick {

    /**
     * ��ǰ����ʹ�ÿ��ӵ���ѧ��
     */
    private Philosopher philosopher;

    private boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while (taken){
            wait();
        }
        taken = true;
    }

    public synchronized void drop(){
        taken = false;
        notifyAll();
    }


    /**
     * ����������take()/drop()���������治֮ͬ������
     * ��¼�˵�ǰʹ�ÿ��ӵ���ѧ��
     * @param philosopher
     * @throws InterruptedException
     */
    public synchronized void take(Philosopher philosopher) throws InterruptedException {
        while (taken){
            wait();
        }
        taken = true;
        this.philosopher = philosopher;
    }

    public synchronized void drop(Philosopher philosopher){
        taken = false;
        this.philosopher = null;
        notifyAll();
    }

    /**
     * ��¼һ�£�����������ڵ�ʹ�����
     * @return
     */
    public String takenInfo(){
        if(taken){
            return "the chopstick is taken by philosopher: " + this.philosopher.getId();
        }else{
            return "the chopstick is not taken by anyone";
        }
    }
}
