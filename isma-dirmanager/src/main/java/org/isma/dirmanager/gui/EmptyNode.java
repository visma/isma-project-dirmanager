package org.isma.dirmanager.gui;

import org.isma.dirmanager.model.DirElement;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class EmptyNode extends AbstractFileTreeNode<DirElement> {
    public EmptyNode() {
        super(null);
    }


    @Override
    public int getChildCount() {
        return 0;
    }


    @Override
    public TreeNode getChildAt(int childIndex) {
        return null;
    }


    @Override
    public TreeNode getParent() {
        return null;
    }


    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }


    @Override
    public boolean getAllowsChildren() {
        return false;
    }


    @Override
    public boolean isLeaf() {
        return true;
    }


    @Override
    public Enumeration children() {
        return null;
    }


    @Override
    public String toString() {
        return "";
    }
}
