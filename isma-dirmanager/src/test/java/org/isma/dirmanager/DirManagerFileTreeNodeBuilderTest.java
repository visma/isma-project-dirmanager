package org.isma.dirmanager;

import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.junit.Test;

import java.io.File;
import java.util.Enumeration;
public class DirManagerFileTreeNodeBuilderTest extends AbstractDirManagerFileTestCase {

    private FileTreeNodeBuilder builder = new FileTreeNodeBuilder();

    @Test
    public void testBuild() throws Exception {
        DirElement root = buildDir();
        DirectoryTreeNode rootTreeNode = builder.buildRootFileTreeNode(root);
        //root
        assertFile(rootTreeNode.getFileElement(), "");
        assertEquals(3, rootTreeNode.getChildCount());
        Enumeration children = rootTreeNode.children();
        FileTreeNode file2 = (FileTreeNode)children.nextElement();
        DirectoryTreeNode dirNode1 = (DirectoryTreeNode)children.nextElement();
        DirectoryTreeNode dirNode2 = (DirectoryTreeNode)children.nextElement();
        //dir1
        assertEquals(1, dirNode1.getChildCount());
        FileTreeNode fileNode1 = (FileTreeNode)dirNode1.children().nextElement();
        assertFile(dirNode1.getFileElement(), "dir1");
        assertFile(fileNode1.getFileElement(), "dir1/file.txt");
        //dir2
        assertFile(dirNode2.getFileElement(), "dir2");
        assertEquals(0, dirNode2.getChildCount());
        //file2
        assertFile(file2.getFileElement(), "file2.txt");
    }


    //TODO utiliser les machins abstraits de l'abstract pour homogeneiser les instanciations
    private DirElement buildDir() {
        DirElement root = new DirElement(new File(TEST_ROOT_DIR));
        DirElement dir1 = new DirElement(new File(TEST_ROOT_DIR + "dir1"));
        dir1.addFileElement(new FileElement(new File(TEST_ROOT_DIR + "dir1/file.txt")));
        DirElement dir2 = new DirElement(new File(TEST_ROOT_DIR + "dir2"));
        root.addFileElement(new FileElement(new File(TEST_ROOT_DIR + "file2.txt")));
        root.addFileElement(dir1);
        root.addFileElement(dir2);
        return root;
    }
}
