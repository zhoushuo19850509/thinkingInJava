package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * DaemonThreadFactory ��DaemonFromFactory��������һ�𿴣�
 * DaemonThreadFactory.javaʵ����ThreadFactory�ӿڣ�
 * ͨ��newThread()����������һ��Daemon Thread
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r){
        Thread t = new Thread(r);
        t.setDaemon(true);
        return  t;
    }
}
