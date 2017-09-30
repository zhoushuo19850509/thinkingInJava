package com.nbcb.thinkingInJava.typeInformation;

/**
 * 这个文件主要是引入了Class.forName()
 * 给你找找感觉；
 * 需要注意的是，Class.forName()中的内容需要类的绝对路径，也就是需要带上package路径，学名叫：CanonicalName
 * 类似： com.nbcb.thinkingInJava.typeInfomation.Gum
 * @author 080776
 *
 */
class Candy{
	static {System.out.println("Loading Candy");}
}

class Gum{
	static {System.out.println("Loading Gum");}
}


class Cookie{
	static {System.out.println("Loading Cookie");}
}


public class SweetShop {
	
	public static void main(String[] args){
//		new Candy();
//		try {
//			Class.forName("com.nbcb.thinkingInJava.typeInfomation.Gum");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		new Cookie();
//		
//		Candy candy = new Candy();
//		String simpleName = candy.getClass().getSimpleName();
		
		// 根据参数传进来的值，初始化对应的类
		String className = args[0];
		if(className != null && className != ""){
			try {
				Class c = Class.forName("com.nbcb.thinkingInJava.typeInfomation." + className);
				c.newInstance();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Please input the right class name!");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}

