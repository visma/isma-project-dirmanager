package org.isma.dirmanager.refactor.logic;

import org.isma.dirmanager.IFileFactory;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.refactor.model.RefactorableDirElement;
import org.isma.dirmanager.refactor.model.RefactorableFileElement;

import java.io.File;

public class RefactorableFileFactory implements IFileFactory {
    public FileElement buildFile(File file) {
        return new RefactorableFileElement(file);
    }

    public DirElement buildDirectory(File file) {
        return new RefactorableDirElement(file);
    }
}
