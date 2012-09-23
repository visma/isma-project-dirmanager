package org.isma.dirmanager.model;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;

import java.util.List;

public class DirElementTestDirManager extends AbstractDirManagerFileTestCase {

    public void testIsDirectory() throws Exception {
        DirElement dirElement = buildDir("dir");
        assertTrue(dirElement.isDirectory());
    }


    public void testAddFileElement() throws Exception {
        DirElement dirElement = buildDir("dir");
        assertEquals(0, dirElement.getElements().size());
        FileElement fileElement1 = buildFileElement("dir", FILE_SHORT_1);
        dirElement.addFileElement(fileElement1);
        assertEquals(1, dirElement.getElements().size());
        assertEquals(fileElement1, dirElement.getElements().get(0));
        FileElement fileElement2 = buildFileElement("dir", FILE_SHORT_2);
        dirElement.addFileElement(fileElement2);
        assertEquals(2, dirElement.getElements().size());
        assertEquals(fileElement2, dirElement.getElements().get(1));
    }


    public void testDetail() throws Exception {
        DirElement dirElement = buildDir("dir");
        assertEquals("dir --- Size : 0 Mo, Files : 0", dirElement.getDetail());
        FileElement fileElement = buildFileElement("dir", FILE_SHORT_1);
        dirElement.addFileElement(fileElement);
        assertEquals("dir --- Size : 0 Mo, Files : 1", dirElement.getDetail());
        FileElement fileElement2 = buildFileElement("dir", FILE_SHORT_2);
        dirElement.addFileElement(fileElement2);
        assertEquals("dir --- Size : 0 Mo, Files : 2", dirElement.getDetail());
    }


    public void testDepthFileElementList() throws Exception {
        DirElement dir = buildDir("dir");
        DirElement dir1 = buildDir("dir\\dir1");
        DirElement dir2 = buildDir("dir\\dir1\\dir2");
        FileElement file1 = buildFileElement("dir\\", FILE_SHORT_1);
        FileElement file2 = buildFileElement("dir\\dir1\\dir2\\", FILE_SHORT_2);
        FileElement file3 = buildFileElement("dir\\dir1\\dir2\\", FILE_EMPTY_1);

        dir.addFileElement(file1);
        dir.addFileElement(dir1);
        dir1.addFileElement(dir2);
        dir2.addFileElement(file2);
        dir2.addFileElement(file3);

        List<FileElement> fileElementList = dir.getDepthFileElementList();
        assertEquals(3, fileElementList.size());
        assertEquals(file1, fileElementList.get(0));
        assertEquals(file2, fileElementList.get(1));
        assertEquals(file3, fileElementList.get(2));
    }


    public void testFileElementRelativePath() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir1\\dir2");
        DirElement dir3 = buildDir("dir1\\dir2\\dir3");
        FileElement file1 = buildFileElement("dir1\\dir2\\dir3", FILE_SHORT_1);

        rootDir.addFileElement(dir1);
        dir1.addFileElement(dir2);
        dir2.addFileElement(dir3);
        dir3.addFileElement(file1);

        assertEquals("\\dir1\\dir2\\dir3\\five_char_file.txt", rootDir.getFileElementRelativePath(file1));
        assertEquals("\\dir2\\dir3\\five_char_file.txt", dir1.getFileElementRelativePath(file1));
        assertEquals("\\dir3\\five_char_file.txt", dir2.getFileElementRelativePath(file1));
        assertEquals("\\five_char_file.txt", dir3.getFileElementRelativePath(file1));
    }


    public void testFileElementByPath() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir1\\dir2");
        FileElement file1 = buildFileElement("dir1\\dir2", FILE_SHORT_1);
        DirElement dir3 = buildDir("dir1\\dir2\\dir3");
        FileElement copyOfFile1 = buildFileElement("dir1\\dir2\\dir3", FILE_SHORT_1);

        rootDir.addFileElement(dir1);
        dir1.addFileElement(dir2);
        dir2.addFileElement(dir3);
        dir2.addFileElement(file1);
        dir3.addFileElement(copyOfFile1);

        String absoluteFilePath = getAbsolutePathTestRootDir() + "dir1\\dir2\\" + FILE_SHORT_1;
        AbstractFileElement fileFound = rootDir.getFileElement(absoluteFilePath);
        assertEquals(file1, fileFound);

        absoluteFilePath = getAbsolutePathTestRootDir() + "dir1\\dir2\\dir3\\" + FILE_SHORT_1;
        fileFound = rootDir.getFileElement(absoluteFilePath);
        assertEquals(copyOfFile1, fileFound);
    }


    public void testAddChildWithIndex() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir1\\dir2");
        FileElement file1 = buildFileElement("dir1\\dir2", FILE_SHORT_1);
        FileElement file2 = buildFileElement("dir1\\dir2", FILE_SHORT_2);
        FileElement file3 = buildFileElement("dir1\\dir2", FILE_SHORT_TEN_CHAR);
        FileElement file4 = buildFileElement("dir1\\dir2", FILE_EMPTY_1);

        rootDir.addChild(dir1, 0);
        dir1.addChild(dir2, 0);
        dir2.addChild(file4, 0);
        dir2.addChild(file1, 0);
        dir2.addChild(file2, 0);
        dir2.addChild(file3, 3);
        //Ordre : file2, file1, file4, file3

        assertEquals(1, rootDir.getElements().size());
        assertEquals(dir1, rootDir.getElements().get(0));

        assertEquals(1, dir1.getElements().size());
        assertEquals(dir2, dir1.getElements().get(0));

        assertEquals(4, dir2.getElements().size());
        assertEquals(file2, dir2.getElements().get(0));
        assertEquals(file1, dir2.getElements().get(1));
        assertEquals(file4, dir2.getElements().get(2));
        assertEquals(file3, dir2.getElements().get(3));
    }


    public void testAddChild() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir1\\dir2");
        FileElement file1 = buildFileElement("dir1\\dir2", FILE_SHORT_1);
        FileElement file2 = buildFileElement("dir1\\dir2", FILE_SHORT_2);
        FileElement file3 = buildFileElement("dir1\\dir2", FILE_SHORT_TEN_CHAR);
        FileElement file4 = buildFileElement("dir1\\dir2", FILE_EMPTY_1);

        rootDir.addChild(dir1);
        dir1.addChild(dir2);
        dir2.addChild(file4);
        dir2.addChild(file1);
        dir2.addChild(file2);
        dir2.addChild(file3);
        //Ordre : file4, file1, file2, file3

        assertEquals(1, rootDir.getElements().size());
        assertEquals(dir1, rootDir.getElements().get(0));

        assertEquals(1, dir1.getElements().size());
        assertEquals(dir2, dir1.getElements().get(0));

        assertEquals(4, dir2.getElements().size());
        assertEquals(file4, dir2.getElements().get(0));
        assertEquals(file1, dir2.getElements().get(1));
        assertEquals(file2, dir2.getElements().get(2));
        assertEquals(file3, dir2.getElements().get(3));
    }


    public void testFindChild() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir2 = buildDir("dir1\\dir2");
        FileElement file1 = buildFileElement("dir1\\dir2\\", FILE_SHORT_1);

        rootDir.addFileElement(dir1);
        dir1.addFileElement(dir2);
        dir2.addFileElement(file1);

        assertEquals(file1, rootDir.findChild(FILE_SHORT_1));
    }
}
