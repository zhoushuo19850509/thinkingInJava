package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.util.Random;

public class Philosopher implements Runnable{

    private Chopstick left;
    private Chopstick right;
    private int id;  // 哲学家的编号

    /**
     * 这个参数代表哲学家思考的时间长度
     * 比如这个值取1，那么会从1000ms中取一个随机数，作为这个哲学家的思考时间
     * 这个值越大，长时间思考的概率就越大
     */
    private int ponderFactor;

    private Random rand = new Random(47);

    /**
     * 哲学家要思考一段时间
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
     * 这个线程要做的事情，就是哲学家不断重复如下动作：
     * 1.思考一段时间
     * 2.肚子饿了
     * 3.拿起左边的筷子
     * 4.拿起右边的筷子
     */
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){

                /**
                 * 先思考一段时间(思考时间是随机的)
                 */
                System.out.println(this + " is thinking");
                long startThinking = System.currentTimeMillis();
                pause();
                System.out.println(this + " stop thinking for time cost: " +
                        ( System.currentTimeMillis() - startThinking) + " ms");

                /**
                 * 然后尝试拿右边的筷子
                 */
                System.out.println(this + " grabbing right");
                right.take(this);

                /**
                 * 为了复现deadlock，我们在拿起右边筷子之后，稍微等一会儿再拿左边的筷子
                 */
//                pause();

                /**
                 * 然后尝试拿左边的筷子
                 */
                System.out.println(this + " grabbing left");
                left.take(this);

                /**
                 * 拿到筷子后，装模作样吃一会儿(吃饭时间)
                 */
                System.out.println(this + " is eating");
//                pause();
                long startEating = System.currentTimeMillis();
                pause();
                System.out.println(this + " stop eating for time cost: " +
                        ( System.currentTimeMillis() - startEating) + " ms");
                /**
                 * 最后把筷子放下
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



