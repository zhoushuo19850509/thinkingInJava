package com.nbcb.thinkingInJava.concurrency.teminatetask;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 这个文件主要是说明NIO能够更加优雅地处理IO中断
 * 通过实际代码运行可以看到，无论是通过关闭Future，还是通过关闭线程池，还是关闭NIO SocketChannel
 * 都能够中断等待IO输入的子线程
 *
 * 这个也是对ClosingResouce.java的一种优化
 * 我们看到ClosingResouce.java中，即便关闭了线程池，子线程中等待socket inputstream的动作也不会中断
 * 但是NIO做了优化，只要线程池关闭，子线程也能捕捉到中断。
 *
 * @总结
 * 这个代码主要是在ClosingResouce.java的基础上，做了进一步的优化
 * 能够通过NIO实现只要子线程关闭，就能够中断子线程的NIO连接资源
 */
class NIOBlocked implements Runnable{
    private final SocketChannel sc;

    NIOBlocked(SocketChannel sc) {
        this.sc = sc;
    }


    @Override
    public void run() {
        try {
            System.out.println("waiting for read in:  " + this);
            sc.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e) {
            System.out.println("ClosedByInterruptException!");
        } catch (ClosedChannelException e){
            System.out.println("ClosedChannelException!!");
        }catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Existing NIOBlocked.run()" + this);

    }
}

public class NIOInterruption {

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        ServerSocket server =new ServerSocket(8080);
        InetSocketAddress isa = new InetSocketAddress("localhost",8080);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);

        /**
         * 通过Future的方式，运行线程1
         */
        Future<?> f = exec.submit(new NIOBlocked(sc1));

        /**
         * 通过exec.execute的方式，运行线程2
         */
        Thread.sleep(1000);
        exec.execute(new NIOBlocked(sc2));

        /**
         * 等待5秒钟之后，关闭Future
         * 可以看到，NIOBlocking是能够正常关闭的
         */
        Thread.sleep(5000);
        f.cancel(true);
        System.out.println("close the future");

        Thread.sleep(5000);
        sc2.close();
        System.out.println("close the socket channel");


        /**
         * 5秒钟之后，关闭线程池
         */
        Thread.sleep(5000);
        exec.shutdownNow();
        System.out.println("shutdown the thead pool! ");

    }
}
