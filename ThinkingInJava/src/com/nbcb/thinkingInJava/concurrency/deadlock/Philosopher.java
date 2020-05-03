package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.util.Random;

public class Philosopher implements Runnable{

    private Chopstick left;
    private Chopstick right;
    private int id;  // ��ѧ�ҵı��

    /**
     * �������������ѧ��˼����ʱ�䳤��
     * �������ֵȡ1����ô���1000ms��ȡһ�����������Ϊ�����ѧ�ҵ�˼��ʱ��
     * ���ֵԽ�󣬳�ʱ��˼���ĸ��ʾ�Խ��
     */
    private int ponderFactor;

    private Random rand = new Random(47);

    /**
     * ��ѧ��Ҫ˼��һ��ʱ��
     */
    public void pause() throws InterruptedException {
        if (ponderFactor == 0){
            return;
        }
        int pauseElapseTime = rand.nextInt(ponderFactor * 250);
        Thread.sleep(pauseElapseTime);
    }

    /**
     * constructor
     * @param left
     * @param right
     * @param id
     * @param ponderFactor
     */
    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }


    /**
     * ����߳�Ҫ�������飬������ѧ�Ҳ����ظ����¶�����
     * 1.˼��һ��ʱ��
     * 2.���Ӷ���
     * 3.������ߵĿ���
     * 4.�����ұߵĿ���
     */
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){

                /**
                 * ��˼��һ��ʱ��(˼��ʱ���������)
                 */
                System.out.println(this + " is thinking");
                long startThinking = System.currentTimeMillis();
                pause();
                System.out.println(this + " stop thinking for time cost: " +
                        ( System.currentTimeMillis() - startThinking) + " ms");

                /**
                 * Ȼ�������ұߵĿ���
                 */
                System.out.println(this + " grabbing right");
                right.take(this);

                /**
                 * Ϊ�˸���deadlock�������������ұ߿���֮����΢��һ���������ߵĿ���
                 */
//                pause();

                /**
                 * Ȼ��������ߵĿ���
                 */
                System.out.println(this + " grabbing left");
                left.take(this);

                /**
                 * �õ����Ӻ�װģ������һ���(�Է�ʱ��)
                 */
                System.out.println(this + " is eating");
//                pause();
                long startEating = System.currentTimeMillis();
                pause();
                System.out.println(this + " stop eating for time cost: " +
                        ( System.currentTimeMillis() - startEating) + " ms");
                /**
                 * ���ѿ��ӷ���
                 */
                right.drop(this);
                left.drop(this);
            }
        }catch(InterruptedException e){
            System.out.println("Philosopher Interrupted!");

        }
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public static void main(String[] args) {
        Random rand = new Random(47);
        int ponderFactor = 3;
        int pauseElapseTime;
        for(int i = 0 ; i < 100; i++){
            pauseElapseTime = rand.nextInt(ponderFactor * 1000);
            System.out.println(pauseElapseTime);

        }
    }

}



