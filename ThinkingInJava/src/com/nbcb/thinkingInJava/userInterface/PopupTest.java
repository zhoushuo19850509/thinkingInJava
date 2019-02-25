package com.nbcb.thinkingInJava.userInterface;

import com.nbcb.thinkingInJava.util.SwingConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * PopupMenu组件的用法
 * 可以在例子中，右键鼠标，看看PopupMenu是怎么展示的
 */
public class PopupTest extends JFrame{

    private JPopupMenu popup = new JPopupMenu();

    private JTextField text = new JTextField(15);

    /**
     * constructor
     */
    public PopupTest(){
        setLayout(new FlowLayout());
        add(text);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText(((JMenuItem)e.getSource()).getText());
            }
        };

        JMenuItem m = new JMenuItem("Helo");
        m.addActionListener(al);
        popup.add(m);

        m = new JMenuItem("Hob");
        m.addActionListener(al);
        popup.add(m);

        m = new JMenuItem("Welcome");
        m.addActionListener(al);
        popup.add(m);

        popup.addSeparator();

        m = new JMenuItem("To the Earth");
        m.addActionListener(al);
        popup.add(m);

        m = new JMenuItem("Eat something");
        m.addActionListener(al);
        popup.add(m);

        PopupListener pl = new PopupListener();
        addMouseListener(pl);

        text.addMouseListener(pl);

    }

    class PopupListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e){
            maybeShowPopup(e);
        }

        public void maybeShowPopup(MouseEvent e){
            if(e.isPopupTrigger()){
                popup.show(e.getComponent(),e.getX(),e.getY());
            }
        }

    }

    public static void main(String[] args){
        SwingConsole.run(new PopupTest(),200,200);
    }
}
