package com.nbcb.thinkingInJava.io;

import java.io.*;
import java.util.Random;

/**
 * 这个类主要演示了对象序列化的例子
 *
 * 为啥要用Worm这个类来实例化呢？
 * 作者当然是有深意的
 * 因为Worm对象其实是一个列表，也就是说，当前对象引用了一连串的对象
 * 在序列化当前对象的时候，同时对这一连串的对象也做了序列化
 * 可以说是非常智能了
 *
 * 序列化的对象可以是持久化的本地文件，
 * 也可以是byte array，方便用于网络传输
 *
 * 这个例子中，最核心的代码是：
 *   ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("worm.out"));
 *   out.writeObject("Worm object as file \n");
 */
class Data implements Serializable{
    private int n;
    public Data(int n){
        this.n = n;
    }

    @Override
    public String toString() {
        return String.valueOf(this.n);
    }
}


public class Worm implements Serializable{
    private Random random = new Random();

    private Data[] dataArray = {
        new Data(random.nextInt(10)),
        new Data(random.nextInt(10)),
        new Data(random.nextInt(10)),
        new Data(random.nextInt(10))
    };

    private Worm next;
    private char c;

    public Worm(int i , char x){
        System.out.println("constructor" + i);
        c = x;
        if(--i > 0){
            /**
             * 循环操作，形成了一个链条
             */
            next = new Worm(i, (char)(x + 1));
        }
    }

    public Worm(){
        System.out.println("default constructor");
    }

    /**
     * 把Worm当前实例的信息打印出来
     * @return
     */
    public String toString(){
        StringBuilder result = new StringBuilder(":");
        result.append(c);
        result.append("(");
        for(Data data: dataArray){
            result.append(data);
        }
        result.append(")");

        /**
         * 把next打印出来，这里比较容易出错
         * 这里不仅仅只打印next单个节点的内容
         * 其实是有嵌套性质的，实际上是把一整串的Worm都打印出来了。
         */
        if(next != null){
            result.append(next);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Worm w = new Worm(6,'c');
        /**
         * 打印结果：
         * w= :c(2698):d(5129):e(7745):f(0929):g(0281):h(4180)
         */
        System.out.println("w= " + w);

        /**
         * 将Worm对象：w 序列化到worm.out文件中
         */
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream("worm.out"));
            out.writeObject("Worm object as file \n");
            out.writeObject(w);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 从文件中，讲Worm对象读取出来
         */
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("worm.out"));
            String s = (String)in.readObject();
            Worm w2 = (Worm)in.readObject();
            System.out.println(s + " w2= " + w2 );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 将Worm对象序列化到byte数组
         */
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out2 = null;
        try {
            out2 = new ObjectOutputStream(bout);
            out2.writeObject("Worm object as byte array\n");
            out2.writeObject(w);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                out2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ObjectInputStream in2 = null;
        try {
             in2 = new ObjectInputStream(
                    new ByteArrayInputStream(bout.toByteArray()));
             String s3 = (String)in2.readObject();
             Worm w3 = (Worm)in2.readObject();
             System.out.println(s3 + " w3= " + w3);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                in2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
