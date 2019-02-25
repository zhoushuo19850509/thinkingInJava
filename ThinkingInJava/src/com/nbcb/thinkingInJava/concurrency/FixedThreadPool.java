package com.nbcb.thinkingInJava.concurrency;


import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class FixedThreadPool {
    public static void main(String[] args){
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for( int i = 0 ; i < 5 ; i++){
            exec.execute(new LiftOff());
        }
        exec.shutdown();

    }
}
