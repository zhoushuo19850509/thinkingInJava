package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * �����չʾ��Button ����÷���
 * ͬʱ����һ��չʾ��SwingConsole.run()���÷���
 * SwingConsole.run()��װ����Swing Thread�����н������ķ���
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
