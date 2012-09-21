package org.isma.dirmanager;

import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

public class DefaultFileTreeNodeFactory implements IFileTreeNodeFactory {
    public DirectoryTreeNode buildDirectoryTreeNode(DirElement dirElement) {
        return new DirectoryTreeNode(dirElement);
    }

    public FileTreeNode buildFileTreeNode(FileElement fileElement) {
        return new FileTreeNode(fileElement);
    }
}
