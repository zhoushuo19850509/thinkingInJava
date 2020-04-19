package com.nbcb.thinkingInJava.concurrency.teminatetask;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个代码主要是为了说明如何关闭子线程中的资源
 *
 * 这个要结合上一个代码：Interrupting.java
 * 上一个代码已经说明，如果一个子线程在等待IO输入，如果你关闭这个子线程，等待IO输入的动作是不会自动关闭的
 * 必须等到主线程退出，这个等待IO输入的动作才会关闭
 *
 * 这个代码说明了如何关闭这个子线程中的IO输入
 *
 * 当然IO输入好好几种方式
 * 一种是socket inputstream(来自的网络的输入流)，这种流关闭之后，子线程能够捕捉到中断。
 * 一种是System.in 这种流关闭后，子线程也没法捕捉到中断。
 *
 * @总结
 * 这个代码说明什么呢？就是我们就算关闭了子线程，子线程中的socket inputstream也是不会关闭的
 * 需要我们显式进行关闭。以后我们socket编程的时候需要特别注意.
 */
public class ClosingResouce {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * 先创建一个socket inputstream
         */
        ServerSocket server =new ServerSocket(8080);
        InputStream in = new Socket("localhost",8080).getInputStream();

        /**
         * 然后启动两个IOBlock进程
         */
        exec.execute(new IOBlocked(in));
        exec.execute(new IOBlocked(System.in));

        /**
         * 一段时间之后，(通过关闭线程池)关闭子线程
         */
        Thread.sleep(100);
        System.out.println("shuting down the all the threads ( from thread pool)");
        exec.shutdownNow();

        /**
         * 关闭socket inputstream
         */
        Thread.sleep(2000);
        System.out.println("closing: " + in.getClass().getName());
        in.close();

        Thread.sleep(2000);
        System.out.println("closing: " + System.in.getClass().getName());
        System.in.close();

        Thread.sleep(2000);
        System.out.println("shuting down the main thread");
    }
}
