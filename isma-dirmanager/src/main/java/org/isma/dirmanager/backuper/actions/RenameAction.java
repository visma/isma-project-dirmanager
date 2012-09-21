package org.isma.dirmanager.backuper.actions;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

import static javax.swing.JOptionPane.showInputDialog;

public class RenameAction extends AbstractContextualTreeAction {

    public RenameAction(DefaultTreeModel treeModel,
                        DefaultTreeModel otherTreeModel,
                        DirectoryTreeNode rootTreeNode,
                        DirectoryTreeNode otherRootTreeNode,
                        AbstractFileTreeNode fileTreeNode) {
        super(treeModel, otherTreeModel, rootTreeNode, otherRootTreeNode, fileTreeNode);
    }


    @Override
    protected void doAction() throws Exception {
        AbstractFileTreeNode otherFileTreeNode = findOtherFileTreeNode(fileElement);
        final String newName = (String)showInputDialog(null,
                                                       "Rename '" + fileElement.getName() + "'",
                                                       "Rename",
                                                       JOptionPane.INFORMATION_MESSAGE,
                                                       null,
                                                       null,
                                                       fileElement.getName());
        rename(treeModel, fileTreeNode, newName);
        if (otherFileTreeNode != null) {
            rename(otherTreeModel, otherFileTreeNode, newName);
        }
    }


    private void rename(DefaultTreeModel defaultTreeModel, AbstractFileTreeNode fileTreeNode, String newName) {
        File oldFile = fileTreeNode.getFileElement().getFile();
        File renamedFile = new File(oldFile.getParent(), newName);
        oldFile.renameTo(renamedFile);
        fileTreeNode.getFileElement().setFile(renamedFile);
        defaultTreeModel.nodeChanged(fileTreeNode);
    }
}
