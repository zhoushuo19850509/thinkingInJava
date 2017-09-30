package com.nbcb.thinkingInJava.typeInformation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 这个文件主要是为了验证Java官方的Proxy的用法：
 * java.lang.reflect.Proxy
 * 
 * 我们只需要定义一个DynamicProxyHandler就行了。
 * 
 * 说得直白点，就是Proxy把实际的执行类的各个方法，重定向到了我们定义的Handler类中去了
 * 由Handler类中的invoke()触发实际类的各个方法，当然，我们可以在invoke()中添加我们个性化的动作
 * 
 * @author 080776
 *
 */


/**
 * Proxy Handler
 */
class DynamicProxyHandler implements InvocationHandler{

	private Object proxied;
	
	public DynamicProxyHandler(Object proxied){
		this.proxied = proxied;
	}
	
	// 需要实现InvocationHandler接口的方法
	// 这个invoke方法负责循环调用实际对象RealObject中的方法
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// 打印invoke方法的各个参数
		System.out.println("*** proxy: " + proxy.getClass().getSimpleName()
				   + " method : " + method + " args ：" + args);
		
		// 打印参数
		if(args != null){
			for(Object arg: args){
				System.out.println( "  " + arg );
			}
		}		
		return method.invoke(proxied, args);
	}

}


public class SimpleDynamicProxy{
	public static void consumer(Interface iface){
		iface.doSomething();
//		System.out.println("---------------------");
		iface.somethingElse("bonobo");
	}
	
	public static void main(String[] args){
		RealObject real = new RealObject();
		
		consumer(real);
		
		System.out.println("****** Start using Java Proxy");
		Interface proxy = (Interface) Proxy.newProxyInstance(
				  Interface.class.getClassLoader(), 
				  new Class[]{Interface.class}, 
				  new DynamicProxyHandler(real));
		
		consumer(proxy);
	}
	
}
