package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.nbcb.thinkingInJava.util.SwingConsole;

public class Faces extends JFrame{
	
	private JButton b1 = new JButton("button1",new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
	private JButton b2 = new JButton("button2");
	private JLabel lb = new JLabel("myLabel");
	
	public Faces(){
		
		setLayout(new FlowLayout());
		add(b1);
		
		
		
		b2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				b2.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
			}
			
		});
		
		add(b2);
		
//		lb.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
		lb.setIcon(new ImageIcon("resource\\succ.png"));
		add(lb);
	}
	
	public static void main(String[] args){
		SwingConsole.run(new Faces(),300 ,400);
	}
	

}
