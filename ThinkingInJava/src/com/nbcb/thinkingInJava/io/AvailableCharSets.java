package com.nbcb.thinkingInJava.io;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * 打印当前环境下所有的字符集编码，
 * 以及各个字符集编码下对应的别名
 */
public class AvailableCharSets {

    public static void main(String[] args) {
        SortedMap<String,Charset> charSets = Charset.availableCharsets();

        // 遍历打印当前环境下所有的字符集编码
        Iterator<String> iterator = charSets.keySet().iterator();
        while(iterator.hasNext()){
            String charSetName = iterator.next();
            System.out.println(charSetName);

            // 各个字符集编码下，还有各种别名
            Iterator<String> aliases = charSets.get(charSetName).aliases().iterator();
            while(aliases.hasNext()){
                System.out.println("   " + aliases.next());
            }
        }

    }

}
