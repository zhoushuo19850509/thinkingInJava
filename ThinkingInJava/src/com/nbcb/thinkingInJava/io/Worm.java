package com.nbcb.thinkingInJava.io;

import java.io.*;
import java.util.Random;

/**
 * �������Ҫ��ʾ�˶������л�������
 *
 * ΪɶҪ��Worm�������ʵ�����أ�
 * ���ߵ�Ȼ���������
 * ��ΪWorm������ʵ��һ���б�Ҳ����˵����ǰ����������һ�����Ķ���
 * �����л���ǰ�����ʱ��ͬʱ����һ�����Ķ���Ҳ�������л�
 * ����˵�Ƿǳ�������
 *
 * ���л��Ķ�������ǳ־û��ı����ļ���
 * Ҳ������byte array�������������紫��
 *
 * ��������У�����ĵĴ����ǣ�
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
             * ѭ���������γ���һ������
             */
            next = new Worm(i, (char)(x + 1));
        }
    }

    public Worm(){
        System.out.println("default constructor");
    }

    /**
     * ��Worm��ǰʵ������Ϣ��ӡ����
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
         * ��next��ӡ����������Ƚ����׳���
         * ���ﲻ����ֻ��ӡnext�����ڵ������
         * ��ʵ����Ƕ�����ʵģ�ʵ�����ǰ�һ������Worm����ӡ�����ˡ�
         */
        if(next != null){
            result.append(next);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Worm w = new Worm(6,'c');
        /**
         * ��ӡ�����
         * w= :c(2698):d(5129):e(7745):f(0929):g(0281):h(4180)
         */
        System.out.println("w= " + w);

        /**
         * ��Worm����w ���л���worm.out�ļ���
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
         * ���ļ��У���Worm�����ȡ����
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
         * ��Worm�������л���byte����
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
