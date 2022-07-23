package com.nbcb.thinkingInJava.generics.mixins.proxy;

import com.nbcb.thinkingInJava.generics.mixins.basic.*;
import net.mindview.util.Tuple;
import net.mindview.util.TwoTuple;
import sun.security.x509.SerialNumber;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过动态代理实现mixin
 *
 */

/**
 * 先定义一个InvocationHandler实现类
 * 这个InvocationHandler实现的功能是这样的：
 * 1.constructor中传入一对对的<实现类,接口>
 * 2.创建一个static方法：newInstance()
 *   这个方法负责生成一个动态代理类
 *   这个动态代理类包含<实现类,接口>中所有接口的方法
 *   这样，这个动态代理类就实现了类似mixin的功能：
 *   整合了一系列对象，并且可以调用这些对象的所有方法
 *
 */
class MixinProxy implements InvocationHandler{


    Map<String, Object> deletegatesByMethods;

    /**
     * constructor of MixinProxy
     * constructor做了那些事情呢？
     * 1.遍历参数传进来的一组implementation/interface
     * 2.分析各个interface中所有的method，
     * 3.然后组装一个map，
     *   map/key：method name,
     *   map/value：该method所在的interface对应的implementation
     *
     *   我们举一个实例，比如：
     *   new MixinProxy(TwoTuple<new BasicImpl(), Basic.class>)
     *   我们知道Basic接口包含两个方法： getValue()/setValue()
     *   那么constructor做的事情就是往deletegatesByMethods添加如下内容：
     *   <"getValue",BasicImpl对象>
     *   <"setValue",BasicImpl对象>
     *
     *   那么，为啥要这么做呢？后续我们在invoke()方法中，要根据method name，
     *   找到该Method name所属的实现类，然后执行这个实现类的method方法
     * @param pairs 这个constructor的参数是啥意思呢？
     *              我们先看一下TwoTuple<>这个类
     *              这个类也用到了泛型，包括两个泛型对象：
     *              1.对象1 是一个实现类；
     *              2.对象2 该实现类对应的的接口；
     *              我们再看一下...，这意味着参数可以传很多TwoTuple对象
     *              这样我们就清楚了，constructor的参数，包含很多对的implementation/interface
     */
    public MixinProxy(TwoTuple<Object, Class<?>>... pairs) {
        deletegatesByMethods = new HashMap<String, Object>();

        for(TwoTuple<Object, Class<?>> pair : pairs){
            for(Method method : pair.second.getMethods()){
                String methodName = method.getName();
                if(!deletegatesByMethods.containsKey(methodName)){
                    deletegatesByMethods.put(methodName, pair.first);
                }
            }
        }
    }

    /**
     * invoke()方法是实现InvocationHandler接口方法
     *
     * @动态代理功能回顾
     * invoke()方法是什么意思，我们应该知道，如果不知道，参考动态代理的设计模式
     * 简单来说，method就是原对象包含的各个方法，args是method对应的参数
     * 在动态代理模式中，我们可以通过遍历原对象的各个方法，在原对象各个方法中塞入一些业务逻辑
     *
     * 那么，在这个场景，我们所做的事情，就是根据method，从
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        Object deletegate = deletegatesByMethods.get(methodName);
        return method.invoke(deletegate,args);
    }

    /**
     * 这里自定义一个newInstance方法，
     * 在Java自带的Proxy.newInstance()基础上，做一些调整
     *
     * pairs啥意思，我们在constructor中已经说明了
     * newInstance()方法做了哪些事情呢？
     * 1.从pairs中随便找一组(第一组)，然后找这组中第一个对象的classLoader对象
     *   作为Proxy.newProxyInstance()的第1个参数
     *
     * 2.收集整理pairs中所有interface，
     *   作为Proxy.newProxyInstance()的第2个参数；
     *
     * 3.把我们自定义的InvocationHandler(MixinProxy)
     *   作为Proxy.newProxyInstance()的第3个参数
     *
     * 经过这样的处理，我们通过newInstance()创建的代理类，就能够调用所有pairs中的方法
     *
     * @return
     */
    public static Object newInstance(TwoTuple<Object, Class<?>>... pairs){
        Class[] interfaces = new Class[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            interfaces[i] = pairs[i].second;
        }

        ClassLoader cl = pairs[0].first.getClass().getClassLoader();
        return Proxy.newProxyInstance(cl, interfaces, new MixinProxy(pairs));
    }
}


public class DynamicProxyMixin {

    public static void main(String[] args) {

        Object mixin = MixinProxy.newInstance(
                new TwoTuple<>(new BasicImpl(), Basic.class),
                new TwoTuple<>(new SerialNumberedImpl(), SerialNumbered.class),
                new TwoTuple<>(new TimeStampedImpl(), TimeStamped.class)
        );

        Basic b = (Basic)mixin;
        SerialNumbered s = (SerialNumbered)mixin;
        TimeStamped t = (TimeStamped)mixin;

        b.setValue("bName");
        System.out.println(b.getValue());
        System.out.println(s.getSerialNumber());
        System.out.println(t.getStamp());
    }
}
