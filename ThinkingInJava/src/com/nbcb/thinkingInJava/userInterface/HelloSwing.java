package com.nbcb.thinkingInJava.userInterface;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloSwing {
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Hello swing");
		JLabel label = new JLabel("A Label");
		frame.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,500);
		frame.setVisible(true);
		
		TimeUnit.SECONDS.sleep(1);
		label.setText("a little different");
		
		JLabel labelServiceDocReference = new JLabel("DocReference:");
		frame.add(labelServiceDocReference);
		TimeUnit.SECONDS.sleep(1);
		labelServiceDocReference.setText("DocReference: succ");
	}

}
