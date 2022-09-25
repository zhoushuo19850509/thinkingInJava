package com.nbcb.thinkingInJava.io.nio;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * 这个代码是对我们之前学习的stream/nio(mapped io)的读写性能，进行一个比对
 * 大致思路就是先写一个Tester框架，然后针对stream/nio的各个读写场景写测试类
 * 最后运行各个读写场景，记录各个场景的耗时
 *
 * 从测试结果来看，nio(mapped io)的性能大大优于stream框架：
 *
 * test case: Stream Write time costs: 198 ms
 * test case: Mapped Write time costs: 24 ms
 * test case: Stream Read time costs: 188 ms
 * test case: Mapped Read time costs: 5 ms
 * test case: Stream Read/Write time costs: 5259 ms
 * test case: Mapped Read/Write time costs: 4 ms
 *
 * 这个测试框架也是很值得学习的，后续我们在自动化测试案例中也能够用到，整体架构为：
 * 把耗时统计这种通用的代码，放到基类(Tester)中，各个子类中写真正的测试逻辑
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
             * object_1 按照之前stream的方式，不断写入int到某个临时文件
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
            /**
             * object_2 按照NIO FileChannel的方式，不断写入int到某个临时文件
            */
            new Tester("Mapped Write"){

                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile(filePath,"rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size()).asIntBuffer();
                    for (int i = 0; i < numOfInts; i++) {
                         ib.put(i);
                    }
                    fc.close();
                }
            },
            /**
             * object_3 按照之前stream的方式，不断某个临时文件读取Int
            */
            new Tester("Stream Read"){

                @Override
                public void test() throws IOException {
                    DataInputStream in = new DataInputStream(
                            new BufferedInputStream(
                            new FileInputStream(filePath)
                    ));
                    for (int i = 0; i < numOfInts; i++) {
                        in.readInt();
                    }
                    in.close();
                }
            },
            /**
             * object_4 按照NIO FileChannel的方式，不断从某个临时文件读取内容
            */
            new Tester("Mapped Read"){

                @Override
                public void test() throws IOException {
                    FileChannel fc = new FileInputStream(new File(filePath)).getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size())
                            .asIntBuffer();
                    for (int i = 0; i < numOfInts; i++) {
                        ib.get();
                    }
                    fc.close();
                }
            },
            /**
             * object_5 按照之前stream的方式，不断某个临时文件写入Int、读取Int
            */
            new Tester("Stream Read/Write"){

                @Override
                public void test() throws IOException {

                    RandomAccessFile raf = new RandomAccessFile(new File(filePath),"rw");
                    raf.writeInt(1);
                    for (int i = 0; i < numOfUbuffInts; i++) {
                         raf.seek(raf.length() - 4);
                         raf.writeInt(raf.readInt());

                    }

                }
            },
            /**
             * object_6 按照NIO FileChannel的方式，不断从某个临时文件写入内容、读取内容
            */
            new Tester("Mapped Read/Write"){

                @Override
                public void test() throws IOException {
                    FileChannel fc = new RandomAccessFile(filePath,"rw").getChannel();
                    IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE,0,fc.size())
                            .asIntBuffer();
                    ib.put(0);
                    for (int i = 1; i < numOfUbuffInts; i++) {
                        ib.put(ib.get(i - 1));
                    }
                    fc.close();
                }
            }
    };


    /**
     * main方法遍历我们之前定义的Tester对象列表，执行各个test()方法
     * 比对各种Tester的性能
     * @param args
     */
    public static void main(String[] args) {
        for(Tester tester : tests){
            tester.runTest();
        }

//        Tester tester = tests[4];
//        tester.runTest();
    }

}
