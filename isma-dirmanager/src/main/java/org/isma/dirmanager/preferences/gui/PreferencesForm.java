package org.isma.dirmanager.preferences.gui;

import org.isma.guitoolkit.IForm;

import javax.swing.*;
/**
 *
 */
public class PreferencesForm implements IForm {
    private JTextField sourcePreferenceTextField;
    private JTextField targetPreferenceTextField;
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton sourceBrowseButton;
    private JButton targetBrowseButton;
    private JButton restoreDefaultButton;


    public JTextField getSourcePreferenceTextField() {
        return sourcePreferenceTextField;
    }


    public JButton getSourceBrowseButton() {
        return sourceBrowseButton;
    }


    public JButton getTargetBrowseButton() {
        return targetBrowseButton;
    }


    public JTextField getTargetPreferenceTextField() {
        return targetPreferenceTextField;
    }


    public JButton getSaveButton() {
        return saveButton;
    }


    public JButton getRestoreDefaultButton() {
        return restoreDefaultButton;
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
