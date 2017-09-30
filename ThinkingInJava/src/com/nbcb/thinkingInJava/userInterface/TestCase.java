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
 * �������Ҫ��Ϊ������Ӱ���̨������º����֤����
 * 
 * Step1 ͨ�����䣬��ȡtestcase ���package�µ�����TestCase�࣬�ŵ�һ��List������
 * Step2 ��������������һ��Label��JCheckbox�б�ÿ��JCheckbox�����һ��JLabel
 * Step3 һ��forѭ���������е�TestCase��
 * Step4 �����ǰJCheckbox��ѡ�У��������гɹ����ͽ���ǰJCheckbox��Ӧ��label����Ϊ�ɹ���ͼ��
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
