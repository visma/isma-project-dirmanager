package org.isma.dirmanager.refactor.model;

import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.IFileTestCaseFactory;

import java.io.File;

public class RefactorableDirManagerFileElementTest extends AbstractDirManagerFileTestCase {

    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new IFileTestCaseFactory<RefactorableFileElement, RefactorableDirElement>() {
            public RefactorableFileElement buildFileElement(File file) {
                return new RefactorableFileElement(file);
            }


            public RefactorableDirElement buildDirElement(File file) {
                return new RefactorableDirElement(file);
            }
        };
    }


    public void testIsElligible() throws Exception {
        RefactorableFileElement fileElement = (RefactorableFileElement)buildFileElement(FILE_SHORT_1);
        assertFalse(fileElement.isEligible());

        fileElement.setNewName(FILE_SHORT_2);
        assertTrue(fileElement.isEligible());
    }


    public void testIsRefactored() throws Exception {
        RefactorableFileElement fileElement = (RefactorableFileElement)buildFileElement(FILE_SHORT_1);
        assertFalse(fileElement.isRefactored());

        fileElement.setNewName(FILE_SHORT_2);
        assertFalse(fileElement.isRefactored());

        fileElement.save();
        assertTrue(fileElement.isRefactored());
    }


    public void testGetFile() throws Exception {
        RefactorableFileElement fileElement = (RefactorableFileElement)buildFileElement(FILE_SHORT_1);
        assertEquals(FILE_SHORT_1, fileElement.getFile().getName());
        assertTrue(fileElement.getFile().exists());

        fileElement.setNewName(FILE_SHORT_2);
        assertEquals(FILE_SHORT_2, fileElement.getFile().getName());
        assertFalse(fileElement.getFile().exists());
    }


    public void testSave() throws Exception {
        RefactorableFileElement fileElement = (RefactorableFileElement)buildFileElement(FILE_SHORT_1);
        fileElement.setNewName(FILE_SHORT_2);
        fileElement.save();

        assertEquals(FILE_SHORT_2, fileElement.getFile().getName());
        assertTrue(fileElement.getFile().exists());
    }
}
