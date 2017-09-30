package com.nbcb.thinkingInJava.holdobject;

import java.util.ArrayList;
import java.util.List;

public class AppleAndOrange {
	public static void main(String[] args){
		List<Apple> apples = new ArrayList<Apple>();
		for(int i = 0 ; i < 5 ; i++){
			Apple apple = new Apple();
			apples.add(apple);
		}
		
		/*
		for(int k = 0 ; k < 3 ; k++){
			Orange orange = new Orange();
			apples.add(orange);
		}
		*/
		
		for( int j = 0 ; j < apples.size() ; j++){
			Apple currentApple = (Apple)apples.get(j);
			System.out.println(currentApple.toString());
		}
		
	}

}

class Apple{
	private static long counter;
	private final long id = counter++;  // 这个id记录了Waveform对象实例化的数量 ，Waveform对象 每实例化一次，计数+1
	public long id(){
		return id;
	}
	public String toString(){
		return "Apple " + id;
	}
}

class Orange{
	
}