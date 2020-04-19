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
 * ����ļ���Ҫ��˵��NIO�ܹ��������ŵش���IO�ж�
 * ͨ��ʵ�ʴ������п��Կ�����������ͨ���ر�Future������ͨ���ر��̳߳أ����ǹر�NIO SocketChannel
 * ���ܹ��жϵȴ�IO��������߳�
 *
 * ���Ҳ�Ƕ�ClosingResouce.java��һ���Ż�
 * ���ǿ���ClosingResouce.java�У�����ر����̳߳أ����߳��еȴ�socket inputstream�Ķ���Ҳ�����ж�
 * ����NIO�����Ż���ֻҪ�̳߳عرգ����߳�Ҳ�ܲ�׽���жϡ�
 *
 * @�ܽ�
 * ���������Ҫ����ClosingResouce.java�Ļ����ϣ����˽�һ�����Ż�
 * �ܹ�ͨ��NIOʵ��ֻҪ���̹߳رգ����ܹ��ж����̵߳�NIO������Դ
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
         * ͨ��Future�ķ�ʽ�������߳�1
         */
        Future<?> f = exec.submit(new NIOBlocked(sc1));

        /**
         * ͨ��exec.execute�ķ�ʽ�������߳�2
         */
        Thread.sleep(1000);
        exec.execute(new NIOBlocked(sc2));

        /**
         * �ȴ�5����֮�󣬹ر�Future
         * ���Կ�����NIOBlocking���ܹ������رյ�
         */
        Thread.sleep(5000);
        f.cancel(true);
        System.out.println("close the future");

        Thread.sleep(5000);
        sc2.close();
        System.out.println("close the socket channel");


        /**
         * 5����֮�󣬹ر��̳߳�
         */
        Thread.sleep(5000);
        exec.shutdownNow();
        System.out.println("shutdown the thead pool! ");

    }
}
