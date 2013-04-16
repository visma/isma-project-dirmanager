package org.isma.dirmanager.gui;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.FileTreeNodeBuilder;
import org.isma.dirmanager.logic.FileElementTreeModel;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

public class DirectoryTreeNodeTestDirManager extends AbstractDirManagerFileTestCase {
    private FileTreeNodeBuilder fileTreeNodeBuilder = new FileTreeNodeBuilder();


    public void testInsertChild() throws Exception {
        //Init
        DirElement root = buildTestDir();
        DirectoryTreeNode rootTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(root);
        FileElementTreeModel treeModel = new FileElementTreeModel(rootTreeNode);

        assertEquals(2, rootTreeNode.getChildCount());
        rootTreeNode.insertChild(treeModel, buildFileElement("target/", FILE_SHORT_2), 1);
        assertEquals(3, rootTreeNode.getChildCount());
    }


    public void testFindNode() throws Exception {
        DirElement root = buildTestDir();
        DirectoryTreeNode rootTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(root);

        FileElement fileShort1 = root.findChild(FILE_SHORT_1);
        assertEquals(fileShort1, rootTreeNode.findNode(fileShort1).getFileElement());
    }


    private DirElement buildTestDir() throws Exception {
        DirElement targetRoot = buildDir("target");
        DirElement subDir = buildDir("target/sub2");
        FileElement fileA = buildFileElement("target/sub2/", FILE_SHORT_1);

        targetRoot.addChild(buildDir("target/toto"));
        targetRoot.addChild(subDir);
        subDir.addChild(fileA);
        return targetRoot;
    }
}
