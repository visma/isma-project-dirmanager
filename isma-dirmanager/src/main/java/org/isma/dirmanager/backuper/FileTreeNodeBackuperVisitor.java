package org.isma.dirmanager.backuper;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNodeVisitor;
import org.isma.dirmanager.model.AbstractFileElement;

import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;

public class FileTreeNodeBackuperVisitor implements FileTreeNodeVisitor {
    private IFileBackuper fileBackuper;
    private DirectoryTreeNode targetRootTreeNode;
    private DefaultTreeModel sourceTreeModel;
    private DefaultTreeModel targetTreeModel;


    public FileTreeNodeBackuperVisitor(
          IFileBackuper fileBackuper,
          DefaultTreeModel sourceTreeModel,
          DefaultTreeModel targetTreeModel,
          DirectoryTreeNode targetRootTreeNode) {
        this.sourceTreeModel = sourceTreeModel;
        this.targetTreeModel = targetTreeModel;
        this.targetRootTreeNode = targetRootTreeNode;
        this.fileBackuper = fileBackuper;
    }


    public void visit(AbstractFileTreeNode node) throws Exception {
        doSynchronize(node);
        Enumeration children = node.children();
        while (children.hasMoreElements()) {
            visit((AbstractFileTreeNode)children.nextElement());
        }
    }


    private void doSynchronize(AbstractFileTreeNode sourceTreeNode) throws Exception {
        int childIndex = sourceTreeNode.getOwnIndex();
        AbstractFileElement newFile = fileBackuper.backup(sourceTreeNode.getFileElement(), childIndex);
        if (newFile != null) {
            sourceTreeModel.nodeChanged(sourceTreeNode);
            targetRootTreeNode.insertChild(targetTreeModel, newFile, childIndex);
        }
    }
}
