package com.nbcb.thinkingInJava.test;

import java.math.BigDecimal;

public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1.2);
        BigDecimal b = new BigDecimal(3.03);
        BigDecimal result = a.add(b).setScale(2,BigDecimal.ROUND_HALF_EVEN);
        System.out.println(result);
        System.out.println(result.ulp());

        BigDecimal  cc= new BigDecimal(550);
        System.out.println(cc.ulp());
    }
}
