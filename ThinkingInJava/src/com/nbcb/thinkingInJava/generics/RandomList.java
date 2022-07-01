package com.nbcb.thinkingInJava.generics;


import java.util.ArrayList;
import java.util.Random;

/**
 * 这是泛型在集合类中的第二个应用场景
 * RandomList的功能是这样的：从ArrayList中随机获取一个元素
 * 当然，这个元素是泛型的
 * @param <T>
 */
public class RandomList<T> {

    private ArrayList<T> list = new ArrayList<T>();

    private Random random = new Random(47);

    /**
     * 往RandomList中添加元素
     * @param element
     */
    public void add(T element){
        this.list.add(element);
    }

    /**
     * 从RandomList中随机获取一个元素
     * @return
     */
    public T getRandomElement(){
        int randomIndex = random.nextInt(list.size());
//        System.out.println("randomIndex : " + randomIndex);
        return list.get(randomIndex);
    }

    /**
     * 返回RandomList的长度
     * @return
     */
    public int size(){
        return list.size();
    }


    public static void main(String[] args) {
        RandomList<String> list = new RandomList<>();
        String content = "Hello Hob, welcome the Disney World...";

        for(String str : content.split(" ")){
            list.add(str);
        }


        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.getRandomElement() + " ");
            }
            System.out.println();
        }
    }



}
