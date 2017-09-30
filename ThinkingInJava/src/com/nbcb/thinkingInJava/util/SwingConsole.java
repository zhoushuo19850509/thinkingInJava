package com.nbcb.thinkingInJava.util;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.nbcb.thinkingInJava.userInterface.SubmitSwingProgram;

/**
 * 这个util类主要是用于Swing界面展示
 * 用法很直接，一个类主要继承JFrame类就行，比如：
 * public class Button1 extends JFrame{ ... }
 * 
 * 就能直接利用这个util在Swing thread中运行啦：
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
