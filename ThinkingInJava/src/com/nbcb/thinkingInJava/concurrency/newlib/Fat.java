package com.nbcb.thinkingInJava.concurrency.newlib;

/**
 *
 * Fat���������Ҫ��ģ��object pool�е�һ��������Դ
 * �����Դ�ڴ�����ʱ�򣬱Ƚ���
 * ���ԣ�����һ����Դ�ؾͱȽ���������
 *
 */
public class Fat {
    private volatile double d;
    private static int counter = 0 ;
    private final int id = counter++;

    /**
     * constructor ��Ҫ������һ���ȽϺķ���Դ�Ĳ���
     * ģ��ö��󴴽���ʱ��Ƚ���
     */
    public Fat() {
        for (int i = 0; i < 10000; i++) {
            d += ( Math.PI + Math.E ) / (double)i;
        }
    }

    public void operate(){
        System.out.println(this);

    }

    @Override
    public String toString() {
        return "Fat{" +
                "id=" + id +
                '}';
    }
}