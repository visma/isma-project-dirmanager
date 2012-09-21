package org.isma.dirmanager.backuper.actions;

import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.guitoolkit.LogicActionListener;

import java.awt.*;

public class OpenFileAction extends LogicActionListener {
    private AbstractFileElement fileElement;


    public OpenFileAction(BackupableFileElement fileElement) {
        this.fileElement = (AbstractFileElement)fileElement;
    }


    @Override
    protected void doAction() throws Exception {
        Desktop.getDesktop().open(fileElement.getFile());
    }
}
