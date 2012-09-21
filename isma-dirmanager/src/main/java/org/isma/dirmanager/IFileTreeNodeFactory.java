package org.isma.dirmanager;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

public interface IFileTreeNodeFactory {
    public DirectoryTreeNode buildDirectoryTreeNode(DirElement dirElement);

    public AbstractFileTreeNode buildFileTreeNode(FileElement fileElement);
}
