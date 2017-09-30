package com.nbcb.thinkingInJava.userInterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nbcb.thinkingInJava.util.SwingConsole;

public class CheckBoxes extends JFrame{
	
	private JTextArea t = new JTextArea(6,15);
	private JLabel lb = new JLabel("");
	
	private JCheckBox cb1 = new JCheckBox("check box 1");
	private JCheckBox cb2 = new JCheckBox("check box 2");
	private JCheckBox cb3 = new JCheckBox("check box 3");

	public CheckBoxes(){
		
		cb1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				trace("1",cb1);
			}
			
		});
		
		cb2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				trace("2",cb2);
			}
			
		});
		
		cb3.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				trace("3",cb3);
			}
			
		});
		
		
		
		setLayout(new FlowLayout());
		add(cb1);
		add(lb);
		add(cb2);
		add(cb3);
		add(new JScrollPane(t));
		
	}
	
	private void trace(String info, JCheckBox cb){
		if( cb.isSelected() ){
			t.append("Box " + info + " selected! \n" );
			lb.setIcon(new ImageIcon("C:\\Users\\zs\\MyMenu.png"));
		}else{
			t.append("Box " + info + " cleared! \n" );
		}
	}
	
	public static void main(String[] args){
		SwingConsole.run(new CheckBoxes(),300 ,400);
	}

}
