package com.nbcb.thinkingInJava.userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.nbcb.thinkingInJava.util.SwingConsole;


/**
 * 这个类进一步展示了监听类ActionListener的用法
 * 界面上方有一个JTextField组件，用于输入javax.swing类的名字
 * 界面下方有一个JTextArea(嵌在JScrollPane组件中)组件，用于展示swing类的方法(当然是经过过滤的)
 * 我们定义的ActionListener类：NameListener的作用主要是监听JTextField组件的动作，
 * 一旦JTextField组件有变化，就吧JTextField组件中的内容进行过滤，展示到JTextArea中
 * @author zs
 *
 */
public class ShowAddListeners extends JFrame{
	
	
	// 定义一些现实元素
	JTextField name = new JTextField(25);   // 输入Swing类名称
	JTextArea results = new JTextArea(40,65);   // 现实过滤后的Swing类的方法
	
	// 
	private static Pattern addListener = Pattern.compile("(add\\w+?Listener\\(.*?\\))");
	
	// 定义一个监听类，监听JTextField name的变化
	// 这个类做什么事情呢？
	class NameListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String nameText = name.getText();
			
			if( nameText.length() == 0){
				results.setText("No match!");
				return;
			}
			
			Class<?> kind;
			
			try {
				kind = Class.forName("javax.swing." + nameText);
				
				Method[] methods = kind.getMethods();
				for(Method m: methods){
					
					Matcher matcher = addListener.matcher(m.toString());
					if(matcher.find()){
						results.append(m.toString() + "\n");
					}					
					
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				results.setText("No match!");
				return;
			}
			
			
		}		
	}
	
	
	
	public ShowAddListeners(){
		// 实例化ActionListener
		NameListener nameListener = new NameListener();
		name.addActionListener(nameListener);
		
		// 界面描绘
		JPanel top = new JPanel();
		top.add(new JLabel("Swing class name(Press Enter):"));
		top.add(name);	
		
		add(BorderLayout.NORTH,top);
		add(new JScrollPane(results));
		
		// 初始化
		name.setText("JTextArea");
		nameListener.actionPerformed(
			new ActionEvent("",0,"")	);   // 这里触发一下actionListener
	
	}
		
	public static void main(String[] args){
		SwingConsole.run(new ShowAddListeners(),500	 ,400);
	}

}
