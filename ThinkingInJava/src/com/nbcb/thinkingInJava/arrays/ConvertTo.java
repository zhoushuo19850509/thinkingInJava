package com.nbcb.thinkingInJava.arrays;


/**
 * 这个ConvertTo类，主要提供一些static方法
 * 将wrapper object 转化为primitive object，比如将Boolean[]转为boolean[]
 */
public class ConvertTo {

    /**
     * 将Boolean[]转为boolean[]
     * @param in
     * @return
     */
    public static boolean[] primitive(Boolean[] in){

        boolean[] out = new boolean[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }

    /**
     * 将Character[]转为char[]
     * @param in
     * @return
     */
    public static char[] primitive(Character[] in){

        char[] out = new char[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];
        }
        return out;
    }


    /**
     * 通过main方法验证convertTo功能
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 通过CountingGenerator创建数组 ： Boolean[]
         * 然后通过Convert将Boolean[]转为boolean[]
         *
         */
        Boolean[] b =
                Generated.array(Boolean.class,new CountingGenerator.Boolean(),5);

        System.out.println("before convert ...");
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }

        boolean[] bb = ConvertTo.primitive(b);
        System.out.println("after convert ...");
        for (int i = 0; i < bb.length; i++) {
            System.out.println(bb[i]);
        }

        /**
         * 通过RandomGenerator创建数组 ： Character[]
         * 然后通过Convert将Character[]转为char[]
         *
         */
        Character[] c = Generated.array(Character.class,
                new RandomGenerator.RandomCharacter(),10);

        System.out.println("before convert ...");
        for (int i = 0; i < c.length; i++) {
            System.out.println(c[i]);
        }

        char[] cc = ConvertTo.primitive(c);
        System.out.println("after convert ...");
        for (int i = 0; i < cc.length; i++) {
            System.out.println(cc[i]);
        }



    }

}
