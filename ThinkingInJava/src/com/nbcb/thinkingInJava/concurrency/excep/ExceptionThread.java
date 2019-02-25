package com.nbcb.thinkingInJava.concurrency.excep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ����ļ���Ҫ������ģ���߳��߳��쳣�ĳ�����
 * ������main thread��������һ���µ��߳�,
 * ���߳��׳����쳣main thread �ǲ�׽������
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
