package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageBoxTest extends JFrame {

    private JButton[] b = {
            new JButton("Alert"),new JButton("Yes/No"),
            new JButton("Color"),new JButton("Input")
    };

    private JTextField text = new JTextField(15);

    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            /**
             * get the text of current pressed button
             */
            String id = ((JButton)e.getSource()).getText();
            if(id.equals("Alert")){
                /**
                 * @param2 message dialog展示的内容
                 * @param3 message dialog的title
                 */
                JOptionPane.showMessageDialog(null,"There's a bug!","Hey", JOptionPane.ERROR_MESSAGE);
            }else if(id.equals("Color")){

            }else if(id.equals("Input")){
                String val = JOptionPane.showInputDialog("How many fingers do you see?");
                text.setText(val);
            }else if(id.equals("Yes/No")){
                JOptionPane.showConfirmDialog(null,"or no","choose yes", JOptionPane.YES_NO_OPTION);

            }

        }
    };


    public MessageBoxTest(){
        setLayout(new FlowLayout());

        /**
         * add buttons to the layout
         */
        for(int i = 0 ; i < b.length; i++){
            b[i].addActionListener(al);
            add(b[i]);
        }
        add(text);

    }

    public static void main(String[] args){
        SwingConsole.run(new MessageBoxTest(),200,200);

    }

}
