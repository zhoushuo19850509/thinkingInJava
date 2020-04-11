package com.nbcb.thinkingInJava.concurrency.shareresource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * ����˵��
 * �����ļ���Ҫ��˵��Critical section
 *
 * ���뱳����
 * Pair����x/y����������Ҫͬʱ��������������1
 * ����������̲߳���ȫ�ģ������Ҫsynchronized�ؼ�������֤�̰߳�ȫ��
 * ����ͨ��Critical section�����ԣ���С��ͬ������ķ��أ�������������С��ĳ����Ҫͬ���ķ�Χ
 *
 * Critical section����˵��
 * ���������ο�PairManager1/PairManager2������
 * ����PairManager1.increment()����������������synchronized�ؼ���
 * PairManager2.increment()���Ƕ��̲߳���ȫ�ĵط�������synchronized�ؼ���
 *
 * ��ôCritical section��ɶ�����أ�
 * �Ӳ��������������յ�ͬ��Ӱ��ķ�Χ��С�˺ܶࡣ
 */



/**
 * �ȶ���һ���ࣺ Pair
 * ����ౣ�������������� x/y
 * ����Ҫ��֤������������ֵ����һ��
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
 * �������
 * �����ṩ��getPair()/store()����ͨ�÷���
 * increment()������ʵ��
 */
abstract class PairManager{

    /**
     * �����ֶμ�¼���ǵ���PairChecker����˶��ٴ���
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
 * ����1
 * ����increment������������synchronized�ؼ���
 */
class PairManager1 extends PairManager{

    /**
     * ʵ��increment()����
     * ÿ��increment��������Ҫ��һ���µ�ʵ���ŵ�List��ȥ
     */
    @Override
    public synchronized void increment() {
        pair.incrementX();
        pair.incrementY();
        storage.add(getPair());
    }
}

/**
 * ����2
 * ֻ���ڹؼ����̲߳���ȫ�Ĳ���(incrementX()/incrementY())��Χ������synchronized�ؼ���
 */
class PairManager2 extends PairManager{

    /**
     * ʵ��increment()����
     * ÿ��increment��������Ҫ��һ���µ�ʵ���ŵ�List��ȥ
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
 * �����ฺ�������̣߳����ϵ���Pair.increment()
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
     * toString()������Ҫ�������ǰPair��x/yֵ���Լ��������
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
 * ��������Ҫ������һ���̣߳���鵱ǰPair��x/yֵ�Ƿ�һ��
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
         * 5�����Ժ󿴿����
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
