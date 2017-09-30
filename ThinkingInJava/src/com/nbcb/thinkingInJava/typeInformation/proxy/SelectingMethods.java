package com.nbcb.thinkingInJava.typeInformation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ?????????????????????Proxy Handler???§µ??????§Ö????????????????
 * ????????????? 
 * if(method.getName().equals("XXX")) then ...
 * @author 080776
 */


/**
 * Proxy handler
 *
 */
class MethodSelector implements InvocationHandler{

	private Object proxied;
	
	public MethodSelector(Object proxied){
		this.proxied = proxied;
	}
	
	// ??????InvocationHandler???????
	// ???invoke?????????????????????RealObject?§Ö????
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		if(method.getName().equals("interesting")){
			System.out.println("proxy detected the interesting method");
		}
		return method.invoke(proxied, args);
	}

}

/**
 * Interface
 * @author 080776
 *
 */
interface SomeMethods{
	void boring1();
	void boring2();
	void interesting(String arg);
	void boring3();
}

/**
 * real Class
 * @author 080776
 *
 */
class Implementation implements SomeMethods{

	public void boring1() {
		// TODO Auto-generated method stub
		System.out.println("boring1");
	}

	public void boring2() {
		// TODO Auto-generated method stub
		System.out.println("boring2");
	}

	public void boring3() {
		// TODO Auto-generated method stub
		System.out.println("boring3");
	}

	public void interesting(String arg) {
		// TODO Auto-generated method stub
		System.out.println("interesting: " + arg);
	}
	
}


public class SelectingMethods {
	public static void main(String[] args){
		SomeMethods proxy = (SomeMethods) Proxy.newProxyInstance(
				SomeMethods.class.getClassLoader(), 
				  new Class[]{SomeMethods.class}, 
				  new MethodSelector(new Implementation()));
		proxy.boring1();
		proxy.boring2();
		proxy.boring3();
		proxy.interesting("hello");
	}

}
