package com.nbcb.thinkingInJava.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * ���������Ҫ����˵�����ͨ��File.list()����,
 * �г�ĳ��Ŀ¼�µ������ļ�/Ŀ¼
 *
 * ֵ��һ����ǣ�File.list()֧��FilenameFilter����
 * ��������ܹ�ͨ��������ķ�ʽ����filename�����������
 * ���ܻ��Ƿǳ�ǿ��ġ�
 */
public class DirList {
	
	public static void main(String[] args){
		File path = new File("src" +File.separator+
				"com"+ File.separator+"nbcb" + File.separator +
				"thinkingInJava" + File.separator +
				"userInterface");  // ���̵�ǰĿ¼
		
		String[] list ; // �����ļ����б�
		
		System.out.println(path.getAbsolutePath());
		String reg = ".*.java";

		list = path.list(new DirFilter(reg));

		Arrays.sort(list,String.CASE_INSENSITIVE_ORDER); // sort the array
		
		for(String fileName : list){
			System.out.println(fileName);
		}
		
	}

}

/**
 * name filter by regular expressions
 * @author zs
 *
 */
class DirFilter implements FilenameFilter{

	private Pattern pattern;
	
	public DirFilter(String regex){
		pattern = Pattern.compile(regex);
	}
	
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return pattern.matcher(name).matches();
	}
	
}