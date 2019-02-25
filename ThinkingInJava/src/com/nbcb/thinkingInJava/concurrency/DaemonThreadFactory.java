package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * DaemonThreadFactory 和DaemonFromFactory搭配起来一起看：
 * DaemonThreadFactory.java实现了ThreadFactory接口，
 * 通过newThread()方法，生成一个Daemon Thread
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r){
        Thread t = new Thread(r);
        t.setDaemon(true);
        return  t;
    }
}
