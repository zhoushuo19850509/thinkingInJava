package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Menu���Ӹ߼����÷�
 * ����д��һ�㣬Ŀǰ���ò���Menu�ĸ߼����� �����ٲ���
 */
public class MenuAdvancedTest extends JFrame{

    /**
     * kinds of UI components
     */
    private JTextField text = new JTextField("no flavor",30);

    /**
     * first menu bar
     */
    private JMenuBar mb1 = new JMenuBar();
    private JMenu f = new JMenu("File");
    private JMenu m = new JMenu("Flavors");
    private JMenu s = new JMenu("Safety");

    private JCheckBoxMenuItem[] safety = {
            new JCheckBoxMenuItem("Guard"),
            new JCheckBoxMenuItem("Hide")
    };

    JMenuItem[] file = {new JMenuItem("Open")};

    /**
     * second menu bar
     */
    JMenuBar mb2 = new JMenuBar();
    JMenu fooBar = new JMenu("foobar");
    /**
     * add menu shortcut
     */
    JMenuItem[] other = {new JMenuItem("Foo", KeyEvent.VK_F),
            new JMenuItem("Bar", KeyEvent.VK_A)};


    JButton button = new JButton("Swap menus");

    /**
     * kinds of implementations of ActionListener
     */

    /**
     * BL is short for Bar Listener
     * ���ActionListener�������飬�����л�MenuBar
     */
    class BL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuBar m = getJMenuBar();
            setJMenuBar(m == mb1 ? mb2:mb1);
            validate();
        }
    }





    /**
     * constructor
     */
    public MenuAdvancedTest(){



    }

    public static void main(String[] args){
        SwingConsole.run(new MenuAdvancedTest(),300,200);

    }

}
