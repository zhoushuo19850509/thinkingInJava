package com.nbcb.thinkingInJava.concurrency.shareresource;

/**
 * �������Ҫ�����������к�
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;
    public synchronized static int nextSerialNumber(){
        return serialNumber++;
    }
}
