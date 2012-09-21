package org.isma.dirmanager;

import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

public class FileTreeNodeBuilder {
    private IFileTreeNodeFactory fileTreeNodeFactory;

    public FileTreeNodeBuilder() {
        this(new DefaultFileTreeNodeFactory());
    }

    public FileTreeNodeBuilder(IFileTreeNodeFactory fileTreeNodeFactory) {
        this.fileTreeNodeFactory = fileTreeNodeFactory;
    }

    public DirectoryTreeNode buildRootFileTreeNode(DirElement dirElement) {
        DirectoryTreeNode rootNode = fileTreeNodeFactory.buildDirectoryTreeNode(dirElement);
        for (AbstractFileElement fileElement : dirElement.getElements()) {
            if (fileElement.isDirectory()) {
                rootNode.addChild(buildRootFileTreeNode((DirElement) fileElement));
            } else {
                rootNode.addChild(fileTreeNodeFactory.buildFileTreeNode((FileElement) fileElement));
            }
        }
        return rootNode;
    }
}
