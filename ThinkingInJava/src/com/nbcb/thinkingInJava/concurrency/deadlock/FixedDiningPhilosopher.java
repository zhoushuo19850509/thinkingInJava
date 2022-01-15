package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �����������İ汾
 * ��α��������أ�
 * ��������������������������������Щ��
 * 1.�����ж�ռ����Դ(������ӣ�һ������û�취ͬʱ��������ѧ��������)
 * 2.��Դ��ռ�к󣬲��ܱ�����������(������ӱ�һ����ѧ��������֮�󣬲��ܱ�������ѧ������)
 * 3.�����������ĳ�����һ����ռ����ԴA֮������ռ����ԴB
 * 4.�����л���ռ����Դ�ĳ���������A��Ҫռ��B����Դ��B��Ҫռ��C��Դ��C��Ҫռ��A����Դ
 *
 * Ϊ�˱���������ֻҪ�������һ�������
 * ����ѡ����ĵ㣬���ƻ���ռ����Դ�ĳ���
 *
 * ˼·Ҳ��ֱ�ӣ����Ǹı����һ����ѧ���ÿ��ӵķ�ʽ����Ϊ��
 * �����ұߵĿ��ӣ�������ߵ�
 * �����ͱ�����ѭ������
 *
 *
 */
public class FixedDiningPhilosopher {

    public static void main(String[] args) throws InterruptedException {
        int size = 5;

        /**
         * ����������Ե�����ѧ��˼����ʱ��
         * �������Ϊ0��˵����ѧ�Ҳ�˼����һֱ�ÿ��ӳԷ�
         * ���ֵ���õ�ԽС��Խ�������������ĳ���
         * ��Ȼ����������Ǿ��������Ż��ģ������������Ĳ��������Լ�������Ϊ0��Ҳ������������
         */
        int ponderFactor = 1;

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
        Chopstick leftChopstick = null;
        Chopstick rightChopstick = null;
        for(int i = 0 ; i < size; i++){
            /**
             * 1-4����ѧ���ÿ��ӻ���ԭ���ķ�ʽ��������߿��ӣ������ұ߿���
             */
            if( i < size - 1){
                leftChopstick = chopsticks.get(i);
                rightChopstick = chopsticks.get( i + 1); // ���ȡ��һ��
            }else{
                /**
                 * ���һ����ѧ�Ҹı�һ�£������ұߵĿ��ӣ�������ߵ�
                 */
                leftChopstick = chopsticks.get(0);
                rightChopstick = chopsticks.get(i);
            }
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
            Thread.sleep(1 * 1000);
            System.out.println("===================== start printing chopsticks info");
            for(int i = 0 ; i < size; i++){
                System.out.println("chopstick id: [" + i + "] taken info: " +
                        chopsticks.get(i).takenInfo());
            }

        }
    }
}