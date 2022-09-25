package com.nbcb.thinkingInJava.io.serialization;

import java.io.*;

public class ThawAlient {
    public static void main(String[] args){
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(new File("alien.out")));
            Object object = in.readObject();
            System.out.println(object.getClass());
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
    }

}
