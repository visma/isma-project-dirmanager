package org.isma.dirmanager.model;

import java.io.File;

public abstract class AbstractFileElement {
    private File file;


    protected AbstractFileElement(File file) {
        this.file = file;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public void accept(FileElementVisitor fileElementVisitor) throws Exception {
        fileElementVisitor.visit(this);
    }


    public boolean isDirectory() {
        return getFile().isDirectory();
    }


    public abstract String getDetail();


    public String getName() {
        return getFile() == null ? null : getFile().getName();
    }
}
