package com.nbcb.thinkingInJava.typeInformation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ����ļ���Ҫ��Ϊ����֤Java�ٷ���Proxy���÷���
 * java.lang.reflect.Proxy
 * 
 * ����ֻ��Ҫ����һ��DynamicProxyHandler�����ˡ�
 * 
 * ˵��ֱ�׵㣬����Proxy��ʵ�ʵ�ִ����ĸ����������ض��������Ƕ����Handler����ȥ��
 * ��Handler���е�invoke()����ʵ����ĸ�����������Ȼ�����ǿ�����invoke()��������Ǹ��Ի��Ķ���
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
	
	// ��Ҫʵ��InvocationHandler�ӿڵķ���
	// ���invoke��������ѭ������ʵ�ʶ���RealObject�еķ���
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		// ��ӡinvoke�����ĸ�������
		System.out.println("*** proxy: " + proxy.getClass().getSimpleName()
				   + " method : " + method + " args ��" + args);
		
		// ��ӡ����
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
