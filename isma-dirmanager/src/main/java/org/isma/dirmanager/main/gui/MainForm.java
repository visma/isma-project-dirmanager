package org.isma.dirmanager.main.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class MainForm implements IForm {
    private JPanel mainPanel;
    private JButton expandButton;
    private JButton collapseButton;
    private JPanel mainContent;
    private JButton refreshButton;
    private JButton runButton;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JButton getExpandButton() {
        return expandButton;
    }


    public JButton getCollapseButton() {
        return collapseButton;
    }


    public JPanel getMainContent() {
        return mainContent;
    }


    public JButton getRefreshButton() {
        return refreshButton;
    }


    public JButton getRunButton() {
        return runButton;
    }
}
