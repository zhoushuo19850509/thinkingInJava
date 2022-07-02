package com.nbcb.thinkingInJava.generics.inner;


import com.nbcb.thinkingInJava.generics.inferfaces.Generator;
import com.nbcb.thinkingInJava.generics.methods.Generators;

import java.util.*;


/**
 * 这个代码是为了说明generic在inner class中的应用场景
 * 我们观察Customer、Teller两个类
 * 两个类中分别定义了一个static方法，static方法中都定义了内部类： Generator
 *
 * 为啥要在内部类中使用泛型呢？
 * 我们回顾一下generics/interfaces package下，
 * 我们要定义某个特定类的Generator实现类，比如CoffeeGenerator，就要单独写一个文件
 * 如果使用内部类，就不用单独写一个文件，大大精简了代码。
 * 正如代码提示中所写，这个写法还能够通过lamda表达式，进一步精简。
 *
 */

/**
 * 定义客户类
 */
class Customer{

    public static long counter = 1; // 全局变量
    public final long id = counter++;  // 标识对象id

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }

    public static Generator<Customer> generator(){
        return new Generator<Customer>() {
            @Override
            public Customer next() {
                return new Customer();
            }
        };
    }

}

/**
 * 定义柜员类
 */
class Teller{
    public static long counter = 1; // 全局变量
    public final long id = counter++;  // 标识对象id

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }

    public static Generator<Teller> generator(){
        return new Generator<Teller>() {
            @Override
            public Teller next() {
                return new Teller();
            }
        };
    }
}

/**
 * 这个类说明柜员如何处理客户端
 */
public class BankTeller {

    /**
     * 定义一个server()方法，说明Teller如何服务Customer ...
     * @param teller
     * @param customer
     */
    public static void serve(Teller teller, Customer customer){
        System.out.println("teller " + teller +
                " is serving customer: " + customer);
    }


    public static void main(String[] args) {

        // 先定义一个Customer队列
        Queue<Customer> customers = new LinkedList<Customer>();

        Generators.fill(customers, Customer.generator(), 15);


        // 再定义一个Teller队列
        List<Teller> tellers = new ArrayList<Teller>();
        Generators.fill(tellers, Teller.generator(), 5);

        Random random = new Random(47);
        for(Customer customer : customers){
            // 随机从柜员中取一个
            int tellerIndex = random.nextInt(tellers.size());
            // 用这个随机的柜员来服务客户
            BankTeller.serve(tellers.get(tellerIndex), customer);
        }

    }


}





