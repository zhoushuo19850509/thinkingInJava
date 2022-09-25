package com.nbcb.thinkingInJava.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * 这个代码主要用于说明如何通过File.list()方法,
 * 列出某个目录下的所有文件/目录
 *
 * 值得一提的是，File.list()支持FilenameFilter参数
 * 这个参数能够通过正则表的方式，对filename进行正则过滤
 * 功能还是非常强大的。
 */
public class DirList {
	
	public static void main(String[] args){
		File path = new File("src" +File.separator+
				"com"+ File.separator+"nbcb" + File.separator +
				"thinkingInJava" + File.separator +
				"userInterface");  // 工程当前目录
		
		String[] list ; // 保存文件名列表
		
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
