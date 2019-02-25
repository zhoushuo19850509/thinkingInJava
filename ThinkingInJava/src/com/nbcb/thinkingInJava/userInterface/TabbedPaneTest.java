package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 所谓的tabbedPane，就是
 */
public class TabbedPaneTest extends JFrame {

    private String[] flavors = {"a","b","c","d","e","f"};
    private JTabbedPane tabs = new JTabbedPane();
    private JTextField text = new JTextField(20);
    private JButton button = new JButton("run");

    /**
     * constructor
     */
    public TabbedPaneTest(){
        for(int i = 0 ; i < flavors.length; i++){
            tabs.addTab(flavors[i], new JButton("Tabbed pane" + i));
        }

        /**
         * 给TabbedPane添加监听事件
         * 一旦tab变化，就触发这个事件
         */
        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                text.setText("tab selected" + tabs.getSelectedIndex());
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("tab selected" + (tabs.getSelectedIndex()+1));
            }
        });

        add(BorderLayout.NORTH,text);
        add(BorderLayout.SOUTH,button);
        add(tabs);
    }

    public static void main(String[] args){
        SwingConsole.run(new TabbedPaneTest(),600,250);

    }

}
