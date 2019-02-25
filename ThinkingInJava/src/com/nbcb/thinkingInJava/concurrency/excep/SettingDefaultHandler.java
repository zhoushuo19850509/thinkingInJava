package com.nbcb.thinkingInJava.concurrency.excep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����ļ�ʵ�ֵĹ��ܺ�CaptureUncaughtException.java����
 * ����ͨ��һ��ExceptionHandler����׽���߳��׳����쳣
 * ���ǿ�����ǳ���ֻ࣬�Ǹ�����֮ǰ��MyUncaughtExceptionHandler�࣬
 * ���ڴ������߳��׳����쳣
 * ����Default������ʽ�����ã�ʹ������Դ�󽵵�
 */
public class SettingDefaultHandler {
    public static void main(String[] args){
        /**
         * Ϊ���߳�����Ĭ�ϵ�ExceptionHandler
         */
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}
