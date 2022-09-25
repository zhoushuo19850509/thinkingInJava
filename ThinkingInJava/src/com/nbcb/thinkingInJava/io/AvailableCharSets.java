package com.nbcb.thinkingInJava.io;


import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * ��ӡ��ǰ���������е��ַ������룬
 * �Լ������ַ��������¶�Ӧ�ı���
 */
public class AvailableCharSets {

    public static void main(String[] args) {
        SortedMap<String,Charset> charSets = Charset.availableCharsets();

        // ������ӡ��ǰ���������е��ַ�������
        Iterator<String> iterator = charSets.keySet().iterator();
        while(iterator.hasNext()){
            String charSetName = iterator.next();
            System.out.println(charSetName);

            // �����ַ��������£����и��ֱ���
            Iterator<String> aliases = charSets.get(charSetName).aliases().iterator();
            while(aliases.hasNext()){
                System.out.println("   " + aliases.next());
            }
        }

    }

}
