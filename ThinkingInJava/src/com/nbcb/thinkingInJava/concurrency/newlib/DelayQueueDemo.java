package com.nbcb.thinkingInJava.concurrency.newlib;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 这又是一个非常有意思的例子，引入了一个新的类：DelayQueue
 * DelayQueue特点是这样的：
 * 1.队列中的对象必须要实现Delay接口
 *   这个Delay接口用来设置delay参数，某个对象delay设置得越大，
 *   说明这个对象越晚从队列中被获取
 * 2.producer队列可以往DelayQueue添加各个元素，
 *   通过Delay接口指定各个元素多久时间以后能够被获取
 * 3.consumer接口可以从DelayQueue对了获取各个元素
 *
 * 总结一下：
 * 1.DelayQueue整体来说也是用于并发场景：producer-consumer
 * 2.可以指定队列中的各个元素的优先级
 */


/**
 * class1 我们定义的这个DelayedTask类
 * 用于我们后续要引入的DelayQueue类
 * 因为DelayQueue队列中的节点，比如实现Delayed接口
 * 不是随便什么对象都能够塞入DelayQueue队列的
 *
 */
class DelayedTask implements Runnable, Delayed {

    // 这两个变量，照例是用来标识线程号的
    private static int counter = 0;
    private final int id = counter++;

    // 这两个变量，是用来指定delay参数的
    private final int delta;
    private final long trigger;

    /**
     * 这个list保留了所有初始化的DelayedTask对象
     * 后续EndSentinel会用这个sequence打印整个DelayedTask List元素
     */
    protected static List<DelayedTask> sequence =
    new ArrayList<DelayedTask>();

    /**
     * constructor of DelayedTask
     * 主要是用来设置delay相关的参数
     * @param delayMilliSeconds
     */
    public DelayedTask(int delayMilliSeconds) {
        /**
         * 这个字段意思是delay的时间(ms)
         */
        this.delta = delayMilliSeconds;
        /**
         * 这个字段意思就是当前时间+ delay的时间(ns)
         * 其实就是一旦当前DelayTask对象到了这个时间点，
         * 就能够触发consumer从这个DelayQueue队列中获取这个DelayTask对象
         * 后续DeleyedQueue也是根据这个trigger字段，
         * 判断从队列中优先取哪个DelayTask对象
         * 备注：单位是nano seconds(微秒)
         */
        this.trigger = System.nanoTime() +
                TimeUnit.NANOSECONDS.convert(delta,TimeUnit.MILLISECONDS);
    }

    /**
     * 实现Delayed 重载方法1
     * 意思就是两个DelayedTask对象，根据这个compareTo()方法进行比较
     * 看谁的triger时间比较近，triger时间越近，
     * 就意味着这个DelayedTask对象优先被触发
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        DelayedTask thatTask = (DelayedTask)o;

        if(this.trigger < thatTask.trigger){
            return -1;
        }else if(this.trigger > thatTask.trigger){
            return 1;
        }else{
            return 0;
        }
    }


    /**
     * 实现Delayed 重载方法2
     * 这个方法意思就是
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(),
                TimeUnit.NANOSECONDS);
    }

    // 异步线程方法执行的逻辑很简单，就是打印当前线程的id
    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return "DelayedTask{" +
                "id=" + id +
                ", delta=" + String.format("[%1$-4d]",delta) +
                '}';
    }

    public String summary(){
        return "(" + id + "" + delta + ")";
    }

    /**
     * EndSentinel是DelayedTask的子类实现两个功能：
     * 1.遍历DelayedTask中的sequence对象，
     *   打印整个DelayedTask list
     * 2.作为DelayedTask list最后一个元素，结束主线程
     *  (主线程就是往delay list中塞入元素的线程)
     *
     */
    public static class EndSentinel extends DelayedTask{

        // 往delay list中塞入元素的主线程
        private ExecutorService executorService;

        public EndSentinel(int delayMilliSeconds, ExecutorService executorService) {
            super(delayMilliSeconds);
            this.executorService = executorService;
        }

        /**
         * 重载父类的run()方法，
         * 功能就是打印DelayedTask list中的各个DelayedTask对象信息
         */
        public void run(){
            for(DelayedTask delayedTask : sequence){
                System.out.println(delayedTask.summary() + " ");
            }
            System.out.println("EndSentinel node is calling shutdown ...");
            executorService.shutdownNow();
        }
    }
}

/**
 * 这是一个消费者线程
 * 这个线程做的事情，就是定期从DelayQueue队列中抓取DelayedTask对象，
 * 并且执行这个DelayedTask对象
 */
class DelayedConsumer implements Runnable{

    private DelayQueue<DelayedTask> delayQueue;

    public DelayedConsumer(DelayQueue<DelayedTask> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                delayQueue.take().run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


/**
 * main class
 *
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        // 用来创建随机的delta
        Random random = new Random(47);

        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 初始化一个DelayQueue队列对象
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();

        // 往DelayQueue队列中塞入若干DelayedTask对象
        for (int i = 0; i < 10; i++) {
            int delta = random.nextInt(5000);
            DelayedTask delayedTask = new DelayedTask(delta);
            delayQueue.add(delayedTask);
        }

        /**
         * 往DelayQueue队列中塞入1个EndSentinel对象
         * 说明这个EndSentinel对象是DelayQueue队列中最后一个元素
         */
        delayQueue.add(new DelayedTask.
                EndSentinel(5000,executorService));


        // 启动一个消费线程，消费DelayQueue队列中的DelayedTask对象
        executorService.execute(new DelayedConsumer(delayQueue));
    }
}
