package org.isma.dirmanager.backuper.synchronizer.logic;

import org.isma.core.ApplicationContext;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.backuper.AbstractBackupLogic;
import org.isma.dirmanager.backuper.FileTreeNodeBackuperVisitor;
import org.isma.dirmanager.backuper.IFileBackuper;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.synchronizer.FileSynchronisationMarkerElementVisitor;
import org.isma.dirmanager.backuper.synchronizer.FileSynchronizer;
import org.isma.dirmanager.gui.FileTreeNodeVisitor;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;

import javax.swing.tree.DefaultTreeModel;

public class SynchroLogic extends AbstractBackupLogic {

    public SynchroLogic(ApplicationContext<IDirConfiguration> context) {
        super(context);
    }


    @Override
    protected FileElementVisitor buildMarkerElementVisitor(DirElement sourceDir, DirElement targetDir) {
        return new FileSynchronisationMarkerElementVisitor(sourceDir, targetDir, logger);
    }


    @Override
    protected void backup() throws Exception {
        resetProgressBar(((BackuperDirElement)rootSourceDirElement).getUnsynchronizationCount());
        logger.log("Backuping...\n");
        DefaultTreeModel sourceTreeModel = (DefaultTreeModel)form.getSourceTree().getModel();
        DefaultTreeModel targetTreeModel = (DefaultTreeModel)form.getTargetTree().getModel();
        IFileBackuper fileBackuper = new FileSynchronizer(rootSourceDirectoryTreeNode.getFileElement(),
                                                          rootTargetDirectoryTreeNode.getFileElement(),
                                                          logger);
        FileTreeNodeVisitor visitor = new FileTreeNodeBackuperVisitor(fileBackuper,
                                                                      sourceTreeModel,
                                                                      targetTreeModel,
                                                                      rootTargetDirectoryTreeNode
        );
        rootSourceDirectoryTreeNode.accept(visitor);
        logger.log("End of backuping...\n");
    }
}
