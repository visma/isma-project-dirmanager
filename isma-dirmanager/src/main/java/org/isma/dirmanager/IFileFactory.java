package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public interface IFileFactory {
    public FileElement buildFile(File file);

    public DirElement buildDirectory(File file);
}
