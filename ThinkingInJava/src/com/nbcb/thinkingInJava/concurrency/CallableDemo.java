package com.nbcb.thinkingInJava.concurrency;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 这个类主要是为了说明如何获取线程的返回值
 * 实现的思路：线程类要实现Callable<T>接口
 * 在call()方法中，返回线程的返回值
 * 后续需要更加深入了解Future的用法
 */
class TaskWithResult implements Callable<String>{

    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

    public String call(){
        // 这里尝试对每个线程都sleep一段时间，用来探索Future的用法
//        try{
//            Thread.sleep(1000 * (5 - id));
//        }catch(Exception e){
//            e.printStackTrace();
//        }

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
        // 其中Future.get()方法是一个阻塞方法。只有当线程返回的时候，才会取消阻塞。
        // 那么这个for循环其实有一个问题，就是要依次等每个线程返回，才能打印

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
