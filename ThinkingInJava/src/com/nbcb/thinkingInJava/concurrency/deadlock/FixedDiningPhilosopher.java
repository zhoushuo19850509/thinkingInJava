package com.nbcb.thinkingInJava.concurrency.deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 避免了死锁的版本
 * 如何避免死锁呢？
 * 我们先来看看，引起死锁的条件有哪些？
 * 1.必须有独占的资源(比如筷子，一根筷子没办法同时被两个哲学家拿起来)
 * 2.资源被占有后，不能被其他人抢走(比如筷子被一个哲学家拿起来之后，不能别其他哲学家抢走)
 * 3.必须有这样的场景：一个人占有资源A之后，又想占有资源B
 * 4.必须有环形占有资源的场景，比如A想要占有B的资源，B想要占有C资源，C想要占有A的资源
 *
 * 为了避免死锁，只要解决其中一点就行了
 * 我们选择第四点，打破环形占用资源的场景
 *
 * 思路也很直接，就是改变最后一个哲学家拿筷子的方式，改为：
 * 先拿右边的筷子，再拿左边的
 * 这样就避免了循环依赖
 *
 * =========================
 * 这个解决死锁的场景，给我们提供了一个很好的思路
 * 我们在项目中，也会经常碰到死锁的场景
 * 比如我们解决了很久了拷贝服务问题。本质上就是两个长事务造成的死锁。
 * 场景也很好重现：
 * 事务A，扫描完成后立即触发上传，要做的事情是：
 * 0.保存索引信息(biz/doc/file/传输表)
 * 1.更新传输表状态；
 * 2.(起一个单独的线程)触发文件上传；
 * 3.触发上传成功后(子线程)会写数据(更新file表、删除传输表)
 *
 * 事务B，跨网拷贝，要做的事情：
 * 1.(同步)触发上传；
 *   修改传输表、同步触发上传、回写索引数据(更新file表、删除传输表)
 * 2.(同步)触发NAS上传；
 *   修改trans_nas表、触发上传到NAS、回写索引数据(更新file表、删除trans_nas表)
 *
 * 我们通过日志发现，事务B经常会发生死锁(超时)
 * 发生死锁的地方包括：
 * 1.(同步)触发上传；
 * 回写索引数据(更新file表cmfileId字段）
 * 2.(同步)触发NAS上传；
 * 回写索引数据(更新file表nasId字段)
 *
 * 为什么会发生死锁呢？
 * 总体来说，是一个死锁的环：
 * 事务B触发上传完成后，回写索引数据更新file表cmfileId字段的时候，file表这条记录正在被事务A占用着。因为事务A触发上传结束后，先更新file表cmfileId
 * 事务A更新完file表cmfileId，要开始删除传输表的时候，发现传输表被事务B锁着，因为事务B一上来就是更新传输表状态。
 * 这样，就造成了事务A和事务B死锁的场景。
 *
 * 后续：
 * 1.为了更加形象说明死锁，建议参考之前考数据库工程师时候的参考书，那里有死锁相关的图例和解决方案
 * 2.深入了解一下Spring事务控制、数据库事务控制的原理，至少要搞清楚在死锁超时异常抛出来的时候，是谁在占用数据库的行级锁
 *
 * 如何解决死锁呢？
 *
 */
public class FixedDiningPhilosopher {

    public static void main(String[] args) throws InterruptedException {
        int size = 5;

        /**
         * 这个参数可以调整哲学家思考的时间
         * 如果设置为0，说明哲学家不思考，一直拿筷子吃饭
         * 这个值设置得越小，越容易重现死锁的场景
         * 当然，这个代码是经过死锁优化的，避免了死锁的产生，所以即便设置为0，也不会重现死锁
         */
        int ponderFactor = 1;

        /**
         * 初始化筷子
         */
        List<Chopstick> chopsticks = new ArrayList<>(size);

        for(int i = 0 ; i < size; i++){
            chopsticks.add(new Chopstick());
        }

        /**
         * 初始化哲学家
         */
        List<Philosopher> philosophers = new ArrayList<Philosopher>(size);
        Chopstick leftChopstick = null;
        Chopstick rightChopstick = null;
        for(int i = 0 ; i < size; i++){
            /**
             * 1-4个哲学家拿筷子还是原来的方式：先拿左边筷子，再拿右边筷子
             */
            if( i < size - 1){
                leftChopstick = chopsticks.get(i);
                rightChopstick = chopsticks.get( i + 1); // 这个取余一下
            }else{
                /**
                 * 最后一个哲学家改变一下：先拿右边的筷子，再拿左边的
                 */
                leftChopstick = chopsticks.get(0);
                rightChopstick = chopsticks.get(i);
            }
            philosophers.add(new Philosopher(leftChopstick, rightChopstick, i, ponderFactor));
        }

        /**
         * 哲学家们开始思考
         */
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < size; i++){
            exec.execute(philosophers.get(i));
        }

        /**
         * 每隔5s，打印一下筷子的状态
         */
        while(true){
            Thread.sleep(1 * 1000);
            System.out.println("===================== start printing chopsticks info");
            for(int i = 0 ; i < size; i++){
                System.out.println("chopstick id: [" + i + "] taken info: " +
                        chopsticks.get(i).takenInfo());
            }

        }
    }
}
