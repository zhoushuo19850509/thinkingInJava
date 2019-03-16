package com.nbcb.thinkingInJava.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class FreezeAlien {
    public static void main(String[] args){
        ObjectOutput out = null;
        try {
             out = new ObjectOutputStream(
                     new FileOutputStream("alien.out"));
            Alien alien = new Alien();
            out.writeObject(alien);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
