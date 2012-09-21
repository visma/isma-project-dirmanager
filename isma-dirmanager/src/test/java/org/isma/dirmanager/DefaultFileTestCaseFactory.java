package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class DefaultFileTestCaseFactory implements IFileTestCaseFactory<FileElement, DirElement> {
    public FileElement buildFileElement(File file) {
        return new FileElement(file);
    }


    public DirElement buildDirElement(File file) {
        return new DirElement(file);
    }
}
