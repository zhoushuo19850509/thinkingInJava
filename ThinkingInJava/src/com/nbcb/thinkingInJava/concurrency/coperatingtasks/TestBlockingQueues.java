package com.nbcb.thinkingInJava.concurrency.coperatingtasks;


import com.nbcb.thinkingInJava.concurrency.LiftOff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 这个文件，主要是为了验证BlockingQueue的功能
 *
 * 所谓的BlockingQueue，其实就是一个支持并发访问的队列
 * 提供一些take()/put()等方法，往queue中存取元素
 *
 * BlockingQueue接口的具体实现类有：
 * 1.LinkedBlockingQueue
 * 2.ArrayBlockingQueue
 * 3.SynchronousQueue
 *
 */

class LiftOffRunner implements Runnable{

    private BlockingQueue<LiftOff> rockets;

    /**
     * constructor
     * @param rockets
     */
    public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
        this.rockets = rockets;
    }

    /**
     * add()方法往BlockingQueue中添加元素
     * 特别注意：因为这里调用的是BlockingQueue.put()方法
     * 因此，如果BlockingQueue的实现类是ArrayBlockingQueue这种fixed size queue
     * 那么，当BlockingQueue队列满的时候，就需要阻塞等待
     * @param lo
     */
    public void add(LiftOff lo){
        try {
            rockets.put(lo);
        } catch (InterruptedException e) {
            System.out.println("Interrupted when" +
                    " putting element into the queue ...");
        }
    }

    /**
     * 异步线程要做的事情，就是从BlockingQueue中不断获取元素
     * 特别注意：因为这里调用的是BlockingQueue.take()方法
     * 因为，如果Queue队列为空的话，这里的take()方法会一直阻塞在这里
     * 直到queue中有新的可以获取的元素为止
     */
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                LiftOff liftOff = rockets.take();
                liftOff.run();
            }
        }catch (InterruptedException e){
            System.out.println("Interrupted during taking from the queue ...");
        }

    }
}

public class TestBlockingQueues {

    /**
     * 这个方法的意思是，要等待用户在console中输入一些东西
     * 然后主线程才能往下走
     */
    static void getKey(){
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void getKey(String msg){
        System.out.println(msg);
        getKey();
    }

    /**
     * 封装一个static test方法，
     * 可以测试各种不同的BlockingQueue
     * @param msg 主要是指定BlockingQueue的名称
     * @param queue BlockingQueue的具体实现类对象
     */
    static void test(String msg, BlockingQueue<LiftOff> queue){
        // 先打印一下BlockingQueue的实现类
        System.out.println("msg: " + msg );

        /**
         * 启动LiftOffRunner异步线程
         * 备注：启动LiftOffRunner异步线程之后，
         * run()方法就会一直从queue中获取元素
         */
        LiftOffRunner liftOffRunner = new LiftOffRunner(queue);
        Thread t = new Thread(liftOffRunner);
        t.start();

        // 然后调用LiftOffRunner.add()，往BlockingQueue中添加元素
        for (int i = 0; i < 5; i++) {
             liftOffRunner.add(new LiftOff());
        }

        // 然后等待用户输入
        getKey("Press 'Enter' (" + msg + ")");

        // 等待用户输入完成后，关闭LiftOffRunner异步线程
        t.interrupt();
        System.out.println("finish the test of : " + msg);

    }

    public static void main(String[] args) {

//        test("LinkedBlockingQueue", new LinkedBlockingQueue<>());
//        test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));
        test("SynchronousQueue", new SynchronousQueue<>());
    }
}
