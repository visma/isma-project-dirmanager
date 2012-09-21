package org.isma.dirmanager.gui;

import org.isma.dirmanager.model.AbstractFileElement;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class AbstractFileTreeNode<E extends AbstractFileElement> extends DefaultMutableTreeNode {
    private E file;

    protected AbstractFileTreeNode(E file) {
        this.file = file;
    }

    public void accept(FileTreeNodeVisitor visitor) throws Exception {
        visitor.visit(this);
    }


    public void addChild(AbstractFileTreeNode treeNode) {
        insert(treeNode, getChildCount());
    }

    public E getFileElement() {
        return file;
    }

    public int getOwnIndex() {
        return getParent() == null ? 0 : getParent().getIndex(this);
    }

    @Override
    public boolean getAllowsChildren() {
        return file.isDirectory();
    }

    @Override
    public boolean isLeaf() {
        return !file.isDirectory();
    }


    @Override
    public String toString() {
        return file.getName();
    }
}
