package org.isma.dirmanager.backuper.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;

public class BackuperForm implements IForm {
    private JPanel mainPanel;
    private JButton runButton;
    private JPanel sourcePanel;
    private JPanel targetPanel;
    private JPanel treesPanel;
    private JScrollPane sourceScrollPane;
    private JTree sourceTree;
    private JTree targetTree;
    private JTextField sourceTextField;
    private JTextField targetTextField;
    private JButton loadButton;
    private JButton sourceBrowseButton;
    private JButton targetBrowseButton;
    private JPanel progressBarPanel;
    private JSplitPane sliderPane;
    private JScrollPane targetScrollPane;


    public JPanel getMainPanel() {
        return mainPanel;
    }


    public JButton getRunButton() {
        return runButton;
    }


    public JTree getSourceTree() {
        return sourceTree;
    }


    public JTree getTargetTree() {
        return targetTree;
    }


    public JSplitPane getSliderPane() {
        return sliderPane;
    }


    public JButton getLoadButton() {
        return loadButton;
    }


    public JTextField getTargetTextField() {
        return targetTextField;
    }


    public JTextField getSourceTextField() {
        return sourceTextField;
    }


    public JButton getSourceBrowseButton() {
        return sourceBrowseButton;
    }


    public JButton getTargetBrowseButton() {
        return targetBrowseButton;
    }


    public JPanel getProgressBarPanel() {
        return progressBarPanel;
    }
}
