package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButton extends JFrame {

    private JTextField t = new JTextField(15);
    private ButtonGroup g = new ButtonGroup();
    private JRadioButton rb1 = new JRadioButton("one",false);
    private JRadioButton rb2 = new JRadioButton("two",false);
    private JRadioButton rb3 = new JRadioButton("three",false);

    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JRadioButton button = (JRadioButton)e.getSource();
            t.setText("Radio button: " + button.getText());
        }
    };


    /**
     * constructor
     */
    public RadioButton(){

        /**
         * 3个RadionButton从属于一个ButtonGroup
         */
        g.add(rb1);
        g.add(rb2);
        g.add(rb3);

        rb1.addActionListener(al);
        rb2.addActionListener(al);
        rb3.addActionListener(al);

        setLayout(new FlowLayout());
        add(t);
        add(rb1);
        add(rb2);
        add(rb3);

    }

    public static void main(String[] args){//        SwingConsole.run(new RadioButton(),200,125);
        SwingConsole.run(new RadioButton(),200,125);

    }
}
