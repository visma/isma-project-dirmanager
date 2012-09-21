package org.isma.dirmanager.backuper;

import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;

import java.io.File;

public class BackupableFileTestCaseFactory implements IFileTestCaseFactory<BackuperFileElement, BackuperDirElement> {

    public BackuperFileElement buildFileElement(File file) {
        return new BackuperFileElement(file, false, true);
    }


    public BackuperDirElement buildDirElement(File file) {
        return new BackuperDirElement(file, false, true);
    }
}
