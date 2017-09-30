package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.nbcb.thinkingInJava.util.SwingConsole;

/**
 * ���������Ҫ��Ϊ��˵��JTextArea��ʹ�÷�����
 * 1.��֮ǰButton2�����Ӳ�ͬ��JTextArea�ܹ�֧�ֶ��У�JTextField֧�ֵ���
 * 2.���ﰴť�����¼���ʹ��Ҳ��һ�����������ֱ��ʹ��ActionListener�࣬����ֱ����inner class�ķ�ʽ
 * 3.��ΪJTextArea�ܹ�֧�ֶ��У����԰�JTextArea����ŵ�JScrollPane�����У���֧�ֶ���
 * @author zs
 *
 */
public class TextArea extends JFrame{
	
	
	private JButton b1 = new JButton("add");
	private JButton b2 = new JButton("clear");
	private JTextField text = new JTextField(10);
		
	JTextArea textArea = new JTextArea(20,40);
	
	public TextArea(){		
		
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
				textArea.append("hello \n");
				textArea.append("mytextArea \n");
				textArea.append("my little angle \n");
				textArea.append("hello1 \n");
				textArea.append("mytextArea2 \n");
				textArea.append("my little angle3 \n");
			}
		});
		
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		
		setLayout(new FlowLayout());
		
		add(new JScrollPane(textArea));
		add(b1);
		add(b2);
	}
	
	public static void main(String[] args){
		SwingConsole.run(new TextArea(),500 ,500);
	}
	
	

}
