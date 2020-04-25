package com.nbcb.thinkingInJava.concurrency.teminatetask;


/**
 * 这个代码主要是为了说明，我们中断一个子线程的时候，需要特别注意关闭子线程中的资源
 * 我们尝试运行一下程序
 *
 * @场景1
 * 如果point1-point2之间中断子线程，能够捕捉到InterruptedException异常，
 * 同时NeedsCleanup1对象资源能够回收
 *
 * @场景2
 * 如果在point2之后中断子线程，并不会抛出InterruptedException异常
 * 子线程会等待Long operation(for循环)做完。
 * 然后NeedsCleanup1、NeedsCleanup2对象都能够回收
 */

/**
 * 这个类主要是模拟某个类清理资源的场景
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
 * 这个类主要是模拟一个单独的线程
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
 * 这里会启动子线程，然后过一段时间就中断这个子线程
 */
public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Block3());
        t.start();

        Thread.sleep(200);
        t.interrupt();
    }


}
