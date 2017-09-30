package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.nbcb.thinkingInJava.util.SwingConsole;

public class GridLayout1 extends JFrame{
    public GridLayout1(){
		
		setLayout(new GridLayout(7,3));
		for(int i = 0 ;i < 20; i++){
			add(new JButton("JButon" + 1));
		}
	}
	
	public static void main(String[] args){
		SwingConsole.run(new GridLayout1(),300 ,300);
	}


}
