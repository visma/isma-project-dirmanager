package org.isma.dirmanager.model;

public interface FileElementVisitor {
    void visit(AbstractFileElement fileElement) throws Exception;
}
