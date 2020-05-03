package com.nbcb.thinkingInJava.concurrency.deadlock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����������ĳ���
 * Ҫģ���������Ҳ�ǳ�ֱ�ӣ���ֻҪ������ѧ��ģ�ͣ�����ѧ�ҷ�����Ӿ�����
 */
public class DeadlockingDiningPhilosopher {
    public static void main(String[] args) throws InterruptedException {
        int size = 5;

        /**
         * ����������Ե�����ѧ��˼����ʱ��
         * �������Ϊ0��˵����ѧ�Ҳ�˼����һֱ�ÿ��ӳԷ�
         * ���ֵ���õ�ԽС��Խ�������������ĳ���
         */
        int ponderFactor = 0;

        /**
         * ��ʼ������
         */
        List<Chopstick> chopsticks = new ArrayList<>(size);

        for(int i = 0 ; i < size; i++){
            chopsticks.add(new Chopstick());
        }

        /**
         * ��ʼ����ѧ��
         */
        List<Philosopher> philosophers = new ArrayList<Philosopher>(size);
        for(int i = 0 ; i < size; i++){
            Chopstick leftChopstick = chopsticks.get(i);
            Chopstick rightChopstick = chopsticks.get( (i + 1) % size ); // ���ȡ��һ��
            philosophers.add(new Philosopher(leftChopstick, rightChopstick, i, ponderFactor));
        }

        /**
         * ��ѧ���ǿ�ʼ˼��
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < size; i++){
            exec.execute(philosophers.get(i));
        }

        /**
         * ÿ��5s����ӡһ�¿��ӵ�״̬
         */
        while(true){
            Thread.sleep(5 * 1000);
            System.out.println("===================== start printing chopsticks info");
            for(int i = 0 ; i < size; i++){
                System.out.println("chopstick id: [" + i + "] taken info: " +
                        chopsticks.get(i).takenInfo());
            }

        }

    }
}
