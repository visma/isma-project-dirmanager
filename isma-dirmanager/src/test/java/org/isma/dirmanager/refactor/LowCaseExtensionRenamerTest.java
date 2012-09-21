package org.isma.dirmanager.refactor;
import junit.framework.TestCase;

public class LowCaseExtensionRenamerTest extends TestCase {

    public void testRename() throws Exception {
        assertEquals("file.txt", new LowCaseExtensionRenamer().rename("file.TXT"));
        assertEquals("file.txt", new LowCaseExtensionRenamer().rename("file.Txt"));
        assertEquals("file.txt", new LowCaseExtensionRenamer().rename("file.txT"));

        assertEquals("FILE", new LowCaseExtensionRenamer().rename("FILE"));

        assertEquals("file.SALUT.COUCOU.txt", new LowCaseExtensionRenamer().rename("file.SALUT.COUCOU.TXT"));
    }
}
