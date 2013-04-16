package org.isma.dirmanager.util;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;

import java.io.File;

import static org.isma.dirmanager.util.FileHelper.isSubDirectory;
import static org.isma.dirmanager.util.FileHelper.windowsUnifyVolumePath;

public class DirManagerFileHelperTest extends AbstractDirManagerFileTestCase {

    public void testWindowsUnifyVolumePath() throws Exception {
        assertEquals("C:/toto.txt", windowsUnifyVolumePath("c:/toto.txt"));
    }


    public void testIsSubDirectory() throws Exception {
        File dir1 = buildDir("dir1").getFile();
        File dir2 = buildDir("dir2").getFile();
        File subDir1 = buildDir("dir1/subdir1").getFile();

        assertFalse(isSubDirectory(dir1, dir2));
        assertFalse(isSubDirectory(dir2, dir1));

        assertTrue(isSubDirectory(dir1, subDir1));
        assertFalse(isSubDirectory(subDir1, dir1));
    }

    public void testhasSamePaths(){
        assertTrue(FileHelper.hasSamePaths("c:/dir/", "c:/dir"));
    }
}
