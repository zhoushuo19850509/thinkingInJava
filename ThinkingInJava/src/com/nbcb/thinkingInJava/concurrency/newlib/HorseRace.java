package com.nbcb.thinkingInJava.concurrency.newlib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����ĳ�������Ҫ��Ϊ��˵�� CyclicBarrier ���÷�
 * CyclicBarrier��ʵ��CountDownLatch���
 * CyclicBarrier�ͺñ������������ڲ��е��ܣ�����ȵ���Щ����ȫ����ɺ�
 * ��������һ��������
 *
 * CyclicBarrier��CountDownLatch��ͬ�����ڣ�
 * 1.CyclicBarrier�����ظ�ʹ�ã���CountDownLatchֻ����һ�Σ�
 * 2.CyclicBarrier�����ʼ����ʱ�򣬿��Զ���Ķ�����
 *   �ȵ�һ��CyclicBarrier�����󣬻�ִ���������
 *
 * @ҵ�񳡾�
 * ������Կ����������������ӣ��ǳ���Ȥ
 */

/**
 * С��
 */
class Horse implements Runnable{

    private static CyclicBarrier cyclicBarrier;
    private int stride = 0 ;  // ��¼��С�����˶��ٲ���
    private static Random random = new Random(47);
    private static int count = 0 ;
    private int id = count++;


    public Horse(CyclicBarrier c){
        cyclicBarrier = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    stride += random.nextInt(3);   // С��ÿ����0/1/2��
                }
                cyclicBarrier.await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String toString() {
        return "Horse{" +
                "id=" + id +
                '}';
    }

    /**
     * С���Ѿ����˶��ٲ���(���岽��)
     * @return
     */
    public int getStride() {
        return stride;
    }

    /**
     * С���Ѿ����˶��ٲ���(��"*"չ�ֵ�С����)
     * @return
     */
    public String tracks(){
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStride(); i++) {
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }




}


/**
 * �ö�ƥС��Ҫ�����ˣ�
 */
public class HorseRace {


    static final int FINISH_LINE = 100;
    private CyclicBarrier barrier;
    private List<Horse> horses = new ArrayList<Horse>();
    ExecutorService exec = Executors.newCachedThreadPool();

    public void printALlHores(){
        for(Horse horse : horses){
            System.out.println(horse + " " + horse.getStride() );
        }
    }

    /**
     * constructor
     * 1.�����CyclicBarrierʵ����
     * 2.��������Horce�첽�߳�
     * @param nHorses  �ж���ƥС��μӱ���
     * @param pause û��һ����Ϣ����ʱ��(ms)
     */
    public HorseRace(int nHorses ,final int pause){

        /**
         * �ȶ����CyclicBarrier
         * CyclicBarrier��ʵ��һ���߳�
         * ����߳�run()�����лᶨ��Ҫ����һЩ����
         * ֻ����Щ����ȫ�����֮꣬ǰCyclicBarrier.await()�Ż᷵��
         */
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                /**
                 * �ȴ�ӡ�ܵ��ĳ���
                 */
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");
                }
                System.out.println(s.toString());

                /**
                 * ��ӡһ��С���ǵ�ǰ�Ľ���
                 */
                for(Horse horse: horses){
                    System.out.println(horse.tracks());
                }

                /**
                 * һ����С�����յ��ߣ���������������
                 * ��Ҫע�⣬�����ж�ƥС��ͬʱ�����յ�
                 */
                for(Horse horse: horses){
                    if(horse.getStride() >= FINISH_LINE){
                        System.out.println( horse + "won!");
                        exec.shutdownNow();
                        printALlHores();
                        return;
                    }

                }
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * С���ǿ�ʼ��������
         */
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }

    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        System.out.println("start run the horse");
        new HorseRace(nHorses, pause);

    }
}
