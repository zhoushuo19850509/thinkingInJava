package com.nbcb.thinkingInJava.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这个类是Arrays这章的开篇，主要演示了array和list的一些不同。
 * @author zs
 *
 */
class BerylliumSphere{
	private static long counter;
	private final long id = counter++;
	public String toString(){
		return " Sphere " + id ;
	}
}

public class ContianerComparision {
	
	public static void main(String[] args){
		// Array containing objects
		BerylliumSphere[] sphere = new BerylliumSphere[10];
//		System.out.println(sphere);
		
		for(int i = 0 ; i < 5; i++){
			sphere[i] = new BerylliumSphere();
		}
		System.out.println(Arrays.toString(sphere));
		System.out.println(sphere[4]);
		
		// list containing objects
		List<BerylliumSphere> sphereList = new ArrayList<BerylliumSphere>();
		for(int i = 0 ; i < 5 ; i++){
			sphereList.add(new BerylliumSphere());
		}
		System.out.println(sphereList);
		System.out.println(sphereList.get(4));
		
		
		int[] integers = {0,1,2,3,4,5};
		System.out.println(Arrays.toString(integers));
		System.out.println(integers[4]);
		
		List<Integer> intList = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5));
		intList.add(99);
		System.out.println(intList);
		System.out.println(intList.get(4));
		
	}

}
