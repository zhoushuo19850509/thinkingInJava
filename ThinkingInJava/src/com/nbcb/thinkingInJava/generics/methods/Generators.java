package com.nbcb.thinkingInJava.generics.methods;


import com.nbcb.thinkingInJava.generics.inferfaces.Coffee;
import com.nbcb.thinkingInJava.generics.inferfaces.CoffeeGenerator;
import com.nbcb.thinkingInJava.generics.inferfaces.Fibonacci;
import com.nbcb.thinkingInJava.generics.inferfaces.Generator;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Generics 在methods中的应用场景1
 *
 * 之前我们在interfaces这个package下开发了一个通用接口Generator
 * 这个接口有一个方法： next() 用来产生一个个泛型元素
 *
 * 在Generators这个文件里，我们更近一步，
 * 通过Generator.next()创建一个个元素，创建一个长度为n的集合类
 * 通过fill()方法中应用泛型，最大限度保证了Generators的通用性
 *
 * 这个文件非常有用，续我们如果要创建一个集合类，就可以用这个方法
 */
public class Generators {

    public static <T> Collection<T> fill(Collection<T> coll,
                                         Generator<T> gen, int n){
        for (int i = 0; i < n; i++) {
            coll.add(gen.next());
        }
        return coll;
    }

    public static void main(String[] args) {
        // 试用一下Generators功能

        /**
         * 试用场景1 通过CoffeeGenerator创建一个包含Coffee元素的ArrayList
         */
        Collection<Coffee> coffees = Generators.fill(
                new ArrayList<Coffee>(), new CoffeeGenerator(),10
        );

        for(Coffee coffee : coffees){
            System.out.println(coffee);
        }

        /**
         * 试用场景2 通过Fibonacci generator,创建一个包含斐波那契数的ArrayList
         */
        System.out.println("start fib generate ...");
        Collection<Integer> fibs = Generators.fill(
                new ArrayList<Integer>(), new Fibonacci(), 15);
        for(Integer integer : fibs){
            System.out.println(integer);
        }



    }

}
