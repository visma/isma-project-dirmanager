package org.isma.dirmanager.gui;

public interface FileTreeNodeVisitor {
    void visit(AbstractFileTreeNode node) throws Exception;
}
