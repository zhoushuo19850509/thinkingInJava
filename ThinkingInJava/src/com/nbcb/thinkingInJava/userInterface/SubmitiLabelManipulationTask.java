package com.nbcb.thinkingInJava.userInterface;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * ������ӳ��Խ�Label��չʾ�ŵ�Swingר�����߳���
 * @author zs
 *
 */
public class SubmitiLabelManipulationTask {
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Hello swing");
		final JLabel label = new JLabel("A Label");
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,500);
		frame.setVisible(true);
		
		TimeUnit.SECONDS.sleep(1);
		
		
		// invoke a new thread which is handled by Swing
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				label.setText("a little different");
			}
		});
	}

}
