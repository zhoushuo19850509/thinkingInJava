package com.nbcb.thinkingInJava.test;


import java.math.BigInteger;

/**
 * 这个测试代码主要是为了验证著名的3x+1问题
 * 给定一个数字n
 * 如果这个数字是偶数，就除以2: n = n / 2
 * 如果这个数字是奇数，就把这个数字进行3x+1处理： n = 3 * n + 1
 * 最后这个数字会变成 4 2 1 ，并不断在这个三个数字之间循环
 */
public class Math3XPlus1Test {

    public static void main(String[] args) {

        int n = 1000000;
        int maxCount = 0;
        String maxNumbers = "";
        int maxNumber = 0;
        for (int i = 4; i < n; i++) {
            Result result = deal1(new BigInteger(String.valueOf(i)));
            if(result.getCount() > maxCount){
                maxCount = result.getCount();
                maxNumbers = result.getNumbers();
                maxNumber = i;
            }
            System.out.println("print the result count : " + i + " count: " + result.getCount());
        }
        System.out.println("number: " + maxNumber);
        System.out.println("最多计算了多少次: " + maxCount);
        System.out.println("中间计算过程的各个数字: " + maxNumbers);

//        int count = deal1(new BigInteger("113383"));
//        System.out.println("start print the result: " + count);


    }


    /**
     * 这个版本有一个问题，就是会越界，
     * n = 113383的时候，就会有问题了
     * @param n
     * @return 经过多少次运算，返回运算的次数
     */
    public static int deal( int n ){

        int count = 1;
        while(n != 4){
            if( n % 2 == 0){
                n = n / 2;
            }else{
                n = 3 * n + 1;
            }
            count++;
            System.out.println(n);
        }

        return count ;
    }

    /**
     * BigInteger 版本的处理函数，为了防止大数越界的问题
     * @param n
     * @return Result
     */
    public static Result deal1( BigInteger n ){

        Result result = new Result();


        int count = 1;  // 总共经过多少次运算
        StringBuilder sb = new StringBuilder(); // 中间运算过程的数字也要记录一下
        sb.append(n + " ");
        while(n.equals(new BigInteger("4")) == false){
            if( n.mod(new BigInteger("2")).equals(new BigInteger("0"))){
                n = n.divide(new BigInteger("2"));
            }else{
                n = n.multiply(new BigInteger("3")).add(new BigInteger("1"));
            }
            count++;
            sb.append(n + " ");
//            System.out.println(n);
        }
        result.setCount(count);
        result.setNumbers(sb.toString());
        result.setNumber(n);

        return result ;
    }


}

class Result{
    private BigInteger number = new BigInteger("0");
    private int count = 0; // 经过多少次运算
    private String numbers = ""; // 中间数字记录一下

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
