package org.isma.dirmanager.preferences;

import org.isma.dirmanager.*;
import org.isma.dirmanager.preferences.gui.PreferencesForm;
import org.isma.guitoolkit.GuiUtils;
import org.isma.guitoolkit.error.ErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class PreferencesLogic extends AbstractDirManagerLogic<PreferencesForm> {
    private PreferencesManager preferencesManager = new PreferencesManager();
    static final String CONFIRM_MESSAGE = "Configuration saved";
    static final String ERROR_MESSAGE = "Configuration not saved";


    public PreferencesLogic(IDirConfiguration conf,
                            final List<LogicListener> logicListenerList) throws Exception {
        super(conf);
        final IDirConfiguration defaultConf = new DirConfigurationFactory().buildDefaultDirConfiguration();
        form.getSourcePreferenceTextField().setText(conf.getSourceFilePath());
        form.getTargetPreferenceTextField().setText(conf.getTargetFilePath());

        GuiUtils.plugDirectoryChooser(form.getMainPanel(),
                                      form.getSourceBrowseButton(),
                                      form.getSourcePreferenceTextField());
        GuiUtils.plugDirectoryChooser(form.getMainPanel(),
                                      form.getTargetBrowseButton(),
                                      form.getTargetPreferenceTextField());

        form.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isCheckOk()) {
                        save(logicListenerList);
                    }
                }
                catch (Exception ex) {
                    ErrorHandler.handleException(ex);
                }
            }
        });
        form.getRestoreDefaultButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                preferencesManager.remove(DirConfigurationFactory.SOURCE_DIR_KEY);
                preferencesManager.remove(DirConfigurationFactory.TARGET_DIR_KEY);
                form.getSourcePreferenceTextField().setText(defaultConf.getSourceFilePath());
                form.getTargetPreferenceTextField().setText(defaultConf.getTargetFilePath());
            }
        });
    }


    private boolean isCheckOk() {
        final String sourcePath = form.getSourcePreferenceTextField().getText();
        final String targetPath = form.getTargetPreferenceTextField().getText();
        return checkDirectory(sourcePath) && checkDirectory(targetPath) && checkNoSubDirectory(sourcePath, targetPath);
    }


    private void save(List<LogicListener> logicListenerList) {
        preferencesManager.put(DirConfigurationFactory.SOURCE_DIR_KEY, form.getSourcePreferenceTextField().getText());
        preferencesManager.put(DirConfigurationFactory.TARGET_DIR_KEY, form.getTargetPreferenceTextField().getText());
        showMessageDialog(form.getMainPanel(), CONFIRM_MESSAGE, CONFIRM_MESSAGE, INFORMATION_MESSAGE);
        for (ConfigurationChangeListener configurationChangeListener : logicListenerList) {
            configurationChangeListener.onConfigurationChange();
        }
        ((Window)form.getMainPanel().getRootPane().getParent()).dispose();
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    @Override
    protected PreferencesForm buildForm() {
        return new PreferencesForm();
    }
}
