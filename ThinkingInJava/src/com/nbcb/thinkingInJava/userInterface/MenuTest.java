package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuTest extends JFrame{

    private JMenu[] menus = {
            new JMenu("File"),new JMenu("View"),
            new JMenu("Run"),new JMenu("Info")
    };

    private JMenuItem[] items = {
            new JMenuItem("Open"),new JMenuItem("Save"),new JMenuItem("Exit")
    };

    JMenuBar mb = new JMenuBar();

    JTextField text = new JTextField(20);
    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem selectedMenuItem =  (JMenuItem)e.getSource();
            text.setText(selectedMenuItem.getText());
        }
    };


    public MenuTest(){

        /**
         * add menu to the MenuBar
         */
        for(JMenu menu: menus){
            mb.add(menu);
        }

        /**
         * add ActionListener to itmes
         */
        for(JMenuItem menuItem: items){
            menuItem.addActionListener(al);
        }


        /**
         * add items to the menu
         */
        menus[0].add(items[0]);
        menus[0].add(items[1]);
        menus[0].add(items[2]);



        setJMenuBar(mb);
        setLayout(new FlowLayout());
        add(text);

    }

    public static void main(String[] args){
        SwingConsole.run(new MenuTest(),200,200);
    }
}
