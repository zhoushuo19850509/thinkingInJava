package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.nio.channels.Pipe;

/**
 * 这个类代表筷子
 * 筷子有两个动作：
 * 1.筷子被拿起；
 * 2.筷子被放下
 */
public class Chopstick {

    /**
     * 当前正在使用筷子的哲学家
     */
    private Philosopher philosopher;

    private boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while (taken){
            wait();
        }
        taken = true;
    }

    public synchronized void drop(){
        taken = false;
        notifyAll();
    }


    /**
     * 下面这两个take()/drop()方法和上面不同之处在于
     * 记录了当前使用筷子的哲学家
     * @param philosopher
     * @throws InterruptedException
     */
    public synchronized void take(Philosopher philosopher) throws InterruptedException {
        while (taken){
            wait();
        }
        taken = true;
        this.philosopher = philosopher;
    }

    public synchronized void drop(Philosopher philosopher){
        taken = false;
        this.philosopher = null;
        notifyAll();
    }

    /**
     * 记录一下，这根筷子现在的使用情况
     * @return
     */
    public String takenInfo(){
        if(taken){
            return "the chopstick is taken by philosopher: " + this.philosopher.getId();
        }else{
            return "the chopstick is not taken by anyone";
        }
    }
}
