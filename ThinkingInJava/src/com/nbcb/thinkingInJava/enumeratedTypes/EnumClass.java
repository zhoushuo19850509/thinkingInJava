package com.nbcb.thinkingInJava.enumeratedTypes;


enum Shrubbery{ GROUND, CRAWING, HANGING }

public class EnumClass {
	public static void main(String[] args){
		for(Shrubbery s : Shrubbery.values()){
			System.out.println(s + " ordinal  " + s.ordinal() );  // ��ӡλ��
			System.out.println(s.compareTo(Shrubbery.CRAWING));  // �Ƚ�Enum�е�λ��
			System.out.println(s.equals(Shrubbery.CRAWING));
			System.out.println(s == Shrubbery.CRAWING);
			System.out.println(s.getDeclaringClass());
			System.out.println(s.name());
			System.out.println("============================");
		}
	}
}
