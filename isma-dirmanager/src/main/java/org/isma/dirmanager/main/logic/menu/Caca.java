package org.isma.dirmanager.main.logic.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Caca {

    public static void main(String[] args) {

    }


    private static void buildMenuGraphique() {
        JFrame frame = new JFrame();
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Caca");
        JMenuItem menuItem1 = new JMenuItem("menuItem1 Caca  zedfdfs");
        menuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("toto");
            }
        });
        menu.add(menuItem1);
        menu.add(new JMenuItem("menuItem2"));

        menubar.add(menu);
        frame.setJMenuBar(menubar);
        frame.setVisible(true);
    }
}
