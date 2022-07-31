package com.nbcb.thinkingInJava.generics.latenttyping;


import com.nbcb.thinkingInJava.typeInformation.pets.Dog;

/**
 * Latent Typing的意思，就是”潜在的类型”。
 * 其实就是让java代码去猜测，一个对象有哪些方法，然后去执行这个代码。
 * 让某个类实现某个接口，然后我们就能知道这个类必然有哪些方法了
 *
 * 在这方面，和python/C++相比，java自动猜测对象包含哪些方法的能力，是有所不足的
 * java中如何说明某个对象包含哪些方法呢？
 * 一个典型的场景，就是让某个类实现某个接口，然后我们就能知道这个类必然有哪些方法了。
 *
 */

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
