package org.isma.dirmanager.clonefinder.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class CloneFinderForm implements IForm {
    private JPanel mainPanel;
    private JTextField directoryTextField;
    private JButton browseButton;
    private JTree directoryTree;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JTextField getDirectoryTextField() {
        return directoryTextField;
    }


    public JButton getBrowseButton() {
        return browseButton;
    }


    public JTree getDirectoryTree() {
        return directoryTree;
    }
}
