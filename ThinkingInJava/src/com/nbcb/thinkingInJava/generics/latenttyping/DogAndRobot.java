package com.nbcb.thinkingInJava.generics.latenttyping;


import com.nbcb.thinkingInJava.typeInformation.pets.Dog;

class Communicate {

    /**
     * 定义一个静态方法，
     * 这个方法的参数是某个Performs接口的实现类
     * 然后调用Performs接口对应的实现方法
     * @param performer
     * @param <T>
     */
    public static <T extends Performs>
    void performs(T performer){
        performer.speak();;
        performer.sit();
    }
}


class PerformingDog extends Dog implements Performs{

    @Override
    public void speak() {
        System.out.println("Woo...");
    }

    @Override
    public void sit() {
        System.out.println("sitting...");
    }
}

class Robot implements Performs{

    @Override
    public void speak() {
        System.out.println("click...");
    }

    @Override
    public void sit() {
        System.out.println("clank!");
    }
}


public class DogAndRobot {

    public static void main(String[] args) {
        PerformingDog performingDog = new PerformingDog();
        Robot robot = new Robot();
        Communicate.performs(performingDog);
        Communicate.performs(robot);
    }
}
