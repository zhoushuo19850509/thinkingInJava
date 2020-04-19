package com.nbcb.thinkingInJava.concurrency.teminatetask;

import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���������Ҫ��Ϊ��˵����ιر����߳��е���Դ
 *
 * ���Ҫ�����һ�����룺Interrupting.java
 * ��һ�������Ѿ�˵�������һ�����߳��ڵȴ�IO���룬�����ر�������̣߳��ȴ�IO����Ķ����ǲ����Զ��رյ�
 * ����ȵ����߳��˳�������ȴ�IO����Ķ����Ż�ر�
 *
 * �������˵������ιر�������߳��е�IO����
 *
 * ��ȻIO����úü��ַ�ʽ
 * һ����socket inputstream(���Ե������������)���������ر�֮�����߳��ܹ���׽���жϡ�
 * һ����System.in �������رպ����߳�Ҳû����׽���жϡ�
 *
 * @�ܽ�
 * �������˵��ʲô�أ��������Ǿ���ر������̣߳����߳��е�socket inputstreamҲ�ǲ���رյ�
 * ��Ҫ������ʽ���йرա��Ժ�����socket��̵�ʱ����Ҫ�ر�ע��.
 */
public class ClosingResouce {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        /**
         * �ȴ���һ��socket inputstream
         */
        ServerSocket server =new ServerSocket(8080);
        InputStream in = new Socket("localhost",8080).getInputStream();

        /**
         * Ȼ����������IOBlock����
         */
        exec.execute(new IOBlocked(in));
        exec.execute(new IOBlocked(System.in));

        /**
         * һ��ʱ��֮��(ͨ���ر��̳߳�)�ر����߳�
         */
        Thread.sleep(100);
        System.out.println("shuting down the all the threads ( from thread pool)");
        exec.shutdownNow();

        /**
         * �ر�socket inputstream
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
