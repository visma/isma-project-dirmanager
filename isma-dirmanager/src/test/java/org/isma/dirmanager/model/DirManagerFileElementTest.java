package org.isma.dirmanager.model;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;

import java.io.File;

public class DirManagerFileElementTest extends AbstractDirManagerFileTestCase {

    private FileElement fileElement;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        fileElement = buildFileElement(FILE_SHORT_1);
    }


    public void testName() throws Exception {
        assertEquals(FILE_SHORT_1, fileElement.getName());
    }


    public void testDetail() throws Exception {
        assertEquals(FILE_SHORT_1, fileElement.getDetail());
    }

    public void testIsDirectory() throws Exception {
        assertFalse(fileElement.isDirectory());
    }


    public void testFile() throws Exception {
        File file = copyFile("", FILE_SHORT_1);
        assertEquals(file.getAbsolutePath(), fileElement.getFile().getAbsolutePath());
    }
}
