package com.nbcb.thinkingInJava.concurrency.newlib;


import com.nbcb.thinkingInJava.generics.inferfaces.BasicGenerator;
import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




/**
 * 这里例子在ExchangerDemo基础上，加入了多个producer/consumer
 * 这意思就是Exchanger也是可以并发访问的，并不仅限于一对producer/consumer
 */
public class ExchangerDemoMultiThread {

    static int size = 10;

    public static void main(String[] args) throws InterruptedException {

        // 创建一个exchanger对象，用于consumer/producer之间交换内容
        Exchanger<List<Fat>> fatExchanger = new Exchanger<List<Fat>>();

        // 创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 创建一个generator对象，用于producer创建元素
        Generator<Fat> fatGenerator = BasicGenerator.create(Fat.class);

        // 创建2个容器，分别用于保存两个producer创建的元素
        List<Fat> producerList1 = new CopyOnWriteArrayList<Fat>();
        List<Fat> producerList2 = new CopyOnWriteArrayList<Fat>();

        // 创建2个容器，分别供2个consumer使用
        List<Fat> consumerList1 = new CopyOnWriteArrayList<Fat>();
        List<Fat> consumerList2 = new CopyOnWriteArrayList<Fat>();

        // 通过线程池，启动producer线程1
        executorService.execute(new ExchangerProducer<Fat>(
                fatGenerator,fatExchanger,producerList1));

        // 通过线程池，启动producer线程2
        executorService.execute(new ExchangerProducer<Fat>(
                fatGenerator,fatExchanger,producerList2));

        // 通过线程池，启动consumer线程1
        executorService.execute(new ExchangerConsumer<Fat>(
                fatExchanger,consumerList1));
        // 通过线程池，启动consumer线程2
        executorService.execute(new ExchangerConsumer<Fat>(
                fatExchanger,consumerList2));

        Thread.sleep(5 * 1000);
        executorService.shutdown();
    }
}
