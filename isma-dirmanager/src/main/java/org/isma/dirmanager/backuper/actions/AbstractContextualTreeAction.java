package org.isma.dirmanager.backuper.actions;

import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.guitoolkit.LogicActionListener;

import javax.swing.tree.DefaultTreeModel;

public abstract class AbstractContextualTreeAction extends LogicActionListener {
    protected final DefaultTreeModel treeModel;
    protected final DefaultTreeModel otherTreeModel;

    protected final DirectoryTreeNode rootTreeNode;
    protected final DirectoryTreeNode otherRootTreeNode;

    protected final AbstractFileTreeNode fileTreeNode;
    protected final BackupableFileElement fileElement;


    protected AbstractContextualTreeAction(DefaultTreeModel treeModel,
                                           DefaultTreeModel otherTreeModel,
                                           DirectoryTreeNode rootTreeNode,
                                           DirectoryTreeNode otherRootTreeNode,
                                           AbstractFileTreeNode fileTreeNode) {
        this.treeModel = treeModel;
        this.otherTreeModel = otherTreeModel;
        this.rootTreeNode = rootTreeNode;
        this.otherRootTreeNode = otherRootTreeNode;
        this.fileTreeNode = fileTreeNode;
        this.fileElement = (BackupableFileElement)fileTreeNode.getFileElement();
    }


    protected AbstractFileTreeNode findOtherFileTreeNode(BackupableFileElement fileElement) {
        AbstractFileElement abstractFileElement = (AbstractFileElement)fileElement;
        DirElement rootDirElement = rootTreeNode.getFileElement();
        String fileElementRelativePath = rootDirElement.getFileElementRelativePath(abstractFileElement);

        DirElement otherRootDirElement = otherRootTreeNode.getFileElement();
        String rootAbsolutePath = otherRootDirElement.getFile().getAbsolutePath();
        AbstractFileElement otherFileElement = otherRootDirElement.getFileElement(
              rootAbsolutePath + fileElementRelativePath);
        return otherRootTreeNode.findNode(otherFileElement);
    }
}
