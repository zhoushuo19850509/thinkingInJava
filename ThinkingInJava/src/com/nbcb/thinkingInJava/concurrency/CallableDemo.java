package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

/**
 * �������Ҫ��Ϊ��˵����λ�ȡ�̵߳ķ���ֵ
 * ʵ�ֵ�˼·���߳���Ҫʵ��Callable<T>�ӿ�
 * ��call()�����У������̵߳ķ���ֵ
 * ������Ҫ���������˽�Future���÷�
 */
class TaskWithResult implements Callable<String>{

    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

    public String call(){
        // ���ﳢ�Զ�ÿ���̶߳�sleepһ��ʱ�䣬����̽��Future���÷�
        try{
            Thread.sleep(1000 * (5 - id));
        }catch(Exception e){
            e.printStackTrace();
        }

        return "the result is : " + id;
    }

}


public class CallableDemo {
    public static void main(String[] args){
        ExecutorService exec = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<Future<String>>();

        for( int i = 0 ; i < 5 ; i++){
//            results.add(exec.submit(new TaskWithResult(i)));
            results.add(exec.submit(new TaskWithResult(i)));
        }


        // print the result
        // ����Future.get()������һ������������ֻ�е��̷߳��ص�ʱ�򣬲Ż�ȡ��������
        // ��ô���forѭ����ʵ��һ�����⣬����Ҫ���ε�ÿ���̷߳��أ����ܴ�ӡ

        try {
            for(Future<String> fs : results){
                System.out.println(fs.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}
