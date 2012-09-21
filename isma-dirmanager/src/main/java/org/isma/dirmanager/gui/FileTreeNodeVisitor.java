package org.isma.dirmanager.gui;

public interface FileTreeNodeVisitor {
    public void visit(AbstractFileTreeNode node) throws Exception;
}
