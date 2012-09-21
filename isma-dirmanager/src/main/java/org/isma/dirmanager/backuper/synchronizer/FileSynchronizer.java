package org.isma.dirmanager.backuper.synchronizer;

import org.isma.dirmanager.backuper.IFileBackuper;
import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.util.IStepLogger;

import java.io.File;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileSynchronizer implements IFileBackuper {
    private DirElement sourceRootDirElement;
    private DirElement targetRootDirElement;
    private IStepLogger logger;
    public static final String LOG_MESSAGE = "synchro [%s] -> %s\n";


    public FileSynchronizer(DirElement sourceRootDirElement,
                            DirElement targetRootDirElement,
                            IStepLogger logger) {
        this.sourceRootDirElement = sourceRootDirElement;
        this.targetRootDirElement = targetRootDirElement;
        this.logger = logger;
    }


    public AbstractFileElement backup(AbstractFileElement sourceFileElement, int childIndex) throws Exception {
        String targetRootPath = targetRootDirElement.getFile().getAbsolutePath();
        String sourceRelativePathFile = sourceRootDirElement.getFileElementRelativePath(sourceFileElement);
        String targetAbsolutePathFile = targetRootPath + "\\" + sourceRelativePathFile;

        File targetFile = new File(targetAbsolutePathFile);
        if (targetFile.exists()) {
            logger.log(LOG_MESSAGE, "NO", sourceFileElement.getFile().getAbsolutePath());
            return null;
        }
        else if (sourceFileElement.isDirectory()) {
            return backupDir(sourceFileElement, childIndex, targetFile);
        }
        else {
            return backupFile(sourceFileElement, childIndex, targetFile);
        }
    }


    private AbstractFileElement backupDir(AbstractFileElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        targetFile.mkdir();
        ((BackupableFileElement)sourceFileElement).setSynchronized(true);
        logger.log(LOG_MESSAGE, "NEW DIR", sourceFileElement.getFile().getAbsolutePath());
        BackuperDirElement res = new BackuperDirElement(targetFile, true, true);
        targetRootDirElement.addChild(res, childIndex);
        return res;
    }


    private AbstractFileElement backupFile(AbstractFileElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        copyFile(sourceFileElement.getFile(), targetFile);
        ((BackupableFileElement)sourceFileElement).setSynchronized(true);
        logger.logStep(LOG_MESSAGE, "NEW FILE", sourceFileElement.getFile().getAbsolutePath());
        BackuperFileElement res = new BackuperFileElement(targetFile, true, true);
        targetRootDirElement.addChild(res, childIndex);
        return res;
    }
}
