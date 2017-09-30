package com.nbcb.thinkingInJava.userInterface;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.nbcb.thinkingInJava.util.SwingConsole;

public class BoaderLayer extends JFrame{
	
	public BoaderLayer(){
		add(BorderLayout.NORTH,new JButton("north"));
		add(BorderLayout.SOUTH,new JButton("south"));
		add(BorderLayout.EAST,new JButton("east"));
		add(BorderLayout.WEST,new JButton("west"));
	}
	
	public static void main(String[] args){
		SwingConsole.run(new BoaderLayer(),500 ,500);
	}

}
