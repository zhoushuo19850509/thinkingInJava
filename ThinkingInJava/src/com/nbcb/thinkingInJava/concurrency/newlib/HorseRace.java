package com.nbcb.thinkingInJava.concurrency.newlib;


import sun.security.ssl.HandshakeOutStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 赛马的场景，主要是为了说明 CyclicBarrier 的用法
 * CyclicBarrier其实和CountDownLatch差不多
 * 就是很多线程在跑，但是所有的线程要等待CyclicBarrier中定义的动作，只有等待CyclicBarrier中定义的动作完成之后，各个线程才能开始继续跑
 * 区别在于，CyclicBarrier可以重复使用
 *
 * 具体可以看下面这个赛马的例子，非常有趣
 */

/**
 * 小马
 */
class Horse implements Runnable{

    private static CyclicBarrier cyclicBarrier;
    private int stride = 0 ;  // 记录了小马跑了多少步了
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
                    stride += random.nextInt(3);   // 小马每次跑0/1/2步
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
     * 小马已经跑了多少步了(具体步数)
     * @return
     */
    public int getStride() {
        return stride;
    }

    /**
     * 小马已经跑了多少步了(以"*"展现的小马步伐)
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
 * 好多匹小马要比赛了！
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
     * @param nHorses  有多少匹小马参加比赛
     * @param pause 没跑一次休息多少时间(ms)
     */
    public HorseRace(int nHorses ,final int pause){

        /**
         * 先定义好CyclicBarrier
         * CyclicBarrier其实是一个线程
         * 这个线程run()方法中会定义要做的一些事情
         * 只有这些事情全部做完，之前CyclicBarrier.await()才会返回
         */
        barrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                /**
                 * 先打印跑道的长度
                 */
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    s.append("=");
                }
                System.out.println(s.toString());

                /**
                 * 打印一下小马们当前的进度
                 */
                for(Horse horse: horses){
                    System.out.println(horse.tracks());
                }

                /**
                 * 一旦有小马到达终点线，就宣布比赛结束
                 * 需要注意，可能有多匹小马同时到达终点
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
         * 小马们开始跑起来！
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
