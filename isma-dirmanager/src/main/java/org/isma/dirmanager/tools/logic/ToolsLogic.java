package org.isma.dirmanager.tools.logic;

import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.tools.gui.ToolsForm;
import org.isma.dirmanager.tools.log.LogLogic;
import org.isma.dirmanager.tools.virtualize.VirtualizeLogic;
import org.isma.guitoolkit.GuiUtils;
import org.isma.guitoolkit.LogicActionListener;

import javax.swing.*;
import java.io.File;

public class ToolsLogic extends AbstractDirManagerLogic<ToolsForm> {
    public static final File TMP_DIR = new File(System.getProperty("java.io.tmpdir"));


    public ToolsLogic(IDirConfiguration conf) {
        super(conf);
        initForm();
    }


    @Override
    protected ToolsForm buildForm() {
        return new ToolsForm();
    }


    private void initForm() {
        GuiUtils.plugDirectoryChooser(form.getMainPanel(), form.getBrowseButton(), form.getSourceDirectoryTextField());
        addListeners();
    }


    private void addListeners() {
        form.getLogButton().addActionListener(new LogicActionListener() {
            @Override
            protected void doAction() throws Exception {
                final String sourcePath = form.getSourceDirectoryTextField().getText();
                if (checkDirectory(sourcePath)) {
                    final File dirToVirtualize = new File(sourcePath);
                    new LogLogic(TMP_DIR).log(dirToVirtualize);
                }
            }
        });
        form.getVirtualizeButton().addActionListener(new LogicActionListener() {
            @Override
            protected void doAction() throws Exception {
                final String sourcePath = form.getSourceDirectoryTextField().getText();
                if (checkDirectory(sourcePath)) {
                    final File dirToVirtualize = new File(sourcePath);
                    new VirtualizeLogic(TMP_DIR).virtualize(dirToVirtualize);
                }
            }
        });
    }


    public JComponent getGui() {
        return form.getMainPanel();
    }


    @Override
    public void onConfigurationChange() {
        form.getSourceDirectoryTextField().setText(conf.getSourceFilePath());
    }
}
