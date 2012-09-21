package org.isma.dirmanager.clonefinder.model;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.model.FileElement;

public class CloneDirElementTestDirManager extends AbstractDirManagerFileTestCase {

    public void testDetail() throws Exception {
        CloneDirElement cloneDirElement = new CloneDirElement();
        FileElement file2 = buildFileElement(FILE_SHORT_1_COPY);
        FileElement file1 = buildFileElement(FILE_SHORT_1);
        CloneFileElement clone1 = new CloneFileElement(file1.getFile());
        CloneFileElement clone2 = new CloneFileElement(file2.getFile());

        cloneDirElement.addFileElement(clone1);
        cloneDirElement.addFileElement(clone2);
        assertEquals("five_char_file.txt -- Size : 0 Kb", cloneDirElement.getDetail());
    }
}
