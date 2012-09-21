package org.isma.dirmanager.gui;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.FileHelper;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DirectoryTreeNode extends AbstractFileTreeNode<DirElement> {
    public DirectoryTreeNode(DirElement file) {
        super(file);
    }


    public void insertChild(DefaultTreeModel treeModel, AbstractFileElement newFileElement, int childIndex) {
        File parentFile = newFileElement.getFile().getParentFile();
        if (FileHelper.hasSamePaths(parentFile, getFileElement().getFile())) {
            childIndex = childIndex > getChildCount() ? getChildCount() : childIndex;
            if (newFileElement.isDirectory()) {
                DirectoryTreeNode newChild = new DirectoryTreeNode((DirElement)newFileElement);
                treeModel.insertNodeInto(newChild, this, childIndex);
            }
            else {
                FileTreeNode newChild = new FileTreeNode((FileElement)newFileElement);
                treeModel.insertNodeInto(newChild, this, childIndex);
            }
            return;
        }
        Enumeration childrenEnum = children();
        while (childrenEnum.hasMoreElements()) {
            AbstractFileTreeNode node = (AbstractFileTreeNode)childrenEnum.nextElement();
            if (!node.isLeaf()) {
                DirectoryTreeNode directoryTreeNode = (DirectoryTreeNode)node;
                DirElement dirElement = directoryTreeNode.getFileElement();
                String dirPath = dirElement.getFile().getAbsolutePath();
                if (parentFile.getAbsolutePath().startsWith(dirPath)) {
                    directoryTreeNode.insertChild(treeModel, newFileElement, childIndex);
                }
            }
        }
    }


    public void deleteChild(AbstractFileTreeNode node, DefaultTreeModel treeModel) {
        treeModel.removeNodeFromParent(node);
        treeModel.nodeStructureChanged(getParent());
    }


    public DirectoryTreeNode findDirectoryNode(File dir) {
        if (!dir.isDirectory()) {
            throw new RuntimeException("path must be a dir path");
        }
        String currentPath = getFileElement().getFile().getAbsolutePath();
        if (currentPath.equals(dir.getAbsolutePath())) {
            return this;
        }
        Enumeration childrenEnum = children();
        while (childrenEnum.hasMoreElements()) {
            AbstractFileTreeNode treeNode = (AbstractFileTreeNode)childrenEnum.nextElement();
            if (!treeNode.getFileElement().isDirectory()) {
                continue;
            }
            DirectoryTreeNode directoryTreeNode = (DirectoryTreeNode)treeNode;
            if (FileHelper.isSubDirectory(directoryTreeNode.getFileElement().getFile(), dir)) {
                return directoryTreeNode.findDirectoryNode(dir);
            }
        }
        return null;
    }


    public AbstractFileTreeNode findNode(AbstractFileElement fileElement) {
        if (fileElement == null) {
            return null;
        }
        if (getFileElement() == fileElement) {
            return this;
        }
        Enumeration childrenEnum = children();
        while (childrenEnum.hasMoreElements()) {
            AbstractFileTreeNode fileTreeNode = (AbstractFileTreeNode)childrenEnum.nextElement();
            if (fileTreeNode.getFileElement() == fileElement) {
                return fileTreeNode;
            }
            if (!fileTreeNode.isLeaf()) {
                AbstractFileTreeNode childFound = ((DirectoryTreeNode)fileTreeNode).findNode(fileElement);
                if (childFound != null) {
                    return childFound;
                }
            }
        }
        return null;
    }


    public TreePath getTreePath() {
        TreeNode node = this;
        List<TreeNode> list = new ArrayList<TreeNode>();
        while (node != null) {
            list.add(node);
            node = node.getParent();
        }
        Collections.reverse(list);
        return new TreePath(list.toArray());
    }
}
