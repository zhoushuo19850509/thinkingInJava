package com.nbcb.thinkingInJava.generics.latenttyping;

/**
 * 这个例子非常牛逼，介绍了函数式编程的原理，要好好体会！
 * 实践了这个例子，就能够深入理解函数式编程的原理
 * 这个例子把generic的特性发挥到了极致
 *
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 定义各种实现函数式编程要用到的接口
 * @param <T>
 */

/**
 * 接口1 把两个元素T经过某种算法，进行处理，返回结果T
 * 用于reduce()
 * @param <T>
 */
interface Combiner<T> {
    T combine(T x, T y);
}

/**
 * 接口2 把元素T经过某种算法运算，返回结果R
 * 用于forEach()/transform()
 * @param <R>
 * @param <T>
 */
interface UnaryFunction<R, T>{
    R function(T x);
}

/**
 * 接口3 继承了UnaryFunction接口，把元素T经过某种算法运算，返回结果T
 * 用于forEach()
 * @param <T>
 */
interface Collector<T> extends UnaryFunction<T,T>{
    T result();
}

/**
 * 接口4 对元素T进行检测，返回boolean形式的结果true/false
 * 用于filter()
 * @param <T>
 */
interface UnaryPredicate<T>{
    boolean test(T x);
}





public class Functional {

    /**
     * 在这里定义4个函数式编程风格的方法
     * 1.reduce()
     * 2.forEach()
     * 3.transform()
     * 4.filter()
     */


    /**
     * 方法1 reduce()
     * reduce()是干嘛的呢？看代码就知道了，
     * 就是遍历某个集合中的各个元素，通过某种算法(combine算法)
     * 把各个元素累积上去(不一定是相加)
     */
    public static <T> T reduce(Iterable<T> seq, Combiner<T> combiner){
        Iterator<T> iterator = seq.iterator();
        /**
         * 下面这个循环做的事情是非常清楚的，就是类似斐波那契数列那样的，对一组元素进行累计(不一定是累加)
         * 但是这种遍历形式值得学习：先拿到第一个元素，然后遍历后续的元素
         * 我觉得比for循环要优雅
         */
        if(iterator.hasNext()){
            T result = iterator.next();
            while(iterator.hasNext()){
                result = combiner.combine(result, iterator.next());
            }
            return result;
        }
        return null;
    }


    /**
     * 方法2 forEach()
     * forEach()做得事情非常清楚，就是遍历一个集合，然后通过Collector算法对每个元素进行一定的计算
     * 然后把Collector算法对象返回
     * 后续我们可以通过Collector.result()的方式，把Collector算法整体计算结果返回
     * (备注：只返回整体计算记过，每个元素的处理结果不返回)
     */
    public static <T> Collector<T> forEach(Iterable<T> seq, Collector<T> func){
        for(T t : seq){
            func.function(t);
        }
        return func;
    }

    /**
     * 方法3 transform()
     * 这个方法的功能也非常清楚，就是遍历一个集合，通过UnaryFunction对每个元素T进行计算，得出结果R
     * 最后把这组结果的集合返回
     * @param seq
     * @param func
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> List<R> transform(Iterable<T> seq, UnaryFunction<R, T> func){
        List<R> list = new ArrayList<R>();
        for(T t : seq){
            R result = func.function(t);
            list.add(result);
        }
        return list;
    }


    /**
     * 方法4 filter()
     * 这个方法意思很明确哦，就是遍历一个集合，然后通过特定的算法func，检测每个元素
     * 看检测结果，如果检测结果为true，就把通过检测的元素放到一个列表中
     * 最终把所有符合要求的元素列表返回
     * @param seq
     * @param func
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(Iterable<T> seq, UnaryPredicate<T> func){

        List<T> list = new ArrayList<>();
        for(T t : seq){
            if(func.test(t)){
                list.add(t);
            }
        }
        return list;
    }


    /**
     * 既然我们已经把函数式编程的方法实现好了
     * 接下来，我们就来试试，
     * 我们定义几个具体的实现类，对应函数式编程中的"具体函数"
     */

    /**
     * 函数1 IntegerAdder
     * 实现了Combiner<T>接口，功能是两个Integer对象相加
     */
    static class IntegerAdder implements Combiner<Integer>{

        @Override
        public Integer combine(Integer x, Integer y) {
            return x + y;
        }
    }

    /**
     * 函数2 IntegerSubstracter
     * 实现了Combiner<T>接口，功能是两个Integer对象相减
     */
    static class IntegerSubstracter implements Combiner<Integer>{

        @Override
        public Integer combine(Integer x, Integer y) {
            return x - y;
        }
    }


    /**
     * 函数3 BigDecimalAdder
     * 实现了Combiner<T>接口，功能是两个BigDecimal对象相加
     */
    static class BigDecimalAdder implements Combiner<BigDecimal>{

        @Override
        public BigDecimal combine(BigDecimal x, BigDecimal y) {
            return x.add(y);
        }
    }

    /**
     * 函数4 BigIntegerAdder
     * 实现了Combiner<T>接口，功能是两个BigInteger对象相加
     */
    static class BigIntegerAdder implements Combiner<BigInteger>{

        @Override
        public BigInteger combine(BigInteger x, BigInteger y) {
            return x.add(y);
        }
    }

    /**
     * 函数5 AtomicLongAddr
     * 实现了Combiner<T>接口，功能是两个AtomicLong对象相加
     * 备注： 看起来是线程安全的
     */
    static class AtomicLongAddr implements Combiner<AtomicLong>{

