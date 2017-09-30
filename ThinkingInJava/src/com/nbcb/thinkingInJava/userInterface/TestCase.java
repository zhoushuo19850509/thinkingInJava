package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nbcb.thinkingInJava.util.SwingConsole;

/**
 * 这个类主要是为了试验影像后台服务更新后的验证工作
 * 
 * Step1 通过反射，获取testcase 这个package下的所有TestCase类，放到一个List对象中
 * Step2 根据类名，创建一个Label的JCheckbox列表，每个JCheckbox后面跟一个JLabel
 * Step3 一个for循环运行所有的TestCase类
 * Step4 如果当前JCheckbox被选中，并且运行成功，就将当前JCheckbox对应的label设置为成功的图标
 * 
 * @author zs
 *
 */
public class TestCase extends JFrame{
	
	private JTextArea t = new JTextArea(6,15);
	private JLabel lb1 = new JLabel("");
	private JLabel lb2 = new JLabel("");
	private JLabel lb3 = new JLabel("");
	
	private JCheckBox cb1 = new JCheckBox("GetRoute");
	private JCheckBox cb2 = new JCheckBox("Docreference");
	private JCheckBox cb3 = new JCheckBox("RecordImageNoFile");

	public TestCase(){
		
		lb1.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
		lb2.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
		lb3.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
		
		setLayout(new GridLayout(6,1));
		add(cb1);
		add(lb1);
		add(cb2);
		add(lb2);
		add(cb3);
		add(lb3);
		add(new JScrollPane(t));
		
	}
	
	public static void main(String[] args){
		SwingConsole.run(new TestCase(),300 ,400);
	}

}
