package org.isma.dirmanager.backuper.synchronizer;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.BackupableFileTestCaseFactory;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.DefaultLogger;

public class DirManagerFileSynchronizerTest extends AbstractDirManagerFileTestCase {

    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new BackupableFileTestCaseFactory();
    }


    public void testSynchro() throws Exception {
        DirElement source = buildSource();
        DirElement target = buildEmptyTarget();

        FileSynchronizer synchronizer = new FileSynchronizer(source, target, new DefaultLogger());
        synchronizer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\"), 0);
        synchronizer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_1), 0);
        synchronizer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_2), 1);

        FileElement file1 = target.findChild(FILE_SHORT_1);
        FileElement file2 = target.findChild(FILE_SHORT_2);
        assertTrue(((BackuperFileElement)file1).isNew());
        assertTrue(((BackuperFileElement)file2).isNew());
        assertTrue(((BackuperFileElement)file1).isSynchronized());
        assertTrue(((BackuperFileElement)file2).isSynchronized());
    }


    private DirElement buildSource() throws Exception {
        DirElement source = buildDir("source");
        DirElement dir1 = buildDir("\\source\\dir1");
        source.addFileElement(dir1);
        dir1.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_1));
        dir1.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_2));
        return source;
    }


    private DirElement buildEmptyTarget() {
        return buildDir("target");
    }
}
