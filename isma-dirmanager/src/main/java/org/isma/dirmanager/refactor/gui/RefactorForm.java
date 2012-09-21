package org.isma.dirmanager.refactor.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class RefactorForm implements IForm {
    private JTextField directoryTextField;
    private JButton browseButton;
    private JTree actualTree;
    private JPanel mainPanel;
    private JTree resultTree;
    private JScrollPane actualScrollPane;
    private JScrollPane resultScrollPane;


    public JTextField getDirectoryTextField() {
        return directoryTextField;
    }


    public JButton getBrowseButton() {
        return browseButton;
    }


    public JTree getActualTree() {
        return actualTree;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JTree getResultTree() {
        return resultTree;
    }


    public JScrollPane getActualScrollPane() {
        return actualScrollPane;
    }


    public JScrollPane getResultScrollPane() {
        return resultScrollPane;
    }
}
