package com.nbcb.thinkingInJava.generics.latenttyping;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 这个文件主要是为了说明如何通过反射的方式
 * 解决latent type的问题
 *
 */

class Mine{

    public void speak(){
        System.out.println("my object speak...");
    }

    @Override
    public String toString() {
        return "Mine{}";
    }
}

class SmartDog{
    public void speak(){
        System.out.println("mart dog speak...");
    }

    public void sit(){
        System.out.println("smart dog sit...");
    }

}


class CommunicateReflectivity{

    /**
     * 定义一个static 方法，这个方法通过反射的方式
     * 调用speaker对象中指定的方法
     */
    public static void perform(Object speaker) {


        /**
         * 先处理speak()方法
         */
        Class spkr = speaker.getClass();
        Method speak = null;
        try {
            speak = spkr.getMethod("speak");
            speak.invoke(speaker);
        } catch (NoSuchMethodException e) {
            System.out.println(speaker + " cannot talk ...");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        /**
         * 再处理sit()方法
         */
        Method sit = null;
        try {
            sit = spkr.getMethod("sit");
            sit.invoke(speaker);
        } catch (NoSuchMethodException e) {
            System.out.println(speaker + " cannot sit ...");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}


public class LatentReflection {


    public static void main(String[] args) {

        CommunicateReflectivity.perform(new SmartDog());
        CommunicateReflectivity.perform(new Robot());
        CommunicateReflectivity.perform(new Mine());
    }

}
