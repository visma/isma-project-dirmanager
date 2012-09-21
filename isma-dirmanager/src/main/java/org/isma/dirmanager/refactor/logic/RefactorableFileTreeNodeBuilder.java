package org.isma.dirmanager.refactor.logic;

import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.refactor.gui.RefactorableFileTreeNode;
import org.isma.dirmanager.refactor.model.RefactorableFileElement;

public class RefactorableFileTreeNodeBuilder {


    public DirectoryTreeNode buildRootFileTreeNode(DirElement dirElement) {
        DirectoryTreeNode rootNode = new DirectoryTreeNode(dirElement);
        for (AbstractFileElement fileElement : dirElement.getElements()) {
            if (fileElement.isDirectory()) {
                rootNode.addChild(buildRootFileTreeNode((DirElement) fileElement));
            } else {
                rootNode.addChild(new RefactorableFileTreeNode((RefactorableFileElement) fileElement));
            }
        }
        return rootNode;
    }
}
