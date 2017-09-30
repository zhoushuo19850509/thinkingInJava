package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 这个类展示了Button 类的用法。
 * 同时，第一次展示了SwingConsole.run()的用法。
 * SwingConsole.run()封装了在Swing Thread中运行界面程序的方法
 * @author zs
 *
 */
public class Button1 extends JFrame{
	private JButton b1 = new JButton("button1");
	private JButton b2 = new JButton("button2");
	
	public Button1(){
		
		setLayout(new FlowLayout());
		add(b1);
		add(b2);
	}
	
	public static void main(String[] args){
		SwingConsole.run(new Button1(),300 ,400);
	}

}
