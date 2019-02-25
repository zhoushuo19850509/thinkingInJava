package com.nbcb.thinkingInJava.concurrency;

/**
 * 这个文件列出了几种通过inner class启动线程的场景
 */


class InnerThread1{

    private int countDown = 5;

    Inner inner;
    /**
     * inner class defined
     */
    private class Inner extends Thread{

        /**
         * contructor of inner class
         * @param name
         */
        public Inner(String name){
            /**
             * set the name of inner thread
             */
            super(name);
            start(); // start the inner class thread

        }

        public String toString(){
            return "#" + getName() + ":" + countDown;
        }

        public void run(){
            while(true){
                System.out.println(this);
                if(--countDown == 0){
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * constructor
     */
    public InnerThread1(String name){
        inner = new Inner(name);

    }

}

class InnerThread2{
    private Thread t;
    private int countDown = 5;

    /**
     * constructor
     */
    public InnerThread2(String name){
        t = new Thread(name){
            public void run(){
                while(true){
                    System.out.println(this);
                    if(--countDown == 0){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            public String toString(){
                return "#" + getName() + ":" + countDown;
            }

        };
        t.start();

    }

}

class InnerRunnable1{

    private Inner inner;
    private int countDown = 5;

    private class Inner implements Runnable{

        private Thread t;
        public Inner(String name){
            t = new Thread(this,name);
            t.start();
        }

        public void run(){
            while(true){
                System.out.println(this);
                if(--countDown == 0){
                    return;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        public String toString(){
            return "#" + t.getName() + ":" + countDown;
        }
    }

    public InnerRunnable1(String name){
        inner = new Inner(name);
    }


}

class InnerRunnable2{
    private Thread t;
    private int countDown = 5;

    public InnerRunnable2(String name){
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println(this);
                    if(--countDown == 0){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public String toString(){
                return "#" + Thread.currentThread().getName() + ":" + countDown;
            }
        },name);
        t.start();
    }

}

public class ThreadVariations {
    public static void main(String[] args){
//        new InnerThread1("InnerThread1");
//        new InnerThread2("InnerThread2");
//        new InnerRunnable1("InnerRunnable1");
        new InnerRunnable2("InnerRunnable2");

    }

}
