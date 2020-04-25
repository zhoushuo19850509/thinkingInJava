package com.nbcb.thinkingInJava.concurrency.coperatingtasks;


/**
 * ����ļ����룬��Ҫ��ͨ�������ߡ�������ģ�ͣ�˵�����̼������ͨ�ŵ�
 * �ؼ�����notifyAll()
 *
 * �����ߡ�������ģ�͵�����ԭ���������ģ�
 * ������(Chef)(��Ϊ�������߳�)��ͣ������(new Meal����)
 * ������(waitPerson)(��Ϊ�������߳�)��ͣ�ذѲ˶���(Meal������Ϊnull)
 *
 * ���������õ����ģ�ͣ������̼߳��̵�ʱ��Ҫ�ر�ע�⣬��Ҫ�õ�notifyAll()����֪ͨ�����߳�
 * ������������߳�ͨ��while()���ϵ�̽�⣬Ҳ̽�ⲻ��
 */

import com.sun.xml.internal.ws.util.ReadAllStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ��ǰ�ĵ����Ϣ
 */
class Meal{

    /**
     * orderNum
     * ��ǰ�ǵڼ�����
     */

    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "orderNum=" + orderNum +
                '}';
    }
}

/**
 * ����Ա
 */
class WaitPerson implements Runnable{
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){

                /**
                 * ����waitPerson��˵�����mealû��׼���ã���һֱ����
                 */
                synchronized (this){
                    while(restaurant.meal == null){
                        wait();
                    }
                }

                /**
                 * һ������whileѭ����˵��Meal׼�����ˣ�
                 */
                System.out.println("WaitPerson is Consuming the meal : " + restaurant.meal);

                /**
                 * ����waitPerson��ʼ����Meal
                 * ����Meal�ɹ��󣬽�restaurant.meal������Ϊ��
                 * ������ɺ�֪ͨChef���Կ�ʼ��һ������
                 */
                synchronized (restaurant.chef){
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }


            }
        }catch (InterruptedException e){
            System.out.println("wait person interrupted");
        }

    }
}

/**
 * ���
 */
class Chef implements Runnable{
    private Restaurant restaurant;

    /**
     * ���count����˼�ǣ�Chefһ���������10���ˡ�
     * һ��������10���ˣ�Chef�������������ˣ�����û�ж���Ĳ��Ͽ���������
     */
    private int count = 0;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()){

                /**
                 * �����ǰ����˻�û�б�WaitPersonȡ�ߣ�
                 * ��ôChef��һֱ���ţ�������һ����
                 */
                synchronized (this){
                    while(restaurant.meal != null){
                        wait();
                    }
                }

                /**
                 * һ������10���ˣ������͹ر���
                 */
                if(++count == Restaurant.MAX_COUNT){
                    System.out.println("out of the food , closing the restaurant");
                    restaurant.exec.shutdownNow();
                }

                /**
                 * Ȼ��ʼ���ˣ���֪ͨwaitPerson��ȡ
                 */
                System.out.println("Chef is producing the meal!");
                synchronized (restaurant.waitPerson){
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }

                /**
                 * ����һ���ˣ�Chef����Ϣһ���
                 */
                Thread.sleep(1000);

            }


        } catch (InterruptedException e) {
            System.out.println("order up ! chef finish today's task!");
        }


    }
}


/**
 * ����
 * �������ʵ�ֺܼ򵥣�����newһ��WaitPerson�����һ��Chef����Ȼ����̳߳��̳����õ��߳���Դ
 * �ֱ�����WaitPerson�̺߳�һ��Chef�߳�
 */
public class Restaurant {

    /**
     * �������һ���������������
     */
    public final static int MAX_COUNT = 10;

    /**
     * ��ǰ��������һ����
     */
    Meal meal;

    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(waitPerson);
        exec.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
