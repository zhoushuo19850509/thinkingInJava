package com.nbcb.thinkingInJava.typeInformation.proxy;

/**
 * һ���Ƚϼ򵥵�Proxy������
 * ����˵��Proxy������ԭ���෽���Ļ����ϣ����һЩʵ������
 * 
 * ������
 * 1.�ӿ���Interface��
 * 2.RealObject��һ����ʵ����
 * 3.SimpleProxy�����ǵ�Proxy������
 * @author 080776
 *
 */

// �ӿ� ������ʵ�ʵ��࣬����Proxy�����࣬����Ҫʵ������ӿ�
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
 * ������
 * �Ӵ�����Ľṹ���ܿ�������
 * ���ǰ�ʵ�ʵ�����Ϊconstructor�Ĳ�����������
 * Ȼ����ʵ��Interface�ӿڵ�ʱ����ԭ��ʵ����Ĵ���Ļ����ϣ������һЩ�µĶ���
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
