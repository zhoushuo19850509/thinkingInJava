package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 代码说明
 * 整个文件主要是说明Critical section
 *
 * 代码背景：
 * Pair类有x/y两个变量，要同时给这两个变量加1
 * 这个操作是线程不安全的，因此需要synchronized关键字来保证线程安全。
 * 我们通过Critical section的特性，缩小了同步代码的返回，从整个方法缩小到某个需要同步的范围
 *
 * Critical section代码说明
 * 具体代码请参考PairManager1/PairManager2的区别
 * 其中PairManager1.increment()是整个方法加上了synchronized关键字
 * PairManager2.increment()中是对线程不安全的地方加上了synchronized关键字
 *
 * 那么Critical section有啥优势呢？
 * 从测试数据来看，收到同步影响的范围缩小了很多。
 */



/**
 * 先定义一个类： Pair
 * 这个类保留了两个变量： x/y
 * 我们要保证这两个变量的值保持一致
 */
class Pair{
    private int x;
    private int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Pair(){
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementX(){
        x++;
    }
    public void incrementY(){
        y++;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public class PairValueNotEqualException extends RuntimeException{
        public PairValueNotEqualException() {
            super("pair not equal! " + Pair.this);
        }
    }

    public void checkState(){
        if( x != y){
            throw new PairValueNotEqualException();
        }
    }
}

/**
 * 抽象基类
 * 基类提供了getPair()/store()两个通用方法
 * increment()由子类实现
 */
abstract class PairManager{

    /**
     * 整个字段记录的是调用PairChecker检查了多少次了
     */
    AtomicInteger checkCounter = new AtomicInteger(0);

    protected Pair pair = new Pair();
    List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());


    /**
     * make a copy of current Pair instance
     * @return
     */
    public synchronized Pair getPair(){
        return new Pair(pair.getX(),pair.getY());
    }

    public synchronized void store(Pair p){
        storage.add(p);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    abstract void increment();
}

/**
 * 子类1
 * 整个increment方法都加上了synchronized关键字
 */
class PairManager1 extends PairManager{

    /**
     * 实现increment()方法
     * 每次increment结束，都要把一个新的实例放到List中去
     */
    @Override
    public synchronized void increment() {
        pair.incrementX();
        pair.incrementY();
        storage.add(getPair());
    }
}

/**
 * 子类2
 * 只有在关键的线程不安全的操作(incrementX()/incrementY())周围加上了synchronized关键字
 */
class PairManager2 extends PairManager{

    /**
     * 实现increment()方法
     * 每次increment结束，都要把一个新的实例放到List中去
     */
    @Override
    public void increment() {
        Pair temp;
        synchronized (this){
            pair.incrementX();
            pair.incrementY();
            temp = getPair();
        }
        storage.add(temp);
    }
}

/**
 * 整个类负责启动线程，不断调用Pair.increment()
 */
class PairManipulator implements Runnable{

    private PairManager pm ;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while(true){
            pm.increment();
        }
    }

    /**
     * toString()方法主要是输出当前Pair的x/y值，以及检查的情况
     * @return
     */
    @Override
    public String toString() {
        return "PairManipulator{" +
                "Pair=" + pm.getPair() +
                '}' + " check count: " + pm.checkCounter.get();
    }
}

/**
 * 整个类主要是启动一个线程，检查当前Pair的x/y值是否一致
 */
class PairChecker implements Runnable{
    private PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }


    @Override
    public void run() {
        while(true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }

    }
}




public class CriticalSection {




    static void testApproaches(PairManager pmanamger1 , PairManager pmanamger2)  {
        ExecutorService exec = Executors.newCachedThreadPool();

        PairManipulator pm1 = new PairManipulator(pmanamger1);
        PairManipulator pm2 = new PairManipulator(pmanamger2);

        PairChecker pc1 = new PairChecker(pmanamger1);
        PairChecker pc2 = new PairChecker(pmanamger2);

        exec.execute(pm1);
        exec.execute(pm2);

        exec.execute(pc1);
        exec.execute(pc2);

        /**
         * 5秒钟以后看看结果
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("pm1 : " + pm1 );
        System.out.println("pm2 : " + pm2 );
        System.exit(0);

    }


    public static void main(String[] args) {
        PairManager1 pmanager1 = new PairManager1();
        PairManager2 pmanager2 = new PairManager2();
        CriticalSection.testApproaches(pmanager1, pmanager2);
    }
}
