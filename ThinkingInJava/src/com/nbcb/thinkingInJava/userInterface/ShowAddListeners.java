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
 * ������һ��չʾ�˼�����ActionListener���÷�
 * �����Ϸ���һ��JTextField�������������javax.swing�������
 * �����·���һ��JTextArea(Ƕ��JScrollPane�����)���������չʾswing��ķ���(��Ȼ�Ǿ������˵�)
 * ���Ƕ����ActionListener�ࣺNameListener��������Ҫ�Ǽ���JTextField����Ķ�����
 * һ��JTextField����б仯���Ͱ�JTextField����е����ݽ��й��ˣ�չʾ��JTextArea��
 * @author zs
 *
 */
public class ShowAddListeners extends JFrame{
	
	
	// ����һЩ��ʵԪ��
	JTextField name = new JTextField(25);   // ����Swing������
	JTextArea results = new JTextArea(40,65);   // ��ʵ���˺��Swing��ķ���
	
	// 
	private static Pattern addListener = Pattern.compile("(add\\w+?Listener\\(.*?\\))");
	
	// ����һ�������࣬����JTextField name�ı仯
	// �������ʲô�����أ�
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
		// ʵ����ActionListener
		NameListener nameListener = new NameListener();
		name.addActionListener(nameListener);
		
		// �������
		JPanel top = new JPanel();
		top.add(new JLabel("Swing class name(Press Enter):"));
		top.add(name);	
		
		add(BorderLayout.NORTH,top);
		add(new JScrollPane(results));
		
		// ��ʼ��
		name.setText("JTextArea");
		nameListener.actionPerformed(
			new ActionEvent("",0,"")	);   // ���ﴥ��һ��actionListener
	
	}
		
	public static void main(String[] args){
		SwingConsole.run(new ShowAddListeners(),500	 ,400);
	}

}
