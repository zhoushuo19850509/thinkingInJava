package com.nbcb.thinkingInJava.generics.simple;


class Automobile{
	public void saySth(){
		System.out.println("I'm Automobile...");
	}

	@Override
	public String toString() {
		return "Automobile{}";
	}
}

/**
 * Holder1
 * 没有采用泛型，那就只能在编程的时候指定具体的类名
 * 不太灵活
 */
public class Holder1 {
	private Automobile a;
	
	public Holder1(Automobile a){
		this.a = a;
	}
	
	Automobile get(){
		return this.a;
	}


	public static void main(String[] args) {
		Holder1 holder1 = new Holder1(new Automobile());

		Automobile automobile = holder1.get();
		automobile.saySth();


	}

}
