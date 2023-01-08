package com.nbcb.thinkingInJava.concurrency.newlib;


import com.nbcb.thinkingInJava.generics.inferfaces.BasicGenerator;
import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 这个例子为了说明Exchanger在并发编程中的用法
 * 并发编程中有一个场景，就是线程1中会创建若干对象，
 * 然后希望把这些对象共享给其他线程。
 * Exchanger可以实现这个功能，不仅能够实现对象在线程间的共享
 * 还能够避免各种并发问题。
 *
 * 使用方式是这样的：
 * 1.线程1把所有要共享的对象元素准备好，放到一个容器中
 * 2.然后线程1通过Exchanger.exchange()方法，把这些共享出去，然后线程1暂时阻塞
 * 3.线程2通过调用Exchanger.exchange()方法，就能共享到这些元素
 * 4.这时，线程1就能够继续准备新的元素，然后再通过Exchanger把新的元素共享出去
 *   不断这样循环。
 *
 * 需要注意的是：
 * 1.producer线程调用Exchanger.exchange()方法之后，produer线程会先阻塞
 *   然后等待consumer调用Exchanger.exchange()方法，就能共享到这些元素
 *   需要注意的是，producer此时无需等待consumer把所有元素消费完。
 *   因此，Exchanger比较适合那种producer创建元素比较费时的场景。
 *
 * @param <T>
 */

class ExchangerProducer<T> implements Runnable{

    /**
     * generator用来生成各个元素
     */
    private Generator<T> generator;
    /**
     * exchanger用来在两个线程之间(producer和consumer之间)
     * 交换对象(这里是交换一个list)
     */
    private Exchanger<List<T>> exchanger;
    /**
     * 当前线程用来保存各个element的容器
     */
    private List<T> holder;

    /**
     * constructor of producer
     * @param generator
     * @param exchanger
     * @param holder
     */
    public ExchangerProducer(Generator<T> generator,
                             Exchanger<List<T>> exchanger,
                             List<T> holder) {
        this.generator = generator;
        this.exchanger = exchanger;
        this.holder = holder;
    }

    /**
     * producer异步线程要做的事情是这样的：
     * 1.通过generator不断往容器中添加元素
     * 2.等所有元素全部添加完毕之后，
     * 通过Exchanger把容器置换给其他线程
     */
    @Override
    public void run() {
        System.out.println("ExchangerProducer start run ...");
        System.out.println("producer interrupted() : " + Thread.interrupted());

        try{
            while(!Thread.interrupted()){
                for (int i = 0; i < ExchangerDemo.size; i++) {
                    Thread.sleep(1000);
                    T element = generator.next();
                    System.out.println(Thread.currentThread().getId() +
                            " ExchangerProducer add element ..." + element);
                    holder.add(element);
                }
                holder = exchanger.exchange(holder);
            }


        }catch (InterruptedException e) {
            System.out.println("producer catch InterruptedException ...");
            e.printStackTrace();
        }
    }
}

class ExchangerConsumer<T> implements Runnable{

    /**
     * exchanger用来在两个线程之间(producer和consumer之间)
     * 交换对象(这里是交换一个list)
     */
    private Exchanger<List<T>> exchanger;
    /**
     * 当前线程用来保存各个element的容器
     */
    private List<T> holder;
    private volatile T value;

    /**
     * consturctor of consumer
     * @param exchanger
     * @param holder
     */
    public ExchangerConsumer(Exchanger<List<T>> exchanger,
                             List<T> holder) {
        this.exchanger = exchanger;
        this.holder = holder;
    }

    /**
     * consumer的异步线程做的事情是这样的：
     * 1.通过Exchanger，从其他线程获取容器
     * 2.然后从容器中读取各个元素
     */
    @Override
    public void run() {
        System.out.println("ExchangerConsumer start run ...");
        System.out.println("consumer interrupted() : " + Thread.interrupted());
        try{
            while(!Thread.interrupted()){
                holder = exchanger.exchange(holder);
                for(T t : holder){
                    value = t;
                    holder.remove(t);
                    System.out.println(Thread.currentThread().getId() +
                            " consumer get element from produer ..."
                            + value);
                    Thread.sleep(200);
                }
            }
        }catch (InterruptedException e){
            System.out.println("consumer catch InterruptedException ...");
            e.printStackTrace();
        }
    }
}

/**
 * main class
 */
public class ExchangerDemo {

    static int size = 10;

    public static void main(String[] args) throws InterruptedException {

        // 创建一个exchanger对象，用于consumer/producer之间交换内容
        Exchanger<List<Fat>> fatExchanger = new Exchanger<List<Fat>>();

        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一个generator对象，用于producer创建元素
        Generator<Fat> fatGenerator = BasicGenerator.create(Fat.class);

        // 创建一个容器，用于保存producer创建的元素
        List<Fat> producerList = new CopyOnWriteArrayList<Fat>();

        // 创建一个容器，供consumer使用
        List<Fat> consumerList = new CopyOnWriteArrayList<Fat>();

        // 通过线程池，启动producer线程
        executorService.execute(new ExchangerProducer<Fat>(
                fatGenerator,fatExchanger,producerList));

        // 通过线程池，启动consumer线程
        executorService.execute(new ExchangerConsumer<Fat>(
                fatExchanger,consumerList));

        Thread.sleep(30 * 1000);
        executorService.shutdownNow();
    }
}
