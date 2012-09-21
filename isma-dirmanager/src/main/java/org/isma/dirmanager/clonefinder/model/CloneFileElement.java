package org.isma.dirmanager.clonefinder.model;

import org.isma.dirmanager.model.FileElement;
import org.isma.utils.io.FileUtils;

import java.io.File;

public class CloneFileElement extends FileElement {
    public CloneFileElement(File file) {
        super(file);
    }


    @Override
    public String getDetail() {
        return FileUtils.suppressBackSlashes(getFile().getAbsolutePath());
    }
}
