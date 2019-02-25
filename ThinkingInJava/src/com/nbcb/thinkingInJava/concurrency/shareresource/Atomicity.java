package com.nbcb.thinkingInJava.concurrency.shareresource;

public class Atomicity {
    int i;
    void f1(){
        i++;
    }

    void f2(){
        i += 3;
    }
}
