package org.isma.dirmanager.clonefinder.logic;

import junit.framework.Assert;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.clonefinder.model.CloneDirElement;
import org.isma.dirmanager.clonefinder.model.CloneRootDirElement;
import org.isma.dirmanager.model.DirElement;

public class CloneTreeNodeBuilderTestDirManager extends AbstractDirManagerFileTestCase {
    private CloneTreeNodeBuilder builder = new CloneTreeNodeBuilder();


    public void testBuildCloneRootDirElement() throws Exception {
        CloneRootDirElement cloneRootDir = builder.buildCloneRootDirElement(buildRoot());
        Assert.assertEquals(2, cloneRootDir.getElements().size());
        CloneDirElement cloneDir1 = (CloneDirElement)cloneRootDir.getElements().get(0);
        CloneDirElement cloneDir2 = (CloneDirElement)cloneRootDir.getElements().get(1);
        assertEquals("ten_char_file.txt -- Size : 0 Kb", cloneDir1.getDetail());
        Assert.assertEquals(2, cloneDir1.getElements().size());
        assertEquals("five_char_file.txt -- Size : 0 Kb", cloneDir2.getDetail());
        Assert.assertEquals(4, cloneDir2.getElements().size());
    }


    private DirElement buildRoot() throws Exception {
        DirElement rootDir = buildRootDir();
        DirElement dir1 = buildDir("dir1");
        DirElement dir1A = buildDir("dir1\\dir1A");
        DirElement dir2 = buildDir("dir2");

        rootDir.addFileElement(buildFileElement(FILE_SHORT_1));
        rootDir.addFileElement(dir1);
        rootDir.addFileElement(dir2);
        rootDir.addFileElement(buildFileElement(FILE_SHORT_TEN_CHAR));

        dir1.addFileElement(dir1A);
        dir1.addFileElement(buildFileElement("dir1\\", FILE_SHORT_1));
        dir1.addFileElement(buildFileElement("dir1\\", FILE_SHORT_1_COPY));

        dir1A.addFileElement(buildFileElement("dir1\\dir1A", FILE_SHORT_1));
        dir1A.addFileElement(buildFileElement("dir1\\dir1A", FILE_SHORT_TEN_CHAR));

        return rootDir;
    }
}
