package org.isma.dirmanager.model;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;

public class DirManagerFileMapTest extends AbstractDirManagerFileTestCase {

    public void testExists() throws Exception {
        DirElement root = buildDir();
        FileMap map = new FileMap(root);
        assertFalse(map.exists(buildFileElement(FILE_EMPTY_1)));
        assertFalse(map.exists(buildFileElement(FILE_SHORT_2)));

        assertTrue(map.exists(buildFileElement(FILE_SHORT_1)));
        assertTrue(map.exists(buildFileElement(FILE_SHORT_1_COPY)));
        assertTrue(map.exists(buildFileElement(FILE_SHORT_TEN_CHAR)));
    }


    public void testExists2() throws Exception {
        DirElement root = buildDir();
        FileElement file1 = buildFileElement(FILE_SHORT_1);
        FileElement file2 = buildFileElement(FILE_SHORT_2);
        FileElement file3 = buildFileElement(FILE_SHORT_TEN_CHAR);

        FileElement file1Clone = buildFileElement(FILE_SHORT_1_COPY);
        root.addFileElement(file3);
        root.addFileElement(file2);
        root.addFileElement(file1);
        FileMap map = new FileMap(root);

        assertTrue(map.exists(file1Clone));
    }


    public void testPutNew() throws Exception {
        DirElement root = buildDir();
        FileMap map = new FileMap(root);
        assertTrue(map.putNew(buildFileElement(FILE_EMPTY_1)));
        assertTrue(map.exists(buildFileElement(FILE_EMPTY_1)));
        assertFalse(map.putNew(buildFileElement(FILE_EMPTY_1)));
    }


    private DirElement buildDir() throws Exception {
        DirElement root = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir2");
        FileElement file1 = buildFileElement("dir", FILE_SHORT_1);
        FileElement file2 = buildFileElement("dir2", FILE_SHORT_TEN_CHAR);

        root.addFileElement(dir1);
        root.addFileElement(dir2);
        dir1.addFileElement(file1);
        dir2.addFileElement(file2);
        return root;
    }
}