        @Override
        public AtomicLong combine(AtomicLong x, AtomicLong y) {
            return new AtomicLong(x.addAndGet(y.get()));
        }
    }

    /**
     * 函数6 BigDecimalUlp
     * 实现了UnaryFunction，参数是BigDecimal类型
     * 进行的操作是获取参数的精度，比如参数是2.05，那么就返回0.01；如果参数是正整数或者0，就返回1
     */
    static class BigDecimalUlp implements UnaryFunction<BigDecimal , BigDecimal>{

        @Override
        public BigDecimal function(BigDecimal x) {
            return x.ulp();
        }
    }

    /**
     * 函数7 实现了UnaryPredicate接口，判断某个值是否大于当前值
     * 主要用于filter
     * @param <T>
     */
    static class GreaterThan<T extends Comparable<T>>
            implements UnaryPredicate<T>{

        private T bound;

        public GreaterThan(T bound) {
            this.bound = bound;
        }

        @Override
        public boolean test(T x) {
            return x.compareTo(bound) > 0;
        }
    }

    /**
     * 函数8 实现了Collector
     * 针对当前值，乘以某个数字
     */
    static class MultiplyingIntegerCollector implements Collector<Integer>{
        private Integer val = 1;

        @Override
        public Integer function(Integer x) {
            val *= x;
            return val;
        }

        @Override
        public Integer result() {
            return val;
        }
    }





    /**
     * 我们在main方法中试用一下之前定义的函数式编程的方法
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);

        /**
         * example1
         * 先试用reduce()方法，其中Combiner接口的实现类是IntegerAdder(实现两个Integer相加)
         * 实现的功能是，把list中每个元素相加
         * 我们看到函数式编程的威力了吧，只用一句代码就实现了如此复杂的集合操作，
         * 代码非常简洁、紧凑、优雅
         */
        Integer result = reduce(list, new IntegerAdder());
        System.out.println(result);

        /**
         * example2
         * 试用filter()方法，其中UnaryPredicate接口的实现类是GreaterThan
         * 实现的功能是：把list中所有大于4的元素找出来，放到一个新的集合中
         */
        List<Integer> list1 = filter(list,new GreaterThan<Integer>(4));
        System.out.println(list1);


        /**
         * example3
         * 试用forEach()方法,其中的function是MultiplyingIntegerCollector()
         * 整体实现的功能是，遍历list，把list中每个元素都乘起来，并返回最终结果
         *
         */
        System.out.println(list);
        System.out.println(forEach(list,new MultiplyingIntegerCollector()).result());


        /**
         * example4
         * 试用forEach()/filter()的组合
         * 先通过filter()进行过滤，然后在通过forEach()对过滤后的元素进行计算
         * 看上去很复杂，其实只要把example3/4整合一下就行了
         *
         */
        System.out.println(list);
        System.out.println(forEach(filter(list,new GreaterThan<Integer>(4))
                ,new MultiplyingIntegerCollector()).result());

        /**
         * example5
         * 先试用reduce()方法，其中Combiner接口的实现类是BigDecimalAdder
         * (实现两个BigDecimal相加)
         *  实现的功能是，把list中每个BigDecimal元素相加
         *  最终得到一个指定精读的结果
         *
         *  这个example和example1类似
         */
        MathContext mc = new MathContext(7); // 用来指定BigDecimal的禁锢
        List<BigDecimal> lbd = Arrays.asList(new BigDecimal(1.1,mc),
                new BigDecimal(2.2,mc),
                new BigDecimal(3.3,mc),
                new BigDecimal(4.4,mc));
        BigDecimal rbd = reduce(lbd,new BigDecimalAdder());
        System.out.println(rbd);


        /**
         * example6
         * 使用filter()方法，功能是：把list中所有大于BigDecimal(3)的元素找出来，
         * 放到一个新的集合中
         * 和example2类似
         *
         */
        System.out.println(filter(lbd, new GreaterThan<BigDecimal>(new BigDecimal(3))));

        /**
         * example7
         * 先创建一个素数的列表
         * 然后把这个列表中所有的素数都累加起来
         */
        List<BigInteger> lbi = new ArrayList<>();
        BigInteger bi = BigInteger.valueOf(11);
        for (int i = 0; i < 11; i++) {
            lbi.add(bi);
            bi = bi.nextProbablePrime();
        }
        System.out.println(lbi);

        BigInteger rbi = reduce(lbi,new BigIntegerAdder());
        System.out.println(rbi);

        // 把以上素数集合的元素相加也还是素数
        System.out.println(rbi.isProbablePrime(5));

        /**
         * example8
         * 一个集合包含的是一些AtomicLong元素
         * 通过redue()把这些元素相加
         * 代码非常简练
         */
        List<AtomicLong> lal = new ArrayList<>();
        lal.add(new AtomicLong(11));
        lal.add(new AtomicLong(47));
        lal.add(new AtomicLong(74));
        lal.add(new AtomicLong(133));
        AtomicLong ral = reduce(lal, new AtomicLongAddr());
        System.out.println(ral);


        /**
         * example9
         * 一个集合包含一些BigDecimal元素
         * 通过tranform()方法计算这些元素的精读
         */
        System.out.println(lbd);
        System.out.println(transform(lbd,new BigDecimalUlp()));


    }


}
