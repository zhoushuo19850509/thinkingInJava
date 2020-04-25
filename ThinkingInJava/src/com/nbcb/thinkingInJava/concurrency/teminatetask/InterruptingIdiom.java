package com.nbcb.thinkingInJava.concurrency.teminatetask;


/**
 * ���������Ҫ��Ϊ��˵���������ж�һ�����̵߳�ʱ����Ҫ�ر�ע��ر����߳��е���Դ
 * ���ǳ�������һ�³���
 *
 * @����1
 * ���point1-point2֮���ж����̣߳��ܹ���׽��InterruptedException�쳣��
 * ͬʱNeedsCleanup1������Դ�ܹ�����
 *
 * @����2
 * �����point2֮���ж����̣߳��������׳�InterruptedException�쳣
 * ���̻߳�ȴ�Long operation(forѭ��)���ꡣ
 * Ȼ��NeedsCleanup1��NeedsCleanup2�����ܹ�����
 */

/**
 * �������Ҫ��ģ��ĳ����������Դ�ĳ���
 */
class NeedsCleanup{
    private final int id;

    NeedsCleanup(int id) {
        this.id = id;
    }

    public void cleanup(){
        System.out.println("cleaning up : " + this.id);
    }
}

/**
 * �������Ҫ��ģ��һ���������߳�
 */
class Block3 implements Runnable{

    private volatile double d = 0.0;
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){

                // point1
                NeedsCleanup n1 = new NeedsCleanup(1);
                try{
                    System.out.println("sleeping");
                    Thread.sleep(1000);
                    // point2
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try{
                        long start = System.currentTimeMillis();
                        System.out.println("calculating!");
                        for(int i = 0 ; i < 250000000; i++){
                            d = d + (Math.PI +Math.E ) /d;
                        }
                        System.out.println("finished long operation, time cost: "
                                + (System.currentTimeMillis() - start) + " ms");
                    }finally {
                        n2.cleanup();
                    }

                }finally {
                    n1.cleanup();
                }
            }
            System.out.println("exiting while loop");
        }catch (InterruptedException e){
            System.out.println("exiting for InterruptedException");
        }

    }
}

/**
 * ������������̣߳�Ȼ���һ��ʱ����ж�������߳�
 */
public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Block3());
        t.start();

        Thread.sleep(200);
        t.interrupt();
    }


}
