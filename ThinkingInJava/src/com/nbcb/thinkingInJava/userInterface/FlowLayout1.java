package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.nbcb.thinkingInJava.util.SwingConsole;

public class FlowLayout1 extends JFrame{
	
    public FlowLayout1(){
		
		setLayout(new FlowLayout());
		for(int i = 0 ;i < 20; i++){
			add(new JButton("JButon" + 1));
		}
	}
	
	public static void main(String[] args){
		SwingConsole.run(new FlowLayout1(),300 ,300);
	}

}
