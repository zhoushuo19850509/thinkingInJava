package com.nbcb.thinkingInJava.strings;

public class Immutable {
	
	public static String Upper(String s){
		return s.toUpperCase();
	}
	
	public static void main(String[] args){
		String a = "asbsdUiifef";
		System.out.println("a: " + a);
		String aa = Upper(a);
		System.out.println("aa: " + aa);
	}

}
