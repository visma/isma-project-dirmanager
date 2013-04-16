package org.isma.dirmanager.backuper.drainer.logic;

import org.isma.core.ApplicationContext;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.backuper.AbstractBackupLogic;
import org.isma.dirmanager.backuper.FileTreeNodeBackuperVisitor;
import org.isma.dirmanager.backuper.IFileBackuper;
import org.isma.dirmanager.backuper.drainer.FileDrainer;
import org.isma.dirmanager.backuper.drainer.FileDrainerMarkerElementVisitor;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.gui.FileTreeNodeVisitor;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;

import javax.swing.tree.DefaultTreeModel;
import java.io.File;

public class DrainerLogic extends AbstractBackupLogic {
    public static final String DRAIN_DIRNAME = "_DRAINED_FILES";


    public DrainerLogic(ApplicationContext<IDirConfiguration> context) {
        super(context);
    }


    @Override
    protected FileElementVisitor buildMarkerElementVisitor(DirElement sourceDir, DirElement targetDir) {
        return new FileDrainerMarkerElementVisitor(sourceDir, targetDir, logger);
    }


    @Override
    protected void loadDirectories() throws Exception {
        super.loadDirectories();
        createDrainedFilesDir(rootTargetDirElement);
    }


    private void createDrainedFilesDir(DirElement targetRoot) throws Exception {
        logger.log("Creating drained directory...");
        String targetRootPath = targetRoot.getFile().getAbsolutePath();
        String targetDrainedRootPath = targetRootPath + "/" + DRAIN_DIRNAME + "/";
        File dir = new File(targetDrainedRootPath);
        if (dir.exists()) {
            return;
        }
        dir.mkdir();
        BackuperDirElement drainedDir = new BackuperDirElement(dir, true, true);
        targetRoot.addChild(drainedDir, 0);
    }


    //TODO faire un buildFileBackuper et remetter le reste dans Abstract si possible
    @Override
    protected void backup() throws Exception {
        logger.log("Backuping...\n");
        DefaultTreeModel sourceTreeModel = (DefaultTreeModel)form.getSourceTree().getModel();
        DefaultTreeModel targetTreeModel = (DefaultTreeModel)form.getTargetTree().getModel();
        IFileBackuper fileDrainer = new FileDrainer(rootSourceDirectoryTreeNode.getFileElement(),
                                                    rootTargetDirectoryTreeNode.getFileElement(),
                                                    logger);
        FileTreeNodeVisitor visitor = new FileTreeNodeBackuperVisitor(fileDrainer,
                                                                      sourceTreeModel,
                                                                      targetTreeModel,
                                                                      rootTargetDirectoryTreeNode
        );
        rootSourceDirectoryTreeNode.accept(visitor);
        logger.log("End of backuping...\n");
    }
}
