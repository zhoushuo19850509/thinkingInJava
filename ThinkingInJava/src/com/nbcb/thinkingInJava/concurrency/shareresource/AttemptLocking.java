package com.nbcb.thinkingInJava.concurrency.shareresource;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ����ļ�Ϊ��˵��lock.tryLock()���÷�
 * һ���Ǽ�ʱ��ȡ����lock.tryLock()
 * һ��������һ���ĳ�ʱʱ�䣺lock.tryLock(2, TimeUnit.SECONDS)
 *
 * �����ڴ����У���ǰ�߳�Ҫ����lock.lock()����ǰ��
 * ����ȵ���tryLock()��������
 * ���ܲ���ȡ����
 */
public class AttemptLocking {

    /**
     * private lock of the class
     */
    private ReentrantLock lock = new ReentrantLock();


    /**
     * ��ʱ��ȡ��
     */
    public void untimed(){
        boolean captured = lock.tryLock();
        try{
            System.out.println("trylock() : " + captured);
        }finally{
            if(captured){
                lock.unlock();
            }

        }

    }

    /**
     * ��һ����ʱʱ���ڣ������δ��ȡ���������׳��쳣
     */
    public void timed(){
        boolean captured = false;
        try{
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException();
        }

        try{
            System.out.println(
                    "lock.tryLock(2, TimeUnit.SECONDS) :" + captured);
        }finally{
            if(captured){
                lock.unlock();
            }
        }

    }

    public static void main(String[] args){
        final AttemptLocking al = new AttemptLocking();

        /**
         * ��û�������̸߳��ŵ������
         */
        al.untimed();  // ��ʱ��ȡ���ǳɹ���
        al.timed();    // �ڳ�ʱʱ���ڻ�ȡ����Ҳ�ǳɹ���

        /**
         * ����һ��Daemon Thread����ȡ��
         * ��ʱ���ٵ���untimed()/time()������ȡ������ʧ����
         */
        new Thread(){
            {
                setDaemon(true);
            }
            public void run(){
                System.out.println("daemon thread start to run");
                al.lock.lock();
                System.out.println("daemon thread finish get lock!");
                System.out.println("get lock by daemon thread!");
                boolean captured = al.lock.tryLock();
                System.out.println("lock.tryLock() in daemon thread:" + captured);

            }
        }.start();
        Thread.yield();

        /**
         * ���Thread.sleep()���Ҽӵ�
         * ������ӵĻ����ﲻ��ʵ���Ч����
         * ��Ϊ������û�ȵ�Daemon Thread��ȡ������untimed()/timed()������������ִ����
         * ������������Daemon Thread֮��Ҫ��΢��һ���������ntimed()/timed()
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * untimed()/timed()������������ȡ������
         * ��Ϊ���ǵ�Daemon Thread�Ѿ�ȡ�����ˣ�
         */
        al.untimed();
        al.timed();

    }


}
