package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;

import java.io.File;
public class DirTreeLoaderTestDirManager extends AbstractDirManagerFileTestCase {

    DirTreeLoader treeLoader = new DirTreeLoader();


    @Override
    public void setUp() throws Exception {
        super.setUp();
        initTree();
    }


    private void initTree() throws Exception {
        mkdir("dir1");
        copyFile("dir1", FILE_SHORT_1);
        copyFile("dir1", FILE_SHORT_2);
        mkdir("dir2");
        copyFile("dir2", FILE_SHORT_1);
        copyFile("dir2", FILE_EMPTY_1);
    }


    public void testLoad() throws Exception {
        DirElement root = treeLoader.loadDirTree(new File(TEST_ROOT_DIR));
        //Root
        assertFile(root, "");
        assertEquals(2, root.getElements().size());

        //Root/dir1
        DirElement dir1 = (DirElement)root.getElements().get(0);
        assertFile(dir1, "dir1");
        assertEquals(2, dir1.getElements().size());
        FileElement short1 = (FileElement)dir1.getElements().get(0);
        FileElement short2 = (FileElement)dir1.getElements().get(1);
        assertFile(short1, "dir1\\" + FILE_SHORT_1);
        assertFile(short2, "dir1\\" + FILE_SHORT_2);

        //Root/dir2
        DirElement dir2 = (DirElement)root.getElements().get(1);
        assertTrue(dir2.getFile().getAbsolutePath().endsWith(TEST_ROOT_DIR + "dir2"));
        assertFile(dir2, "dir2");
        assertEquals(2, dir2.getElements().size());
        FileElement empty1 = (FileElement)dir2.getElements().get(0);
        short1 = (FileElement)dir2.getElements().get(1);
        assertFile(empty1, "dir2\\" + FILE_EMPTY_1);
        assertFile(short1, "dir2\\" + FILE_SHORT_1);
    }
}
