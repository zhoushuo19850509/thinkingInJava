package com.nbcb.thinkingInJava.concurrency.shareresource;

/**
 * 这个类主要用于生成序列号
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public synchronized static int nextSerialNumber(){
        return serialNumber++;
    }
}
