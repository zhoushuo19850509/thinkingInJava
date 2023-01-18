package com.nbcb.thinkingInJava.concurrency.newlib;


/**
 * 本代码沿袭了上一个代码：DelayQueueDemo
 * 不能说一模一样，但是相似度那是99%
 * 在DelayQueueDemo中，决定一个任务的优先是delay，
 * 就是等待时间越久的元素，越后面才被取到
 *
 * PriorityBlockingQueue意思就是可以设置队列中各个元素的优先级
 * 哪个元素的优先级越高，就越先被取到
 * 当然PriorityBlockingQueue也是用于并发场景的：
 * Producer并发往PriorityBlockingQueue中添加一个个指定优先级的元素
 * Consumer从PriorityBlockingQueue中获取元素，
 * 这些元素是按照优先级顺序一个个被consumer取走的
 *
 * 这个模型非常棒
 */


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 这个class，是我们定义的准备放到PriorityQueue队列的元素
 * 这个元素最关键的就是需要实现Comparable接口，
 * 用于PriorityQueue队列比较各个元素的大小，从而制定优先级
 * 优先级越高的元素，最先被消费
 */
class PrioritizedTask implements Runnable, Comparable<PrioritizedTask>
{
    Random random = new Random(47);

    // 这两个变量，照例是用来标识线程号的
    private static int counter = 0;
    private final int id = counter++;

    /**
     * 这个变量制定了当前PrioritizedTask对象的优先级
     */
    private final int priority;

    /**
     * 这个list保留了所有放到PriorityQueue队列的PrioritizedTask对象
     * 作用：后续EndSentinel会遍历这个sequence
     * 打印整个PrioritizedTask List元素
     */
    protected static List<PrioritizedTask> sequence =
            new ArrayList<PrioritizedTask>();

    /**
     * constructor: 用来指定当前PrioritizedTask对象的优先级
     * @param priority
     */
    public PrioritizedTask(int priority) {
        this.priority = priority;
    }

    /**
     * 实现Comparable接口的方法：
     * 用于比较两个PrioritizedTask对象的优先级
     * @param o
     * @return
     */
    @Override
    public int compareTo(PrioritizedTask o) {
        if(this.priority < o.priority){
            return 1;
        }else if(this.priority > o.priority){
            return -1;
        }else{
            return 0;
        }
    }

    /**
     * 异步线程方法，实现的业务逻辑比较简单：
     * 1.等待一段随机的时间；
     * 2.打印当前PrioritizedTask对象
     */
    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "PrioritizedTask{" +
                "id=" + id +
                ", priority=" + String.format("[%1$-3d]",priority) +
                '}';
    }

    public String summary(){
        return "(" + id + "" + priority + ")";
    }

    /**
     * EndSentinel是PrioritizedTask的子类实现两个功能：
     * 1.遍历PrioritizedTask队列中的sequence对象，
     *   打印整个PrioritizedTask队列
     * 2.作为PrioritizedTask队列最后一个元素，结束主线程
     *  (主线程就是往PrioritizedTask队列中塞入元素的线程)
     *
     */
    public static class EndSentinel extends PrioritizedTask{

        // 往PriorityQueue队列中塞入元素的主线程
        private ExecutorService executorService;

        public EndSentinel(ExecutorService executorService) {
            // 备注：最后一个元素由于要最后处理，所以设置了最低的优先级
            super(-1);
            this.executorService = executorService;
        }

        /**
         * 重载父类的run()方法，
         * 功能就是打印PrioritizedTask队列中的各个PrioritizedTask对象信息
         */
        public void run(){
            for(PrioritizedTask prioritizedTask : sequence){
                System.out.println(prioritizedTask.summary() + " ");
            }
            System.out.println("EndSentinel node is calling shutdown ...");
            executorService.shutdownNow();
        }
    }
}


/**
 * Producer
 */
class PrioritizedProducer implements Runnable{

    private Random random = new Random(47);
    /**
     * producer要往这个queue中塞PrioritizedTask对象元素
     */
    private Queue<Runnable> queue;
    /**
     * producer往queue中塞最后一个PrioritizedTask对象元素的时候，
     * 会用到线程池对象，因为最后一个PrioritizedTask对象是EndSentinel
     * 需要调用线程池关闭主线程
     */
    private ExecutorService executorService;

    /**
     * constructor of Producer
     * @param queue
     * @param executorService
     */
    public PrioritizedProducer(Queue<Runnable> queue,
                               ExecutorService executorService) {
        this.queue = queue;
        this.executorService = executorService;
    }

    /**
     * Producer异步线程方法要做的事情，
     * 就是定期往PriorityQueue队列中添加PrioritizedTask对象元素
     */
    @Override
    public void run() {

        // 先添加20个随机优先级的PrioritizedTask对象元素
        for (int i = 0; i < 20; i++) {
             this.queue.add(
                     new PrioritizedTask(random.nextInt(10)));
        }

        // 再添加10个优先级最高的PrioritizedTask对象元素
        for (int i = 0; i < 10; i++) {
            this.queue.add(new PrioritizedTask(10));
        }

        // 最后再添加10个PrioritizedTask对象，优先级从1到10一次增加
        for (int i = 0; i < 10; i++) {
            this.queue.add(new PrioritizedTask(i));
        }


        // 最后一个元素必须是EndSentinel对象
        this.queue.add(new PrioritizedTask.EndSentinel(executorService));
    }
}


/**
 * Consumer
 */
class PrioritizedConsumer implements Runnable{

    /**
     * Consumer要从这个queue中获取PrioritizedTask对象元素并消费
     */
    private PriorityBlockingQueue<Runnable> queue;

    // constructor of consumer
    public PrioritizedConsumer(PriorityBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){
                queue.take().run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        System.out.println("start PriorityBlockingQueueDemo ...");
        /**
         * 先定义一个PriorityQueue
         */
        PriorityBlockingQueue<Runnable> priorityQueue =
                new PriorityBlockingQueue<>();

        /**
         * 然后定义一个线程池
         */
        ExecutorService executorService = Executors.newCachedThreadPool();

        /**
         * 启动Producer线程，往PriorityQueue队列塞PrioritizedTask对象元素
         */
        executorService.execute(
                new PrioritizedProducer(priorityQueue,executorService));


        /**
         * 启动Consumer线程，从PriorityQueue队列消费PrioritizedTask对象元素
         */
        executorService.execute(new PrioritizedConsumer(priorityQueue));

    }
}
