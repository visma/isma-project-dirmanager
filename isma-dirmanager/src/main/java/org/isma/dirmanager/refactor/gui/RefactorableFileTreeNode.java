package org.isma.dirmanager.refactor.gui;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.refactor.model.RefactorableFileElement;

import javax.swing.tree.DefaultTreeModel;

public class RefactorableFileTreeNode extends AbstractFileTreeNode<RefactorableFileElement> {
    public RefactorableFileTreeNode(RefactorableFileElement file) {
        super(file);
    }

    public void rename(DefaultTreeModel treeModel, String newName) {
        getFileElement().setNewName(newName);
        treeModel.nodeChanged(this);
    }
}
