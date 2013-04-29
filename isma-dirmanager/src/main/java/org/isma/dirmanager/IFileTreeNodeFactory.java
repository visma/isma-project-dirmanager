package org.isma.dirmanager;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

public interface IFileTreeNodeFactory {
    DirectoryTreeNode buildDirectoryTreeNode(DirElement dirElement);

    AbstractFileTreeNode buildFileTreeNode(FileElement fileElement);
}
