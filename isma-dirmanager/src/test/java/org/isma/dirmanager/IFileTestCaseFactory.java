package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public interface IFileTestCaseFactory<F extends FileElement, D extends DirElement> {
    public F buildFileElement(File file);


    public D buildDirElement(File file);
}
