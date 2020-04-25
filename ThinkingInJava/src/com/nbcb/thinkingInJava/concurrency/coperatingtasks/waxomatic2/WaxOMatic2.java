package com.nbcb.thinkingInJava.concurrency.coperatingtasks.waxomatic2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WaxOMatic2.java主要是在WaxOMatic.java基础上进行了优化
 * 把Car对象中那些使用了synchronized关键字的方法，
 * 改为1.Lock进行加锁处理； 2.condition进行线程等待处理;3.finally中加入锁的释放
 * 这样的好处是显而易见的。就是不用对整个方法进行同步了。
 * 只需要对需要同步的代码块(不同线程会同时处理的变waxOn)加锁就行了
 */


class Car{
    private boolean waxOn = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * 上蜡完毕的动作
     * 将waxOn设置为true
     * 同时将这个状态通知所有的线程
     */
    public void waxed(){
        lock.lock();
        try{
            waxOn = true;
            condition.signal();
        }finally {
            lock.unlock();
        }

    }

    /**
     * 擦拭完毕的动作
     * 将waxOff设置为false
     * 同时将这个状态通知所有的线程
     */
    public void buffed(){
        lock.lock();
        try{
            waxOn = false;
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * WaxOff等待WaxOn擦拭完毕
     * @throws InterruptedException
     */
    public void waitingFofWaxed() throws InterruptedException {
        lock.lock();
        try{
            while(waxOn == false){
                condition.await();
            }
        }finally {
            lock.unlock();
        }
    }

    /**
     * WaxOn完成打蜡后，等待WaxOff擦拭完毕
     * @throws InterruptedException
     */
    public void waitingForBuffed() throws InterruptedException {
        lock.lock();
        try{
            while(waxOn == true){
                condition.await();
            }
        }finally {
            lock.unlock();
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


public class WaxOMatic2 {

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
