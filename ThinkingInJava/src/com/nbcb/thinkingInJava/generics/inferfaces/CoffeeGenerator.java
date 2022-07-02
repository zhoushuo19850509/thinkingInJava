package com.nbcb.thinkingInJava.generics.inferfaces;

import java.util.Iterator;
import java.util.Random;

/**
 * CoffeeGenerator 实现了Generator、
 */
public class CoffeeGenerator
        implements Generator<Coffee>,Iterable<Coffee> {

    private Class[] types = { Mocha.class, Americano.class,
            Breve.class, Cappuccino.class, Latte.class};

    private int size;

    private Random random = new Random(47);

    public CoffeeGenerator(int size) {
        this.size = size;
    }

    public CoffeeGenerator() {
    }

    /**
     * 实现了Generator接口的方法，用于生成随机的Coffee(子类)对象
     * 特别注意：types数组中，只是class类，并不是实例对象
     * 只有next()返回的才是实例化的对象
     * @return
     */
    @Override
    public Coffee next() {
        try {
            int index = random.nextInt(types.length);
            return (Coffee) types[index].newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 定义一个Iterator类，用于这种用法： for(Coffee coffee : new CoffeeGenerator(5))
    class CoffeeIterator implements Iterator<Coffee>{

        int count = size;

        @Override
        public boolean hasNext() {

            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next(); // 返回一个随机Coffee对象
        }
    }
    public Iterator<Coffee> iterator(){
        return new CoffeeIterator();
    }

    public static void main(String[] args) {
        CoffeeGenerator coffeeGenerator = new CoffeeGenerator(5);
//        for (int i = 0; i < 5; i++) {
//            System.out.println(coffeeGenerator.next());
//        }
        for(Coffee coffee : new CoffeeGenerator(8)){
            System.out.println(coffeeGenerator.next());
        }
    }

}
