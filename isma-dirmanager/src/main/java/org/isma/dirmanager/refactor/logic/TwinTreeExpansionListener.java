package org.isma.dirmanager.refactor.logic;

import org.isma.dirmanager.gui.DirectoryTreeNode;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

public class TwinTreeExpansionListener implements TreeExpansionListener {
    private JTree twinTree;


    public TwinTreeExpansionListener(JTree twinTree) {
        this.twinTree = twinTree;
    }


    public void treeExpanded(TreeExpansionEvent event) {
        twinTree.expandPath(getTreePath(event));
    }


    public void treeCollapsed(TreeExpansionEvent event) {
        twinTree.collapsePath(getTreePath(event));
    }


    private TreePath getTreePath(TreeExpansionEvent event) {
        DirectoryTreeNode sourceDirNode = (DirectoryTreeNode)event.getPath().getPath()[event.getPath().getPath().length
                                                                                       - 1];
        DirectoryTreeNode targetRootNode = (DirectoryTreeNode)twinTree.getModel().getRoot();
        DirectoryTreeNode targetDirNode = targetRootNode.findDirectoryNode(sourceDirNode.getFileElement().getFile());
        return targetDirNode == null ? null : targetDirNode.getTreePath();
    }
}
