package com.nbcb.thinkingInJava.userInterface;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.nbcb.thinkingInJava.util.SwingConsole;


/**
 * �������Ҫ�ǽ�������μ���Button�ļ����¼�
 * ʵ�ַ�ʽҲ�ǳ�ֱ�ӣ�����һ��Button�ļ����ࡣ�����Ҫ����ActionListener�ӿ�
 * Ȼ��Button����ֻҪ�����������������
 * @author zs
 *
 */

public class Button2 extends JFrame{
	
	private JButton b1 = new JButton("button1");
	private JButton b2 = new JButton("button2");
	private JTextField text = new JTextField(10);
	
	// ����Button��ļ�����
	class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = ((JButton)e.getSource()).getText(); // �����Ҫ�ǻ�ȡJButtonʵ�����������
			text.setText(name);
//			System.out.println("button name: " + name);
		}
		
	}
	
	ButtonListener bl = new ButtonListener(); // ʵ����ButtonListener����
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
