package com.nbcb.thinkingInJava.typeInformation.proxy;

/**
 * 一个比较简单的Proxy的例子
 * 简单来说，Proxy就是在原有类方法的基础上，添加一些实现内容
 * 
 * 本例中
 * 1.接口是Interface类
 * 2.RealObject是一个真实的类
 * 3.SimpleProxy是我们的Proxy代理类
 * @author 080776
 *
 */

// 接口 无论是实际的类，还是Proxy代理类，都需要实现这个接口
interface Interface{
	void doSomething();
	void somethingElse(String arg);
}


class RealObject implements Interface{

	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("do something");
	}

	public void somethingElse(String arg) {
		// TODO Auto-generated method stub
		System.out.println("some thing else: " + arg);
	}
	
}

/**
 * 代理类
 * 从代理类的结构就能看出来，
 * 我们把实际的类作为constructor的参数传进来。
 * 然后在实现Interface接口的时候，在原有实际类的代码的基础上，添加了一些新的动作
 * @author 080776
 *
 */
class SimpleProxy implements Interface{

	private Interface proxied;
	
	// constructor
	public SimpleProxy(Interface proxied){
		this.proxied = proxied;
	}
	
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("Simple proxy doing some thing");
		proxied.doSomething();
	}

	public void somethingElse(String arg) {
		// TODO Auto-generated method stub
		System.out.println("Simple proxy doing some thing else: " + arg);
		proxied.somethingElse(arg);
	}
	
}


public class SimpleProxyDemo {
	public static void consumer(Interface iface){
		iface.doSomething();
		iface.somethingElse("bonobo");
	}
	
	public static void main(String[] args){
		consumer(new RealObject());
		consumer(new SimpleProxy(new RealObject()));
	}
	
}
