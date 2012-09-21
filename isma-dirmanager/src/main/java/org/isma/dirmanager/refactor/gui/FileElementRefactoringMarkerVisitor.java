package org.isma.dirmanager.refactor.gui;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.FileTreeNodeVisitor;
import org.isma.dirmanager.refactor.FileRefactoringMarker;
import org.isma.dirmanager.refactor.RefactorListener;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class FileElementRefactoringMarkerVisitor implements FileTreeNodeVisitor {
    private RefactorListener refactorListener;
    private FileRefactoringMarker fileRenamer;
    //private DefaultTreeModel actualTreeModel;
    private DefaultTreeModel resultTreeModel;


    public FileElementRefactoringMarkerVisitor(RefactorListener refactorListener,
                                               FileRefactoringMarker fileRenamer,
                                               //DefaultTreeModel actualTreeModel,
                                               DefaultTreeModel resultTreeModel) {
        this.refactorListener = refactorListener;
        this.fileRenamer = fileRenamer;
        //this.actualTreeModel = actualTreeModel;
        this.resultTreeModel = resultTreeModel;
    }


    public void visit(AbstractFileTreeNode node) throws Exception {
        doMarkNewName(node);
        Enumeration children = node.children();
        while (children.hasMoreElements()) {
            visit((AbstractFileTreeNode)children.nextElement());
        }
    }


    private void doMarkNewName(AbstractFileTreeNode actualFileTreeNode) {
        if (actualFileTreeNode.getFileElement().isDirectory()) {
            return;
        }
        //TreeNode[] pathToRoot = actualTreeModel.getPathToRoot(actualFileTreeNode);
        TreeNode[] pathToRoot = resultTreeModel.getPathToRoot(actualFileTreeNode);
        TreeNode parentNode = pathToRoot[pathToRoot.length - 2];
        TreeNode childNode = pathToRoot[pathToRoot.length - 1];
        int childIndex = resultTreeModel.getIndexOfChild(parentNode, childNode);

        RefactorableFileTreeNode resultFileTreeNode = (RefactorableFileTreeNode)resultTreeModel.getChild(parentNode,
                                                                                                         childIndex);
        String newName = fileRenamer.getNewName(actualFileTreeNode.getFileElement());
        if (newName != null) {
            //((RefactorableFileTreeNode)actualFileTreeNode).rename(actualTreeModel, newName);
            resultFileTreeNode.rename(resultTreeModel, newName);
            refactorListener.existRefactoring();
        }
    }
}
