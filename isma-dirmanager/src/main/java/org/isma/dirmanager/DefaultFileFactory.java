package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class DefaultFileFactory implements IFileFactory {
    public FileElement buildFile(File file) {
        return new FileElement(file);
    }

    public DirElement buildDirectory(File file) {
        return new DirElement(file);
    }
}
