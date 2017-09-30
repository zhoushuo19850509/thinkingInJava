package com.nbcb.thinkingInJava.userInterface;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


/**
 * �����������Ӽ���
 * �������漰����(JFrame)�Ĳ������ŵ�Swingר�����߳���ȥ����
 * @author zs
 *
 */
public class SubmitSwingProgram extends JFrame{
	
	JLabel label;
	public SubmitSwingProgram(){
		super("Hello Hello Swing");
		label = new JLabel("A Label");
		add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
	}
	
	static SubmitSwingProgram ssp;
	public static void main(String[] args) throws InterruptedException{
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				ssp = new SubmitSwingProgram();
			}
		});
		
        TimeUnit.SECONDS.sleep(1);
		
		
		// invoke a new thread which is handled by Swing
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				ssp.label.setText("Text changed");
			}
		});
	}

}
