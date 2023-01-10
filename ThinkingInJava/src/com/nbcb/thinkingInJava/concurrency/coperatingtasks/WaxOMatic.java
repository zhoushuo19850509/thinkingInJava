package com.nbcb.thinkingInJava.concurrency.coperatingtasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 这个代码主要是借助汽车上蜡、擦拭这两个独立的线程
 * 说明线程间如何通信。关键字是wait()/notifyAll()
 * 因为有时候，线程之间执行的时候，是有先后顺序。
 * 这个例子里也是一样。WaxOff这个独立的线程，必须在WaxOn线程执行完成后才能执行
 *
 * @业务场景
 * 这个代码说明业务场景是这样的
 * 有两个独立的异步线程：
 * WaxOn/WaxOff
 * 其中WasOn线程是给汽车上蜡
 * WaxOff线程是给汽车抛光
 *
 * 两个异步线程虽然是独立的，但是有先后关系：
 * 必须先执行完WaxOn上蜡，才能执行WaxOff线程给汽车抛光
 * WaxOff线程执行完后，才执行WaxOn线程
 * 这样不断循环，直到主线程退出
 *
 * @技术点
 * 本案例的技术点就是wait()/notifyAll()
 * 其中wait()是等待其他线程，更新某个字段
 * notifyAll()是当前线程更新某个字段之后，通知其他线程
 *
 * 几个状态位
 * wax 打蜡
 * buff 用软布擦拭(抛光)
 *
 * Car对象主要的几个方法
 * waxed() --上蜡完毕
 * buffed() --擦拭完毕
 * waitingForWaxed() --等待上蜡
 * waitingForBuffed()  --等待擦拭
 *
 */


class Car{
    private boolean waxOn = false;

    /**
     * 上蜡完毕的动作
     * 将waxOn设置为true
     * 同时将这个状态通知所有的线程
     */
    public synchronized void waxed(){
        waxOn = true;
        notifyAll();
    }

    /**
     * 擦拭完毕的动作
     * 将waxOff设置为false
     * 同时将这个状态通知所有的线程
     */
    public synchronized void buffed(){
        waxOn = false;
        notifyAll();
    }

    /**
     * WaxOff等待WaxOn擦拭完毕
     * @throws InterruptedException
     */
    public synchronized void waitingFofWaxed() throws InterruptedException {
        while(waxOn == false){
            wait();
        }
    }

    /**
     * WaxOn完成打蜡后，等待WaxOff擦拭完毕
     * @throws InterruptedException
     */
    public synchronized void waitingForBuffed() throws InterruptedException {
        while(waxOn == true){
            wait();
        }
    }
}


/**
 * 独立的线程，这个线程负责给汽车上蜡
 */
class WaxOn implements Runnable{
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                System.out.println("Wax on: 给汽车上蜡！");

                /**
                 * 线程等待一段时间，模拟汽车上蜡的过程
                 */
                Thread.sleep(1000);

                /**
                 * 汽车上蜡完毕，
                 * 1.更新状态位waxOn
                 * 2.然后调用notifyAll()方法通知其他线程：waxOn状态已经更新
                 */
                car.waxed();

                /**
                 * 汽车上蜡完毕，等待擦拭
                 *
                 */
                car.waitingForBuffed();

            }
        } catch (InterruptedException e) {
//                e.printStackTrace();
            System.out.println("interrupted while wax on !");
        }

        System.out.println("finish wax on");
    }
}


/**
 * 独立的线程，这个线程负责给汽车擦拭
 * 特别注意，这个线程必须等待WaxOn完成擦拭后才能执行
 */
class WaxOff implements Runnable{
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {

            while(!Thread.interrupted()){
                /**
                 * 擦拭前，必须先等待上蜡
                 * waitingFofWaxed()主要是通过调用wait()方法
                 * 监听waxOn字段的状态，一旦waxOn字段置为true，监听就结束
                 */
                car.waitingFofWaxed();
                System.out.println("Wax off： 上蜡完毕，准备擦拭！");

                /**
                 * 线程等待一段时间，模拟汽车擦拭的过程
                 */
                Thread.sleep(1000);
                /**
                 * 汽车擦拭完毕，更新状态位
                 * buffed()执行的动作包括：
                 * 1.将waxOn置为false
                 * 2.调用notifyAll()方法通知其他线程：waxOn状态置为false了，可以继续打蜡了
                 */
                car.buffed();
            }
        } catch (InterruptedException e) {
//                e.printStackTrace();
            System.out.println("interrupted while wax off !");
        }

        System.out.println("finish wax off");
    }
}

/**
 * main class
 */
public class WaxOMatic {

    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        WaxOn waxOn = new WaxOn(car);
        WaxOff waxOff = new WaxOff(car);

        /**
         * 启动两个单独的线程
         */
        exec.execute(waxOff);
        exec.execute(waxOn);

        /**
         * sleep一段时间后，线程池关闭
         */
        Thread.sleep(5000);
        exec.shutdownNow();
    }

}
