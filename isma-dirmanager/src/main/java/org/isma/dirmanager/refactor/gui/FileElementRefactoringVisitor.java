package org.isma.dirmanager.refactor.gui;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNodeVisitor;
import org.isma.dirmanager.refactor.model.RefactorableFileElement;

import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;

public class FileElementRefactoringVisitor implements FileTreeNodeVisitor {
    //private DirectoryTreeNode actualRootTreeNode;
    //private DefaultTreeModel actualTreeModel;
    private DirectoryTreeNode resultRootTreeNode;
    private DefaultTreeModel resultTreeModel;


    public FileElementRefactoringVisitor(/*DirectoryTreeNode actualRootTreeNode, DefaultTreeModel actualTreeModel,*/
                                         DirectoryTreeNode resultRootTreeNode, DefaultTreeModel resultTreeModel) {
        //this.actualRootTreeNode = actualRootTreeNode;
        //this.actualTreeModel = actualTreeModel;
        this.resultRootTreeNode = resultRootTreeNode;
        this.resultTreeModel = resultTreeModel;
    }


    public void visit(AbstractFileTreeNode node) throws Exception {
        doSynchronize(node);
        Enumeration children = node.children();
        while (children.hasMoreElements()) {
            visit((AbstractFileTreeNode)children.nextElement());
        }
    }


    private void doSynchronize(AbstractFileTreeNode node) {
        if (node.getFileElement().isDirectory()) {
            return;
        }
        RefactorableFileElement refactorableFileElement = (RefactorableFileElement)node.getFileElement();
        if (refactorableFileElement.isEligible()) {
            refactorableFileElement.save();
            resultTreeModel.nodeChanged(resultRootTreeNode);
            //actualTreeModel.nodeChanged(actualRootTreeNode);
        }
    }
}
