package com.nbcb.thinkingInJava.concurrency.coperatingtasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 这个代码主要是借助汽车上蜡、擦拭这两个独立的线程
 * 说明线程间如何通信。关键字是notifyAll()
 * 因为有时候，线程之间执行的时候，是有先后顺序。
 * 这个例子里也是一样。WaxOff这个独立的线程，必须在WaxOn线程执行完成后才能执行
 *
 * 几个状态位
 * wax 打蜡
 * buff 用软布擦拭
 *
 * 几个方法
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
                 * 汽车上蜡完毕，更新状态位
                 */
                car.waxed();

                /**
                 * 汽车上蜡完毕，等待擦拭
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
                 * 擦拭钱，必须先等待上蜡
                 */
                car.waitingFofWaxed();
                System.out.println("Wax off： 上蜡完毕，准备擦拭！");

                /**
                 * 线程等待一段时间，模拟汽车擦拭的过程
                 */
                Thread.sleep(1000);
                /**
                 * 汽车擦拭完毕，更新状态位
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
         * 一段时间后，线程池关闭
         */
        Thread.sleep(5000);
        exec.shutdownNow();

    }

}
