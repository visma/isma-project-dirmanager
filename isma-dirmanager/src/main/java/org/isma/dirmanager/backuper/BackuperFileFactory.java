package org.isma.dirmanager.backuper;

import org.isma.dirmanager.IFileFactory;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class BackuperFileFactory implements IFileFactory {
    public FileElement buildFile(File file) {
        return new BackuperFileElement(file, false, true);
    }

    public DirElement buildDirectory(File file) {
        return new BackuperDirElement(file, false, true);
    }
}
