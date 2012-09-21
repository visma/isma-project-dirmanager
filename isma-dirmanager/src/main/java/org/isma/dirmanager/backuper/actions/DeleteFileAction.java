package org.isma.dirmanager.backuper.actions;

import org.apache.commons.io.FileUtils;
import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

public class DeleteFileAction extends AbstractContextualTreeAction {

    public DeleteFileAction(DefaultTreeModel treeModel,
                            DefaultTreeModel otherTreeModel,
                            DirectoryTreeNode rootTreeNode,
                            DirectoryTreeNode otherRootTreeNode,
                            AbstractFileTreeNode fileTreeNode) {
        super(treeModel, otherTreeModel, rootTreeNode, otherRootTreeNode, fileTreeNode);
    }


    @Override
    protected void doAction() throws Exception {
        AbstractFileTreeNode otherFileTreeNode = findOtherFileTreeNode(fileElement);
        if (showConfirmDialog(null,
                              "Are you sure to delete '" + fileElement.getName() + "' ?",
                              "Delete",
                              JOptionPane.INFORMATION_MESSAGE) == OK_OPTION) {
            delete(treeModel, fileTreeNode);
            if (otherFileTreeNode != null) {
                delete(otherTreeModel, otherFileTreeNode);
            }
        }
    }


    private void delete(DefaultTreeModel treeModel, AbstractFileTreeNode fileTreeNode) throws IOException {
        treeModel.removeNodeFromParent(fileTreeNode);
        System.out.println("delete " + fileTreeNode.getFileElement().getFile());
        FileUtils.forceDelete(fileTreeNode.getFileElement().getFile());
    }
}
