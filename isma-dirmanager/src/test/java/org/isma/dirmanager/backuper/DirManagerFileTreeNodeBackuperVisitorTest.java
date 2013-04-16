package org.isma.dirmanager.backuper;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.FileTreeNodeBuilder;
import org.isma.dirmanager.IFileTestCaseFactory;
import org.isma.dirmanager.backuper.drainer.FileDrainer;
import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNode;
import org.isma.dirmanager.logic.FileElementTreeModel;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.DefaultLogger;

public class DirManagerFileTreeNodeBackuperVisitorTest extends AbstractDirManagerFileTestCase {
    private FileTreeNodeBuilder fileTreeNodeBuilder = new FileTreeNodeBuilder();


    @Override
    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new BackupableFileTestCaseFactory();
    }


    public void testVisit() throws Exception {
        //Init
        DirElement sourceRoot = buildSourceDir();
        DirElement targetRoot = buildTargetDir();
        IFileBackuper fileBackuper = new FileDrainer(sourceRoot, targetRoot, new DefaultLogger());

        DirectoryTreeNode sourceRootTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(sourceRoot);
        DirectoryTreeNode targetRootTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(targetRoot);
        FileElementTreeModel sourceTreeModel = new FileElementTreeModel(sourceRootTreeNode);
        FileElementTreeModel targetTreeModel = new FileElementTreeModel(targetRootTreeNode);

        FileTreeNodeBackuperVisitor visitor = new FileTreeNodeBackuperVisitor(fileBackuper,
                                                                              sourceTreeModel,
                                                                              targetTreeModel,
                                                                              targetRootTreeNode);
        //Run
        sourceRootTreeNode.accept(visitor);
        //ASSERTS
        // assert target dir
        assertEquals(3, targetRootTreeNode.getChildCount());
        DirectoryTreeNode drainedDir = (DirectoryTreeNode)targetRootTreeNode.getChildAt(0);
        assertEquals("_DRAINED_FILES", drainedDir.getFileElement().getName());
        FileTreeNode fileB = (FileTreeNode)targetRootTreeNode.getChildAt(1);
        assertEquals(FILE_SHORT_2, fileB.getFileElement().getName());
        DirectoryTreeNode sub2Dir = (DirectoryTreeNode)targetRootTreeNode.getChildAt(2);
        assertEquals("sub2", sub2Dir.getFileElement().getName());

        //assert target/_DRAINED_FILES dir
        assertEquals(1, drainedDir.getChildCount());
        DirectoryTreeNode sub1Dir = (DirectoryTreeNode)drainedDir.getChildAt(0);
        assertEquals("sub1", sub1Dir.getFileElement().getName());

        //assert target/_DRAINED_FILES/sub1 dir
        assertEquals(1, sub1Dir.getChildCount());
        FileTreeNode fileC = (FileTreeNode)sub1Dir.getChildAt(0);
        assertEquals(FILE_SHORT_TEN_CHAR, fileC.getFileElement().getName());

        //assert target/sub2
        assertEquals(1, sub2Dir.getChildCount());
        FileTreeNode fileA = (FileTreeNode)sub2Dir.getChildAt(0);
        assertEquals(FILE_SHORT_1, fileA.getFileElement().getName());
    }


    private DirElement buildTargetDir() throws Exception {
        DirElement targetRoot = buildDir("target");
        DirElement subDir = buildDir("target/sub2");
        FileElement fileA = buildFileElement("target/sub2/", FILE_SHORT_1);

        targetRoot.addChild(buildDir("target/_DRAINED_FILES"));
        targetRoot.addChild(subDir);
        subDir.addChild(fileA);
        return targetRoot;
    }


    private DirElement buildSourceDir() throws Exception {
        DirElement sourceRoot = buildDir("source");
        FileElement fileA = buildFileElement("source/", FILE_SHORT_1);
        FileElement fileB = buildFileElement("source/", FILE_SHORT_2);
        ((BackupableFileElement)fileB).setSynchronized(false);
        DirElement subDir = buildDir("source/sub1");
        FileElement fileC = buildFileElement("source/sub1/", FILE_SHORT_TEN_CHAR);
        ((BackupableFileElement)fileC).setSynchronized(false);

        sourceRoot.addChild(fileA);
        sourceRoot.addChild(fileB);
        sourceRoot.addChild(subDir);
        subDir.addChild(fileC);
        return sourceRoot;
    }
}
