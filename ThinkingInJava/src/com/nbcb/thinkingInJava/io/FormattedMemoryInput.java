package com.nbcb.thinkingInJava.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 *  File -> String -> ByteArrayInputStream -> DataInputStream -> chars
 */
public class FormattedMemoryInput {
    public static void main(String[] args)  {
        String filePath = "/Users/zhoushuo/Documents/delete/a.txt";
        DataInputStream in = null;
        try {
            in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile.read(filePath).getBytes()
                    )
            );
            while(true){
                System.out.println((char) in.readByte());
            }
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("end of stream ... ");
        }
    }
}
