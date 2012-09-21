package org.isma.dirmanager.tools.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class ToolsForm implements IForm {
    private JTextField sourceDirectoryTextField;
    private JButton browseButton;
    private JButton logButton;
    private JPanel mainPanel;
    private JButton virtualizeButton;


    public JTextField getSourceDirectoryTextField() {
        return sourceDirectoryTextField;
    }


    public JButton getBrowseButton() {
        return browseButton;
    }


    public JButton getLogButton() {
        return logButton;
    }


    public JButton getVirtualizeButton() {
        return virtualizeButton;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
