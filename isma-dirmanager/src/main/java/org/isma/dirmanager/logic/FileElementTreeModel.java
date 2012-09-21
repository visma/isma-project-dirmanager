package org.isma.dirmanager.logic;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class FileElementTreeModel extends DefaultTreeModel {
    public FileElementTreeModel(TreeNode root) {
        super(root);
    }

    @Override
    public void nodeChanged(TreeNode node) {
        super.nodeChanged(node);
        if (node.getParent() != null) {
            nodeChanged(node.getParent());
        }
    }
}
