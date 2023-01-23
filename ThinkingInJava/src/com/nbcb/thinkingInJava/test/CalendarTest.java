package com.nbcb.thinkingInJava.test;

import java.util.Calendar;

public class CalendarTest {
    public static void main(String[] args) {
        System.out.println("Calendar ...");
        Calendar time = Calendar.getInstance();
        System.out.println(time);
        System.out.println(time.getTime());

        time.set(Calendar.MINUTE,30);
        time.set(Calendar.SECOND,00);
        System.out.println(time.getTime());

        time.set(Calendar.MINUTE, time.get(Calendar.MINUTE) + 30);
        System.out.println(time.getTime());


    }
}
