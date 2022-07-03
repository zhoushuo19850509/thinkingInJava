package com.nbcb.thinkingInJava.generics.complexmodel;


import com.nbcb.thinkingInJava.generics.inferfaces.Generator;
import com.nbcb.thinkingInJava.generics.methods.Generators;

import java.util.ArrayList;
import java.util.Random;

/**
 * 这个文件主要是为了说明泛型在复杂结构模型中的应用
 */


/**
 * class1 产品
 */
class Product{

    private final int id; // id of the Product
    private double price;
    private String desc;

    // constructor
    public Product(int id, double price, String desc) {
        this.id = id;
        this.price = price;
        this.desc = desc;
    }

    /**
     * 发布一个Generator类，这个类实现了Generator接口
     * 通过接口方法next9)返回一个随机的Product对象
     *
     * 注意：这里用到了inner class , implement Generator interface
     * 具体用法参考可以参考： BankTeller.java
     *
     * @return
     */
    public static Generator<Product> generator =
            new Generator<Product>() {
                Random random = new Random(47);

                @Override
                public Product next() {
                    int randomId = random.nextInt(1000);
                    double randomPrice = random.nextDouble() * 1000 + 0.99;
                    String testDesc = "Test";
                    return new Product(randomId, randomPrice, testDesc);
                }
        };


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                '}';
    }
}

/**
 * class2 货架(一个货架上包含一组产品)
 * 通过Generators.fill()方法，产生一组产品，具体参考 BankTeller.java
 */
class Shelf extends ArrayList<Product> {
    /**
     * constructor
     * @param nProducts 一组产品的数量
     */
    public Shelf(int nProducts) {
        Generators.fill(this, Product.generator, nProducts);
    }
}

/**
 * class3 过道(一个过道包含若干个货架)
 *
 */
class Aisle extends ArrayList<Shelf>{
    public Aisle(int nShelies, int nProducts) {

        /**
         * 通过for循环，往过道中添加一组货架
         */
        for (int i = 0; i < nShelies; i++) {
             add(new Shelf(nProducts));
        }
    }
}


/**
 * class4 商店(一个商店包含多个过道)
 */
public class Store extends ArrayList<Aisle>{

    /**
     * constroctor of Store class
     * 这个类的作用是，根据指定的通道数、货架数、产品数，搭建起一个商店
     * @param nAisles 通道
     * @param nShelies 货架
     * @param nProducts 产品
     */
    public Store(int nAisles, int nShelies, int nProducts) {
        for (int i = 0; i < nAisles; i++) {
            add(new Aisle(nShelies, nProducts));
        }
    }

    /**
     * 验证一下Store类的功能
     * @param args
     */
    public static void main(String[] args) {
        Store store = new Store(3,5,10);


        for(Aisle aisle : store){
            for(Shelf shelf : aisle){
                for(Product product : shelf){
                    System.out.println(product);
                }
            }
        }


    }


}
