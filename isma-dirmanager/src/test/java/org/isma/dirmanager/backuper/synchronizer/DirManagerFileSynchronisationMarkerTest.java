package org.isma.dirmanager.backuper.synchronizer;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.BackupableFileTestCaseFactory;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.util.DefaultLogger;

public class DirManagerFileSynchronisationMarkerTest extends AbstractDirManagerFileTestCase {
    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new BackupableFileTestCaseFactory();
    }


    public void testMarker() throws Exception {
        DirElement dirSource = buildSource();
        DirElement dirTarget = buildTarget();
        FileSynchronisationMarker marker = new FileSynchronisationMarker(dirSource, dirTarget, new DefaultLogger());
        BackuperFileElement file1Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_1);
        BackuperFileElement file2Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_2);
        //Initial state
        assertTrue(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
        //Marking
        marker.markSynchronisation(file1Source);
        marker.markSynchronisation(file2Source);
        //Check method
        assertFalse(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
    }


    public void testMarkerOnUnSynchroStructure() throws Exception {
        DirElement dirSource = buildSource();
        DirElement dirTarget = buildUnSynchroTarget();
        FileSynchronisationMarker marker = new FileSynchronisationMarker(dirSource, dirTarget, new DefaultLogger());
        BackuperFileElement file1Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_1);
        BackuperFileElement file2Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_2);
        //Initial state
        assertTrue(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
        //Marking
        marker.markSynchronisation(file1Source);
        marker.markSynchronisation(file2Source);
        //Check method
        assertFalse(file1Source.isSynchronized());
        assertFalse(file2Source.isSynchronized());
    }


    private DirElement buildSource() throws Exception {
        DirElement source = buildDir("source");
        DirElement dir1 = buildDir("\\source\\dir1");
        source.addFileElement(dir1);
        dir1.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_1));
        dir1.addFileElement(buildFileElement("\\source\\dir1", FILE_SHORT_2));
        return source;
    }


    private DirElement buildTarget() throws Exception {
        DirElement target = buildDir("target");
        DirElement dir1 = buildDir("\\target\\dir1");
        target.addFileElement(dir1);
        dir1.addFileElement(buildFileElement("\\target\\dir1", FILE_SHORT_2));
        return target;
    }


    private DirElement buildUnSynchroTarget() throws Exception {
        DirElement target = buildDir("target");
        DirElement dir1 = buildDir("\\target\\dir1");
        DirElement subdir = buildDir("\\target\\dir1\\subdir\\");
        target.addFileElement(dir1);
        dir1.addFileElement(subdir);
        subdir.addFileElement(buildFileElement("\\target\\dir1\\subdir\\", FILE_SHORT_2));
        return target;
    }
}
