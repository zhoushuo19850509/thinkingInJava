package com.nbcb.thinkingInJava.typeInformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 这个例子，主要是演示如何通过反射，获取Class类的methods and constructors
 * @author 080776
 *
 */
public class ShowMethods {
	private static Pattern p = Pattern.compile("\\2+\\.");
	
	public static void main(String[] args){
		if(args.length < 1){
			System.out.println("command param error!");
			System.exit(0);
		}
		
		String className = args[0];
		try {
			Class<?> c = Class.forName(className);
			Method[] methods = c.getMethods();
			Constructor[] ctros = c.getConstructors();
			
			if(args.length == 1){
				System.out.println("start print methods: ");
				for(Method method: methods){
					System.out.println(p.matcher(method.toString()).replaceAll(""));
//					System.out.println(method.toString());
				}
				System.out.println("start print Constructors: ");
				for(Constructor ctro: ctros){
					System.out.println(p.matcher(ctro.toString()).replaceAll(""));
				}
				
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
