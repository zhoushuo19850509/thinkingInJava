package com.nbcb.thinkingInJava.userInterface;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.nbcb.thinkingInJava.util.SwingConsole;



/**
 * �������Ҫ��������JTable���÷�
 * @author zs
 *
 */
public class Table1 extends JFrame{
	
	public Table1(){
		
		// �ȶ����ͷ
		String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
		
		// �ٶ�������
		Object[][] data = {
			    {"Kathy", "Smith",
			     "Snowboarding", new Integer(5), new Boolean(false)},
			    {"John", "Doe",
			     "Rowing", new Integer(3), new Boolean(true)},
			    {"Sue", "Black",
			     "Knitting", new Integer(2), new Boolean(false)},
			    {"Jane", "White",
			     "Speed reading", new Integer(20), new Boolean(true)},
			    {"Joe", "Brown",
			     "Pool", new Integer(10), new Boolean(false)},
			     {"Kathy", "Smith",
				     "Snowboarding", new Integer(5), new Boolean(false)},
				    {"John", "Doe",
				     "Rowing", new Integer(3), new Boolean(true)},
				    {"Sue", "Black",
				     "Knitting", new Integer(2), new Boolean(false)},
				    {"Jane", "White",
				     "Speed reading", new Integer(20), new Boolean(true)},
				    {"Joe", "Brown",
				     "Pool", new Integer(10), new Boolean(false)},
				     {"Kathy", "Smith",
					     "Snowboarding", new Integer(5), new Boolean(false)},
					    {"John", "Doe",
					     "Rowing", new Integer(3), new Boolean(true)},
					    {"Sue", "Black",
					     "Knitting", new Integer(2), new Boolean(false)},
					    {"Jane", "White",
					     "Speed reading", new Integer(20), new Boolean(true)},
					    {"Joe", "Brown",
					     "Pool", new Integer(10), new Boolean(false)},
					     {"Kathy", "Smith",
						     "Snowboarding", new Integer(5), new Boolean(false)},
						    {"John", "Doe",
						     "Rowing", new Integer(3), new Boolean(true)},
						    {"Sue", "Black",
						     "Knitting", new Integer(2), new Boolean(false)},
						    {"Jane", "White",
						     "Speed reading", new Integer(20), new Boolean(true)},
						    {"Joe", "Brown",
						     "Pool", new Integer(10), new Boolean(false)}   ,
						     {"Kathy", "Smith",
							     "Snowboarding", new Integer(5), new Boolean(false)},
							    {"John", "Doe",
							     "Rowing", new Integer(3), new Boolean(true)},
							    {"Sue", "Black",
							     "Knitting", new Integer(2), new Boolean(false)},
							    {"Jane", "White",
							     "Speed reading", new Integer(20), new Boolean(true)},
							    {"Joe", "Brown",
							     "Pool", new Integer(10), new Boolean(false)},
							     {"Kathy", "Smith",
								     "Snowboarding", new Integer(5), new Boolean(false)},
								    {"John", "Doe",
								     "Rowing", new Integer(3), new Boolean(true)},
								    {"Sue", "Black",
								     "Knitting", new Integer(2), new Boolean(false)},
								    {"Jane", "White",
								     "Speed reading", new Integer(20), new Boolean(true)},
								    {"Joe", "Brown",
								     "Pool", new Integer(10), new Boolean(false)},
								     {"Kathy", "Smith",
									     "Snowboarding", new Integer(5), new Boolean(false)},
									    {"John", "Doe",
									     "Rowing", new Integer(3), new Boolean(true)},
									    {"Sue", "Black",
									     "Knitting", new Integer(2), new Boolean(false)},
									    {"Jane", "White",
									     "Speed reading", new Integer(20), new Boolean(true)},
									    {"Joe", "Brown",
									     "Pool", new Integer(10), new Boolean(false)},
									     {"Kathy", "Smith",
										     "Snowboarding", new Integer(5), new Boolean(false)},
										    {"John", "Doe",
										     "Rowing", new Integer(3), new Boolean(true)},
										    {"Sue", "Black",
										     "Knitting", new Integer(2), new Boolean(false)},
										    {"Jane", "White",
										     "Speed reading", new Integer(20), new Boolean(true)},
										    {"Joe", "Brown",
										     "Pool", new Integer(10), new Boolean(false)}   	     
			};
		
		JTable table = new JTable(data, columnNames);
		add(new JScrollPane(table));
		
	}
	
	
	public static void main(String[] args){
		SwingConsole.run(new Table1(),300 ,400);
	}

}
