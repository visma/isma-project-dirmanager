package org.isma.dirmanager.model;

public interface FileElementVisitor {
    public void visit(AbstractFileElement fileElement) throws Exception;
}
