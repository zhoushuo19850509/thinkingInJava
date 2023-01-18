package com.nbcb.thinkingInJava.concurrency.coperatingtasks;


import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这里例子引入了一个新的类： PipedWriter
 * 光从名字就能看出是一个管道，用于并发线程之间传输内容
 * 功能和之前的BlockingQueue差不多，就是安排了一个队列，
 * 一部分线程往队列中添加元素，一部分线程从队列中拿元素
 *
 * 本代码的整体架构比较清晰，就是两个独立的线程：
 * Sender: 往管道塞数据
 * Receiver: 从管道读取数据
 *
 * 需要注意的是PipedWriter的传输对象是char/int/string，
 * 适用性没有BlockingQueue好，因为BlockingQueue支持传输对象
 */


class Sender implements Runnable{

    private PipedWriter pipedWriter = new PipedWriter();
    private Random random = new Random(47);

    public PipedWriter getPipedWriter() {
        return pipedWriter;
    }

    @Override
    public void run() {
        try {
            while(true){
                for (char c = 'A'; c <= 'Z'; c++) {
                    pipedWriter.write(c);
                    Thread.sleep(random.nextInt(1000));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e + " sender interrupted");
        }

    }
}

class Receiver implements Runnable{

    private PipedReader pipedReader;

    /**
     * constructor of Receiver
     * 主要是用来初始化PipedReader对象
     * PipedReader对象来自Sender，
     * 根据PipedWriter对象初始化PipedReader对象
     * @param sender
     */
    public Receiver(Sender sender) {
        try {
            this.pipedReader = new PipedReader(sender.getPipedWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            while(true){
                char c = (char)this.pipedReader.read();
                System.out.println("read from pipe ...  " + c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


public class PipedIO {
    public static void main(String[] args) throws InterruptedException {

        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        Executor executor = Executors.newCachedThreadPool();

        executor.execute(sender);
        executor.execute(receiver);

        /**
         * 等待一段时间之后，主线程再关闭
         */
        Thread.sleep(10 * 1000);
        ((ExecutorService) executor).shutdownNow();
    }
}
