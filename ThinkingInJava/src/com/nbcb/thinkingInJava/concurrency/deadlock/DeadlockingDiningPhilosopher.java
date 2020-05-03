package com.nbcb.thinkingInJava.concurrency.deadlock;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 会造成死锁的场景
 * 要模拟这个场景也非常直接，就只要按照哲学家模型，给哲学家分配筷子就行了
 */
public class DeadlockingDiningPhilosopher {
    public static void main(String[] args) throws InterruptedException {
        int size = 5;

        /**
         * 这个参数可以调整哲学家思考的时间
         * 如果设置为0，说明哲学家不思考，一直拿筷子吃饭
         * 这个值设置得越小，越容易重现死锁的场景
         */
        int ponderFactor = 0;

        /**
         * 初始化筷子
         */
        List<Chopstick> chopsticks = new ArrayList<>(size);

        for(int i = 0 ; i < size; i++){
            chopsticks.add(new Chopstick());
        }

        /**
         * 初始化哲学家
         */
        List<Philosopher> philosophers = new ArrayList<Philosopher>(size);
        for(int i = 0 ; i < size; i++){
            Chopstick leftChopstick = chopsticks.get(i);
            Chopstick rightChopstick = chopsticks.get( (i + 1) % size ); // 这个取余一下
            philosophers.add(new Philosopher(leftChopstick, rightChopstick, i, ponderFactor));
        }

        /**
         * 哲学家们开始思考
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < size; i++){
            exec.execute(philosophers.get(i));
        }

        /**
         * 每隔5s，打印一下筷子的状态
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
