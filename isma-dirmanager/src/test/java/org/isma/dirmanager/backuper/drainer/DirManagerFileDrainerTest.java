package org.isma.dirmanager.backuper.drainer;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.BackupableFileTestCaseFactory;
import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.DefaultLogger;

public class DirManagerFileDrainerTest extends AbstractDirManagerFileTestCase {
    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new BackupableFileTestCaseFactory();
    }


    public void testDrainFilesOnSynchroTree() throws Exception {
        DirElement source = buildSource();
        DirElement target = buildEmptyTarget(source);

        FileDrainer drainer = new FileDrainer(source, target, new DefaultLogger());
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\" + FILE_EMPTY_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_2), 1);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_TEN_CHAR), 2);

        FileElement file1 = target.findChild(FILE_SHORT_1);
        FileElement file2 = target.findChild(FILE_SHORT_2);
        FileElement file3 = target.findChild(FILE_SHORT_TEN_CHAR);
        assertTrue(((BackuperFileElement)file1).isNew());
        assertTrue(((BackuperFileElement)file2).isNew());
        assertTrue(((BackuperFileElement)file3).isNew());
        assertTrue(((BackuperFileElement)file1).isSynchronized());
        assertTrue(((BackuperFileElement)file2).isSynchronized());
        assertTrue(((BackuperFileElement)file3).isSynchronized());

        assertTrue(target.findChild(FILE_EMPTY_1).getFile().getAbsolutePath().endsWith("target\\" + FILE_EMPTY_1));
        assertTrue(target.findChild(FILE_SHORT_1)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("target\\dir1\\" + FILE_SHORT_1));
        assertTrue(target.findChild(FILE_SHORT_2)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("target\\dir1\\" + FILE_SHORT_2));
        assertTrue(target.findChild(FILE_SHORT_TEN_CHAR)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("target\\dir1\\" + FILE_SHORT_TEN_CHAR));

        assertTrue(target.findChild(FILE_SHORT_1).getFile().exists());
        assertTrue(target.findChild(FILE_SHORT_2).getFile().exists());
        assertTrue(target.findChild(FILE_SHORT_TEN_CHAR).getFile().exists());
    }


    public void testNoDrain() throws Exception {
        DirElement source = buildSource();
        DirElement target = buildUnSynchroTarget(source);

        FileDrainer drainer = new FileDrainer(source, target, new DefaultLogger());
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\" + FILE_EMPTY_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_2), 1);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_TEN_CHAR), 2);

        FileElement file1 = target.findChild(FILE_SHORT_1);
        FileElement file2 = target.findChild(FILE_SHORT_2);
        FileElement file3 = target.findChild(FILE_SHORT_TEN_CHAR);
        assertFalse(((BackuperFileElement)file1).isNew());
        assertFalse(((BackuperFileElement)file2).isNew());
        assertFalse(((BackuperFileElement)file3).isNew());
        assertTrue(((BackuperFileElement)file1).isSynchronized());
        assertTrue(((BackuperFileElement)file2).isSynchronized());
        assertTrue(((BackuperFileElement)file3).isSynchronized());
    }


    public void testDrainOnUnSynchroTree() throws Exception {
        DirElement source = buildSource();
        DirElement target = buildNotCompleteTargetOnUnSynchroTree(source);

        FileDrainer drainer = new FileDrainer(source, target, new DefaultLogger());
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\" + FILE_EMPTY_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\"), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_2), 1);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\dir1\\" + FILE_SHORT_TEN_CHAR), 2);

        FileElement file0 = target.findChild(FILE_EMPTY_1);
        FileElement file1 = target.findChild(FILE_SHORT_1);
        FileElement file2 = target.findChild(FILE_SHORT_2);
        FileElement file3 = target.findChild(FILE_SHORT_TEN_CHAR);
        assertTrue(((BackuperFileElement)file0).isNew());
        assertFalse(((BackuperFileElement)file1).isNew());
        assertFalse(((BackuperFileElement)file2).isNew());
        assertTrue(((BackuperFileElement)file3).isNew());

        assertTrue(((BackuperFileElement)file1).isSynchronized());
        assertTrue(((BackuperFileElement)file2).isSynchronized());
        assertTrue(((BackuperFileElement)file3).isSynchronized());

        assertTrue(target.findChild(FILE_EMPTY_1).getFile().getAbsolutePath().endsWith("target\\" + FILE_EMPTY_1));
        assertTrue(target.findChild(FILE_SHORT_1)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("target\\otherDir\\" + FILE_SHORT_1));
        assertTrue(target.findChild(FILE_SHORT_2)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("target\\otherDir\\" + FILE_SHORT_2));
        assertTrue(target.findChild(FILE_SHORT_TEN_CHAR)
                         .getFile()
                         .getAbsolutePath()
                         .endsWith("\\target\\_DRAINED_FILES\\dir1\\" + FILE_SHORT_TEN_CHAR));

        assertTrue(target.findChild(FILE_SHORT_1).getFile().exists());
        assertTrue(target.findChild(FILE_SHORT_2).getFile().exists());
        assertTrue(target.findChild(FILE_SHORT_TEN_CHAR).getFile().exists());
    }


    public void testDrainNoClonesFromSource() throws Exception {
        DirElement source = buildSourceWithClones();
        DirElement target = buildEmptyTarget(source);

        FileDrainer drainer = new FileDrainer(source, target, new DefaultLogger());
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\" + FILE_SHORT_1), 0);
        drainer.backup(source.getFileElement(getAbsolutePathTestRootDir() + "source\\" + FILE_SHORT_1_COPY), 1);

        assertNotNull(target.findChild(FILE_SHORT_1));
        assertNull(target.findChild(FILE_SHORT_1_COPY));
    }


    private DirElement buildSourceWithClones() throws Exception {
        DirElement source = buildDir("source");
        source.addFileElement(buildFileElement("\\source\\", FILE_EMPTY_1));
        source.addFileElement(buildFileElement("\\source\\", FILE_SHORT_2));
        source.addFileElement(buildFileElement("\\source\\", FILE_SHORT_TEN_CHAR));
        //The clones files
        source.addFileElement(buildFileElement("\\source\\", FILE_SHORT_1));
        source.addFileElement(buildFileElement("\\source\\", FILE_SHORT_1_COPY));
        ((BackupableFileElement)source.findChild(FILE_SHORT_1_COPY)).setSynchronized(false);
        return source;
    }


    private DirElement buildSource() throws Exception {
        DirElement source = buildDir("source");
        DirElement dir = buildDir("\\source\\dir1");
        source.addFileElement(buildFileElement("\\source\\", FILE_EMPTY_1));
        source.addFileElement(dir);
        dir.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_1));
        dir.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_2));
        dir.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_TEN_CHAR));
        return source;
    }


    private DirElement buildEmptyTarget(DirElement source) {
        //Prerequisite : marking source
        ((BackupableFileElement)source.findChild(FILE_EMPTY_1)).setSynchronized(false);
        ((BackupableFileElement)source.findChild(FILE_SHORT_1)).setSynchronized(false);
        ((BackupableFileElement)source.findChild(FILE_SHORT_2)).setSynchronized(false);
        ((BackupableFileElement)source.findChild(FILE_SHORT_TEN_CHAR)).setSynchronized(false);
        //
        DirElement target = buildDir("target");
        DirElement dir1 = buildDir("\\target\\dir1");
        target.addFileElement(dir1);
        return target;
    }


    private DirElement buildUnSynchroTarget(DirElement source) throws Exception {
        //Prerequisite : marking source
        ((BackupableFileElement)source.findChild(FILE_EMPTY_1)).setSynchronized(false);
        //
        DirElement target = buildDir("target");
        DirElement dir = buildDir("\\target\\otherDir");
        target.addFileElement(dir);
        dir.addFileElement(buildFileElement("\\target\\otherDir", FILE_SHORT_1));
        dir.addFileElement(buildFileElement("\\target\\otherDir", FILE_SHORT_2));
        dir.addFileElement(buildFileElement("\\target\\otherDir", FILE_SHORT_TEN_CHAR));
        return target;
    }


    private DirElement buildNotCompleteTargetOnUnSynchroTree(DirElement source) throws Exception {
        //Prerequisite : marking source
        ((BackupableFileElement)source.findChild(FILE_EMPTY_1)).setSynchronized(false);
        ((BackupableFileElement)source.findChild(FILE_SHORT_TEN_CHAR)).setSynchronized(false);
        //
        DirElement target = buildDir("target");
        DirElement dir = buildDir("\\target\\otherDir");
        target.addFileElement(dir);
        dir.addFileElement(buildFileElement("\\target\\otherDir", FILE_SHORT_1));
        dir.addFileElement(buildFileElement("\\target\\otherDir", FILE_SHORT_2));
        //prerequis au drain
        DirElement drainDir = buildDir("\\target\\_DRAINED_FILES");
        target.addFileElement(drainDir);
        return target;
    }
}
