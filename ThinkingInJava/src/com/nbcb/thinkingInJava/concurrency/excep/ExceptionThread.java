package com.nbcb.thinkingInJava.concurrency.excep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这个文件主要是用来模拟线程线程异常的场景：
 * 我们在main thread中启动了一个新的线程,
 * 新线程抛出的异常main thread 是捕捉不到的
 */
public class ExceptionThread implements Runnable {

    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args){

        try{
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch (RuntimeException e){
            System.out.println("Exception catched!");
        }
    }
}
