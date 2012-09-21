package org.isma.dirmanager.refactor.logic;

import org.isma.dirmanager.IFileTreeNodeFactory;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.refactor.gui.RefactorableFileTreeNode;
import org.isma.dirmanager.refactor.model.RefactorableFileElement;

public class RefactorableFileTreeNodeFactory implements IFileTreeNodeFactory {

    public DirectoryTreeNode buildDirectoryTreeNode(DirElement dirElement) {
        return new DirectoryTreeNode(dirElement);
    }

    public RefactorableFileTreeNode buildFileTreeNode(FileElement fileElement) {
        return new RefactorableFileTreeNode((RefactorableFileElement) fileElement);
    }
}
