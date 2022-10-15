package com.nbcb.thinkingInJava.strings.basic;

/**
 * 这个代码为了说明，在String拼接的场景中，为啥必须要用StringBuilder
 *
 * 怎么说明呢？
 * 首先，这里有两个方法，分别采用String直接拼接、通过StringBuilder拼接
 * 然后我们通过javap -c 的方式调研WithStringBuilder.java的bytecode
 * 从bytecode介绍文件(WithStringBuilder_bytecode.txt)中可以看到
 * implicit()存在什么问题呢？JVM在每次for循环中，都会创建一个StringBuilder()对象
 * 这显然会消耗大量的资源。
 * 反观explicit()方法，就好多了，在for循环外围就创建了StringBuilder()对象
 * 这样消耗资源就稍多了，并且bytecode代码整体更加精简。
 *
 */
public class WithStringBuilder {

    /**
     * 采用String直接拼接
     * @param fields
     * @return
     */
    public String implicit(String[] fields){
        String result = "";
        for (int i = 0; i < fields.length; i++) {
            result += fields[i];
        }
        return  result;
    }

    /**
     * 通过StringBuilder拼接
     * @param fields
     * @return
     */
    public String explicit(String[] fields){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            result.append(fields[i]);
        }
        return  result.toString();
    }

}
