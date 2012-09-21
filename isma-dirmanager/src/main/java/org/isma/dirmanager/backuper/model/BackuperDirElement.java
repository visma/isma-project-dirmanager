package org.isma.dirmanager.backuper.model;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;

import java.io.File;

public class BackuperDirElement extends DirElement implements BackupableFileElement {
    private boolean isNew;
    private boolean isSynchro;


    public BackuperDirElement(File directory, boolean isNew, boolean isSynchro) {
        super(directory);
        this.isSynchro = isSynchro;
        this.isNew = isNew;
    }


    public boolean isNew() {
        if (isNew) {
            return true;
        }
        for (AbstractFileElement abstractFileElement : getElements()) {
            BackupableFileElement backupableFileElement = (BackupableFileElement)abstractFileElement;
            if (backupableFileElement.isNew()) {
                return true;
            }
        }
        return false;
    }


    public boolean isSynchronized() {
        if (!isSynchro) {
            return false;
        }
        for (AbstractFileElement abstractFileElement : getElements()) {
            BackupableFileElement backupableFileElement = (BackupableFileElement)abstractFileElement;
            if (!backupableFileElement.isSynchronized()) {
                return false;
            }
        }
        return true;
    }


    public void setSynchronized(boolean aSynchronized) {
        isSynchro = aSynchronized;
    }


    //TODO un peu crade... a foutre dans une classe de stats ?
    public int getUnsynchronizationCount() {
        int count = 0;
        for (AbstractFileElement element : getElements()) {
            if (!element.isDirectory()) {
                count += ((BackuperFileElement)element).isSynchronized() ? 0 : 1;
            }
            else {
                count += ((BackuperDirElement)element).getUnsynchronizationCount();
            }
        }
        return count;
    }
}
