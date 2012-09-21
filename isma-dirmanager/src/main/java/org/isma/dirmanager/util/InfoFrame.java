package org.isma.dirmanager.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InfoFrame extends JFrame {

    public InfoFrame(String title, String text) {
        setTitle(title);
        JPanel toto = new JPanel();
        JTextField textField = new JTextField(text);
        textField.setName("infoFrame.textField");
        textField.setEditable(false);
        textField.setBorder(new EmptyBorder(textField.getInsets()));
        toto.add(textField);
        setContentPane(toto);
        setVisible(true);
        pack();
    }
}
