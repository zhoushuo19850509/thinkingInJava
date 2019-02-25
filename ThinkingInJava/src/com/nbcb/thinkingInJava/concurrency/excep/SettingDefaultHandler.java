package com.nbcb.thinkingInJava.concurrency.excep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个文件实现的功能和CaptureUncaughtException.java类似
 * 就是通过一个ExceptionHandler来捕捉子线程抛出的异常
 * 我们看代码非常简洁，只是复用了之前的MyUncaughtExceptionHandler类，
 * 用于处理子线程抛出的异常
 * 但是Default这种形式的设置，使得灵活性大大降低
 */
public class SettingDefaultHandler {
    public static void main(String[] args){
        /**
         * 为主线程设置默认的ExceptionHandler
         */
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}
