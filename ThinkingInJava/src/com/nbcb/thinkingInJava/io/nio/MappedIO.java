package com.nbcb.thinkingInJava.io.nio;

import java.io.*;

/**
 *
 */
public class MappedIO {

    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;
    private static String filePath =
            "/Users/zhoushuo/Documents/delete/temp.tmp";

    /**
     * 抽象类Testser 作为各个测试类的基类
     */
    private abstract static class Tester{
        private String testName;
        /**
         * constructor
         * @param testName
         */
        public Tester(String testName) {
            this.testName = testName;
        }

        /**
         * 测试测试案例
         * 记录各个测试案例的运行时间
         */
        public void runTest(){
            try {
                long start = System.currentTimeMillis();
                test();
                long end = System.currentTimeMillis();
                System.out.println("test case: " + this.testName +
                        " time costs: " + ( end - start) + " ms");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 抽象方法，由各个实际的测试子类实现
         */
        public abstract void test() throws IOException;
    }


    private static Tester[] tests = {
            /**
             * class1 按照之前stream的方式，不断写入int到某个临时文件
            */
            new Tester("Stream Write"){
                @Override
                public void test() throws IOException {
                    DataOutputStream out = new DataOutputStream(
                            new BufferedOutputStream((
                                    new FileOutputStream(filePath)))
                    );
                    for (int i = 0; i < numOfInts; i++) {
                         out.writeInt(i);
                    }
                    out.close();
                }
            },
            new Tester("Mapped Write"){

                @Override
                public void test() {


                }
            },
            new Tester("Stream Read"){

                @Override
                public void test() {

                }
            },
            new Tester("Mapped Read"){

                @Override
                public void test() {

                }
            },new Tester("Stream Read/Write"){

                @Override
                public void test() {

                }
            },
            new Tester("Mapped Read/Write"){

                @Override
                public void test() {

                }
            }
    };


    /**
     * main方法遍历我们之前定义的Tester对象列表，执行各个test()方法
     * 比对各种Tester的性能
     * @param args
     */
    public static void main(String[] args) {
//        for(Tester tester : tests){
//            tester.runTest();
//        }

        Tester tester = tests[0];
        tester.runTest();
    }

}
