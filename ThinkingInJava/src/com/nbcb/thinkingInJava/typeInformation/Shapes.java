package com.nbcb.thinkingInJava.typeInformation;

import java.util.Arrays;
import java.util.List;


abstract class Shape{
	public void draw(){
		System.out.println(this + ".draw()");
	}
	abstract public String toString();
	
	// 这个方法会判断当前instance是否为Circle，如果是Circle的话，就不执行rotate动作
	public void rotate(){
		if(this.getClass().getSimpleName().equals("Circle")){
			System.out.println("do nothing");
		}else{
			System.out.println("rotate");
		}
	}
	
	// highlight flag 
	private boolean isHighLighted = false;
	public void setHighLight(boolean isHighLighted){
		this.isHighLighted = isHighLighted;
	}
	
	public String isHighLighted(){
		if(this.isHighLighted){
			return " hight lighted!";
		}else{
			return "";
		}
		
	}
}

class Square extends Shape{
	public String toString(){
		return "Square" + super.isHighLighted();
	}
}

class Circle extends Shape{
	public String toString(){
		return "Circle";
	}
}

class Triangle extends Shape{
	public String toString(){
		return "Triangle";
	}
}

class Rhomboid extends Shape{
	public String toString(){
		return "Rhomboid" + super.isHighLighted();
	}
}

public class Shapes {
	public static void main(String[] args){
		List<Shape> shapelist = Arrays.asList(new Square(),new Circle(),new Triangle() );
		for(Shape s : shapelist){
			s.draw();
			s.rotate();
		}
		
		Rhomboid rr = new Rhomboid();
		rr.setHighLight(true);
		rr.draw();
		if(rr instanceof Rhomboid){
			System.out.println("confirmed");
		}
		Shape ss = rr;
		
		
//		Rhomboid rr = (Rhomboid)ss;
//		Circle cc = (Circle)rr;
//		rr.draw();
	}
	
}
