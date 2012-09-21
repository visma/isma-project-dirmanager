package org.isma.dirmanager.model;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;

public class DirManagerFileComparatorTest extends AbstractDirManagerFileTestCase {
    private FileComparator fileComparator = new FileComparator();


    public void testEmptyVsEmpty() throws Exception {
        FileElement empty1 = buildFileElement(FILE_EMPTY_1);
        FileElement empty2 = buildFileElement(FILE_EMPTY_2);
        assertEquals(0, fileComparator.compare(empty1, empty2));
    }


    public void testEmptyVsNotEmpty() throws Exception {
        FileElement empty1 = buildFileElement(FILE_EMPTY_1);
        FileElement short1 = buildFileElement(FILE_SHORT_1);
        assertNotSame(0, fileComparator.compare(empty1, short1));
        assertEquals(-fileComparator.compare(short1, empty1), fileComparator.compare(empty1, short1));
    }


    public void testSameContent() throws Exception {
        FileElement short1 = buildFileElement(FILE_SHORT_1);
        FileElement short1Copy = buildFileElement(FILE_SHORT_1_COPY);
        assertEquals(0, fileComparator.compare(short1, short1Copy));
    }


    public void testSameSizeButNotSameContent() throws Exception {
        FileElement short1 = buildFileElement(FILE_SHORT_1);
        FileElement short2 = buildFileElement(FILE_SHORT_2);
        assertNotSame(0, fileComparator.compare(short1, short2));
    }
}
