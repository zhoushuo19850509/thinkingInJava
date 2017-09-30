package com.nbcb.thinkingInJava.typeInformation;

/**
 * ����ļ���Ҫ��������Class.forName()
 * �������Ҹо���
 * ��Ҫע����ǣ�Class.forName()�е�������Ҫ��ľ���·����Ҳ������Ҫ����package·����ѧ���У�CanonicalName
 * ���ƣ� com.nbcb.thinkingInJava.typeInfomation.Gum
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
		
		// ���ݲ�����������ֵ����ʼ����Ӧ����
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

