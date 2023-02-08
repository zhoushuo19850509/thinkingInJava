package com.nbcb.thinkingInJava.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * 这个test类，主要用来验证SynchronousQueue的用法
 * 首先，从字面理解，这个队列是线程安全的。但是，这个类虽然名字是queue队列，
 * 但是这个队列的容量是0，也就是说，这个队列啥元素都没法保存
 * 每次调用队列的take()方法，必须wait for put()方法完成后才能执行；
 * 反过来也是一样：每次调用队列的put()方法，必须wait for take()方法完成后才能执行
 *
 * 这就相当于一个中转站，用于不同线程之间交换元素，有点像是cyclic barrier
 *
 * 实现的效果就是保证两个异步线程(比如例子中的Producer/Consumer)之间单个元素的交换。
 *
 * 除了元素交换SynchronousQueue还实现了两个异步线程之间的同步，
 * 比如例子中的Producer/Consumer，无论他们循环间隔sleep多少时间，都是保持同步的
 * 这是通过SynchronousQueue实现的
 */
public class SynchronousQueueTest {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue();

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable producer = new Runnable() {
            private int id = 0;
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()){
                        /**
                         * Producer每个1秒钟往队列中添加递增的id
                         */
                        String currentId = String.valueOf(id++);
                        System.out.println("producer putting element :" + currentId);
                        queue.put(currentId);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    System.out.println("producer Interrupted ...");
                }
            }
        };


        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    while(!Thread.interrupted()){
//                    System.out.println("consumer running ...");
                        String element = queue.take();
                        System.out.println(
                                "consumer take element from the queue: " + element);
                        Thread.sleep(2000);
                    }

                } catch (InterruptedException e) {
                    System.out.println("consumer Interrupted ...");
                }

            }
        };


        executorService.submit(producer);
        executorService.submit(consumer);

        try {
            Thread.sleep(10 * 1000);
            executorService.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
