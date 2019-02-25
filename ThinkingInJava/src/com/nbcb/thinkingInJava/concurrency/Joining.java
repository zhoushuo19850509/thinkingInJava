package com.nbcb.thinkingInJava.concurrency;



class Sleeper extends Thread{
    private int duration;

    public Sleeper(String name ,int sleepTime){
        super(name);
        this.duration = sleepTime;
        start();
    }

    public void run(){
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() +
                    " was interrupted! isInterrupted(): " + isInterrupted());
            return;
        }
        System.out.println(getName() +
                " has awake!");
    }

}

class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run(){
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Inerrupted!");
        }
        System.out.println(getName() + " join has completed" );
    }

}



public class Joining {
    public static void main(String[] args){
        /**
         * Sleeper
         */
        Sleeper sleepy = new Sleeper("Sleepy",6000);
        Sleeper grumy = new Sleeper("grumy",5000);

        /**
         * Joiner
         */
        Joiner dopey = new Joiner("Dopey",sleepy);
        Joiner doc = new Joiner("Doc",grumy);
        grumy.interrupt();

    }
}
