package org.isma.dirmanager.backuper.drainer;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.BackupableFileTestCaseFactory;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.util.DefaultLogger;

public class DirManagerFileDrainerMarkerTest extends AbstractDirManagerFileTestCase {
    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new BackupableFileTestCaseFactory();
    }


    public void testMarkerOnSynchroStructure() throws Exception {
        DirElement dirSource = buildSynchroSource();
        DirElement dirTarget = buildSynchroTarget();
        FileDrainerMarker marker = new FileDrainerMarker(dirSource, dirTarget, new DefaultLogger());
        BackuperFileElement file1Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_1);
        BackuperFileElement file2Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_2);
        BackuperDirElement dirEmptySource = (BackuperDirElement)dirSource.getFileElement(
              getAbsolutePathTestRootDir() + "source/dirEmpty");
        //Initial state
        assertTrue(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
        assertTrue(dirEmptySource.isSynchronized());
        //Marking
        marker.markUnsynchronisation(file1Source);
        marker.markUnsynchronisation(file2Source);
        marker.markUnsynchronisation(dirEmptySource);
        //Check method
        assertFalse(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
        assertTrue(dirEmptySource.isSynchronized());
    }


    public void testMarkerOnUnSynchroStructure() throws Exception {
        DirElement dirSource = buildSynchroSource();
        DirElement dirTarget = buildUnSynchroTarget();
        FileDrainerMarker marker = new FileDrainerMarker(dirSource, dirTarget, new DefaultLogger());
        BackuperFileElement file1Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_1);
        BackuperFileElement file2Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_2);
        BackuperFileElement file3Source = (BackuperFileElement)dirSource.findChild(FILE_SHORT_TEN_CHAR);
        BackuperDirElement dirEmptySource = (BackuperDirElement)dirSource.getFileElement(
              getAbsolutePathTestRootDir() + "source/dirEmpty");
        BackuperDirElement dirSynchroContent = (BackuperDirElement)dirSource.getFileElement(
              getAbsolutePathTestRootDir() + "source/dirSynchroContent");
        //Initial state
        assertTrue(file1Source.isSynchronized());
        assertTrue(file2Source.isSynchronized());
        assertTrue(dirEmptySource.isSynchronized());
        //Marking
        marker.markUnsynchronisation(file1Source);
        marker.markUnsynchronisation(file2Source);
        marker.markUnsynchronisation(dirEmptySource);
        marker.markUnsynchronisation(dirSynchroContent);
        marker.markUnsynchronisation(file3Source);
        //Check method
        assertFalse(file1Source.isSynchronized());
        //si un fichier identique est trouv� � un autre nv d'arbo = c'est sync
        assertTrue(file2Source.isSynchronized());
        //si un dir de meme nom est trouv� � un autre nv d'arbo = sync si le contenu est sync (c-a-d si le dir est vide ou si le contenu existe qlq part dans target)
        assertTrue(dirEmptySource.isSynchronized());
        assertTrue(dirSynchroContent.isSynchronized());
    }


    private DirElement buildSynchroSource() throws Exception {
        DirElement source = buildDir("source");
        DirElement dir1 = buildDir("/source/dir1");
        DirElement dirEmpty = buildDir("/source/dirEmpty");
        DirElement dirSynchroContent = buildDir("/source/dirSynchroContent");
        source.addFileElement(dir1);
        source.addFileElement(dirEmpty);
        source.addFileElement(dirSynchroContent);
        dir1.addFileElement(buildFileElement("/source/dir1", FILE_SHORT_1));
        dir1.addFileElement(buildFileElement("/source/dir1", FILE_SHORT_2));
        dirSynchroContent.addFileElement(buildFileElement("/source/dirSynchroContent/", FILE_SHORT_TEN_CHAR));
        return source;
    }


    private DirElement buildSynchroTarget() throws Exception {
        DirElement target = buildDir("target");
        DirElement dir1 = buildDir("/target/dir1");
        DirElement dirEmpty = buildDir("/target/dirEmpty");
        target.addFileElement(dir1);
        target.addFileElement(dirEmpty);
        dir1.addFileElement(buildFileElement("/target/dir1", FILE_SHORT_2));
        return target;
    }


    private DirElement buildUnSynchroTarget() throws Exception {
        DirElement target = buildDir("target");
        target.addFileElement(buildFileElement("/target/", FILE_SHORT_TEN_CHAR));
        DirElement dir1 = buildDir("/target/dir1");
        DirElement subdir = buildDir("/target/dir1/subdir/");
        DirElement dirEmpty = buildDir("/target/dir1/subdir/dirEmpty");
        target.addFileElement(dir1);
        dir1.addFileElement(subdir);
        subdir.addFileElement(dirEmpty);
        subdir.addFileElement(buildFileElement("/target/dir1/subdir/", FILE_SHORT_2));
        return target;
    }
}
