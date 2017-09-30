package com.nbcb.thinkingInJava.userInterface;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.nbcb.thinkingInJava.util.SwingConsole;


/**
 * 这个类主要是介绍了如何监听Button的监听事件
 * 实现方式也非常直接，定义一个Button的监听类。这个类要集成ActionListener接口
 * 然后Button对象只要添加这个监听类就行了
 * @author zs
 *
 */

public class Button2 extends JFrame{
	
	private JButton b1 = new JButton("button1");
	private JButton b2 = new JButton("button2");
	private JTextField text = new JTextField(10);
	
	// 定义Button类的监听类
	class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = ((JButton)e.getSource()).getText(); // 这个主要是获取JButton实例对象的名称
			text.setText(name);
//			System.out.println("button name: " + name);
		}
		
	}
	
	ButtonListener bl = new ButtonListener(); // 实例化ButtonListener对象
	public Button2(){
		b1.addActionListener(bl);
		b2.addActionListener(bl);
		setLayout(new FlowLayout());
		add(b1);
		add(b2);
		add(text);
	}
	
	public static void main(String[] args){
		SwingConsole.run(new Button2(),300 ,400);
	}

}
