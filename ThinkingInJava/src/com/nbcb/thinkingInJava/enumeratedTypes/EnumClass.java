package com.nbcb.thinkingInJava.enumeratedTypes;


enum Shrubbery{ GROUND, CRAWING, HANGING }

public class EnumClass {
	public static void main(String[] args){
		for(Shrubbery s : Shrubbery.values()){
			System.out.println(s + " ordinal  " + s.ordinal() );  // 打印位置
			System.out.println(s.compareTo(Shrubbery.CRAWING));  // 比较Enum中的位置
			System.out.println(s.equals(Shrubbery.CRAWING));
			System.out.println(s == Shrubbery.CRAWING);
			System.out.println(s.getDeclaringClass());
			System.out.println(s.name());
			System.out.println("============================");
		}
	}
}
