package com.nbcb.thinkingInJava.util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nbcb.thinkingInJava.userInterface.SubmitSwingProgram;

/**
 * ���util����Ҫ������Swing����չʾ
 * �÷���ֱ�ӣ�һ������Ҫ�̳�JFrame����У����磺
 * public class Button1 extends JFrame{ ... }
 * 
 * ����ֱ���������util��Swing thread����������
 * 
 * run(new Button1(),300,200);
 * @author zs
 *
 */
public class SwingConsole {
	
	public static void run(final JFrame frame, final int width, final int height ){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				frame.setTitle(frame.getClass().getSimpleName());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(width,height);
				frame.setVisible(true);
			}
		});
	}
	

}
