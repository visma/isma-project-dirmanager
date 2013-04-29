package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public interface IFileFactory {
    FileElement buildFile(File file);

    DirElement buildDirectory(File file);
}
