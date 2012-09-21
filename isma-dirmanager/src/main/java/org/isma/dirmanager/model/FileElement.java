package org.isma.dirmanager.model;

import java.io.File;

public class FileElement extends AbstractFileElement {

    public FileElement(File file) {
        super(file);
    }


    @Override
    public String getDetail() {
        return getName();
    }
}
