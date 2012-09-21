package org.isma.dirmanager.backuper.model;

import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class BackuperFileElement extends FileElement implements BackupableFileElement {
    private boolean isNew;
    private boolean isSynchro;

    public BackuperFileElement(File file, boolean isNew, boolean isSynchro) {
        super(file);
        this.isNew = isNew;
        this.isSynchro = isSynchro;
    }

    public boolean isSynchronized() {
        return isSynchro;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setSynchronized(boolean aSynchronized) {
        this.isSynchro = aSynchronized;
    }
}
