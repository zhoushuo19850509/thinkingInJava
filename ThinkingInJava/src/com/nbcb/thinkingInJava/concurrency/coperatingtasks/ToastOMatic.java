package com.nbcb.thinkingInJava.concurrency.coperatingtasks;


/**
 *
 * 这个代码的核心也是BlockingQueue这个适应并发场景的数据结构
 * 在TestBlockingQueues.java的基础上更近一步
 * TestBlockingQueues还是以BlockingQueue语法为主，业务场景不多
 * 本代码就是基于一个非常有趣的业务场景
 *
 * 这个例子也说明了BlockingQueue的强大之处：
 * 无需通过wait()/notify()/notifyAll()
 * 这些方法手工通知各个异步线程
 * 这些步骤都封装在BlockingQueue中了.
 * 后续我们感兴趣的话，可以看看LinkedBlockingQueue这个类的实现原理
 * 其实内部也是wait()/notify()机制
 *
 * @业务场景
 * 有若干吐司需要处理，
 * 然后每块吐司的流程是这样的：
 * 1.制作新的(未经处理的)吐司
 * 2.给吐司涂上奶油
 * 3.给吐司涂上果酱
 * 4.顾客吃掉吐司
 *
 * 这4个步骤是有先后顺序的，并且分别由4个独立的线程完成
 * 每个线程都会有一个专属的BlockingQueue队列，分别是：
 * 1.刚制作出来的(未经处理的)吐司放到队列1
 * 2.涂上奶油的吐司放到队列2
 * 3.涂上果酱的吐司(完成任务)放到队列3
 * 4.顾客会一个一个吃掉吐司
 */

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * class1 Toast吐司面包的类
 * 包含吐司的一些属性、状态啥的
 */
class Toast{
    /**
     * 吐司状态的枚举值，包括： (未经处理的)干吐司、涂上了奶油、涂上了果酱
     */
    public enum Status {DRY, BUTTERED, JAMMED}

    /**
     * 当前吐司的状态
     */
    private Status status = Status.DRY;

    /**
     * 当前吐司对象的编号
     */
    private final int id;

    /**
     * constructor of Toast
     * @param id
     */
    public Toast(int id) {
        this.id = id;
    }

    /**
     * 给吐司涂上奶油
     */
    public void butter(){
        this.status = Status.BUTTERED;
    }

    public void jam(){
        this.status = Status.JAMMED;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Toast{" +
                "status=" + status +
                ", id=" + id +
                '}';
    }
}

/**
 * 定义一个队列
 * 这个队列继承了LinkedBlockingQueue
 * 队列中的元素就是Toast对象
 */
class ToastQueue extends LinkedBlockingQueue<Toast>{

}

/**
 * 线程1 这个线程的工作是：
 * 制作出来的(未经处理的)吐司放到队列1 : toastQueue
 *
 */
class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int counter = 0;
    private Random random = new Random(47);


    /**
     * constructor
     * @param toastQueue
     */
    public Toaster(ToastQueue toastQueue) {
        this.toastQueue = toastQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // sleep 一段时间，模拟吐司制作的过程
                Thread.sleep(100 + random.nextInt(500));
                Toast toast = new Toast(counter++);
                System.out.println("new toasted : " + toast);
                toastQueue.put(toast);
            }
        }catch (InterruptedException e){
            System.out.println("Toaster Interrupted ... ");
        }
        System.out.println("Toaster off");
    }
}

/**
 * 线程2 这个线程的工作是给吐司涂上奶油
 * 放到队列2： butteredQueue
 */
class Butterer implements Runnable{

    // 队列1 (未经处理的)吐司队列
    private ToastQueue toastQueue;
    // 队列2 涂上了奶油的吐司队列
    private ToastQueue butteredQueue;

    /**
     * constructor of Butterer
     * @param toastQueue
     * @param butteredQueue
     */
    public Butterer(ToastQueue toastQueue, ToastQueue butteredQueue) {
        this.toastQueue = toastQueue;
        this.butteredQueue = butteredQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // 从(未经处理的)吐司队列取出吐司
                Toast toast = toastQueue.take();
                // 涂上奶油
                toast.butter();
                System.out.println("buttered: " + toast);
                // 然后放到涂上了奶油的吐司队列
                butteredQueue.put(toast);
            }
        }catch (InterruptedException e){
            System.out.println("Butterer Interrupted ... ");
        }
        System.out.println("Butterer off ...");
    }
}

/**
 * 线程3 这个线程的工作是给吐司涂上果酱
 */
class Jammer implements Runnable{

    // 队列1 涂上了奶油的吐司队列
    private ToastQueue butteredQueue;
    // 队列2 涂上了果酱的吐司队列(完成队列)
    private ToastQueue finishedQueue;

    /**
     * constructor of Jammer
     * @param butteredQueue
     * @param finishedQueue
     */
    public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
        this.butteredQueue = butteredQueue;
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // 从涂上奶油的吐司队列取出吐司
                Toast toast = butteredQueue.take();
                // 涂上果酱
                toast.jam();
                System.out.println("jamed: " + toast);
                // 然后放到涂上了果酱的吐司队列(完成队列)
                finishedQueue.put(toast);
            }
        }catch (InterruptedException e){
            System.out.println("Jammer Interrupted ... ");
        }
        System.out.println("Jammer off ...");
    }
}

/**
 * 线程4 这个线程是客户吃掉吐司
 */
class Eater implements Runnable{
    // 队列1 涂上了果酱的吐司队列
    private ToastQueue finishedQueue;

    /**
     * constructor of Eater
     * @param finishedQueue
     */
    public Eater(ToastQueue finishedQueue) {
        this.finishedQueue = finishedQueue;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                // 从涂上果酱的(已完成)吐司队列取出吐司
                Toast toast = finishedQueue.take();

                System.out.println("eating toast: " + toast);

            }
        }catch (InterruptedException e){
            System.out.println("Eater Interrupted ... ");
        }
        System.out.println("Eater off ...");
    }
}

/**
 * main class
 */
public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 队列1 保存(未经处理的)吐司
        ToastQueue dryQueue = new ToastQueue();
        // 队列2 保存(涂上了奶油的)吐司
        ToastQueue butteredQueue = new ToastQueue();
        // 队列3 保存(涂上了果酱的)吐司
        ToastQueue finishedQueue = new ToastQueue();

        executorService.execute(new Toaster(dryQueue));
        executorService.execute(new Butterer(dryQueue,butteredQueue));
        executorService.execute(new Jammer(butteredQueue,finishedQueue));
        executorService.execute(new Eater(finishedQueue));

        Thread.sleep(5 * 1000);
        executorService.shutdownNow();

    }
}
