package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �������Ҫ�������IntGenerator���ɵ������Ƿ�Ϊż��
 * ���ķ�ʽ��Ҫ�ǣ����̲߳������
 * ���IntGenerator���̰߳�ȫ����һ����Ҫ��
 * ������˵��IntGenerator.netx()Ӧ�����ɵĶ���ż��
 * �������IntGenerator�̲߳���ȫ�����п��ܼ�⵽���������
 */
public class EvenChecker implements Runnable{

    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator g ,int ident){
        generator = g;
        id = ident;
    }

    @Override
    public void run() {
        /**
         * whileѭ����ͣ���IntGenerator���ɵ������Ƿ�Ϊż��
         * ֱ��IntGenerator�˳�Ϊֹ
         */
        while(!generator.isCancled()){
            int val = generator.next();
            if( ( val % 2 ) != 0){
                System.out.println(val + " not event");
                generator.cancle();
            }
        }
    }

    /**
     * ��̬���Է���
     * ������߳�
     * @param g
     * @param count  �������߳���
     */
    public static void test(IntGenerator g,int count){
        System.out.println("Press ctl-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for( int i = 0 ; i < count; i++){
            exec.execute(new EvenChecker(g,count));
        }
        exec.shutdown();
    }

    /**
     * default test function
     * @param g
     */
    public static void test(IntGenerator g){
        test(g,10);
    }

}
