package org.isma.dirmanager.backuper.drainer;

import org.apache.commons.io.FileUtils;
import org.isma.dirmanager.backuper.IFileBackuper;
import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.backuper.model.BackuperDirElement;
import org.isma.dirmanager.backuper.model.BackuperFileElement;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.model.FileMap;
import org.isma.dirmanager.util.ILogger;

import java.io.File;

import static org.apache.commons.lang.StringUtils.isBlank;

public class FileDrainer implements IFileBackuper {
    private DirElement sourceRootDirElement;
    private DirElement targetRootDirElement;
    private ILogger logger;
    public static final String LOG_MESSAGE = "synchro [%s] -> %s\n";
    public static final String DRAIN_DIRNAME = "_DRAINED_FILES";
    private FileMap targetFileMap;
    private final String targetRootPath;
    private final String targetDrainedRootPath;


    public FileDrainer(DirElement sourceRootDirElement, DirElement targetRootDirElement, ILogger logger) {
        this.sourceRootDirElement = sourceRootDirElement;
        this.targetRootDirElement = targetRootDirElement;
        this.logger = logger;
        this.targetFileMap = new FileMap(targetRootDirElement);

        targetRootPath = targetRootDirElement.getFile().getAbsolutePath();
        targetDrainedRootPath = targetRootPath + "\\" + DRAIN_DIRNAME + "\\";
    }


    public AbstractFileElement backup(AbstractFileElement sourceFileElement, int childIndex) throws Exception {
        if (sourceFileElement.equals(sourceRootDirElement)) {
            return null;
        }
        String sourceRelativePathFile = sourceRootDirElement.getFileElementRelativePath(sourceFileElement);
        String targetAbsolutePathFile = targetRootPath + sourceRelativePathFile;

        File targetFile = new File(targetAbsolutePathFile);
        boolean isSourceAlreadySynchronized = ((BackupableFileElement)sourceFileElement).isSynchronized();

        if (!sourceFileElement.isDirectory() && (isSourceAlreadySynchronized
                                                 || targetFileMap.exists(sourceFileElement))) {
            logger.log(LOG_MESSAGE, "FILE_EXISTS", sourceFileElement.getFile().getAbsolutePath());
            return null;
        }
        else if (sourceFileElement.isDirectory() && (isSourceAlreadySynchronized
                                                     || new File(targetAbsolutePathFile).exists())) {
            logger.log(LOG_MESSAGE, "DIR_EXISTS", sourceFileElement.getFile().getAbsolutePath());
            return null;
        }
        else if (sourceFileElement.isDirectory()) {
            if (!isBlank(sourceRelativePathFile)) {
                return backupDirectory((BackuperDirElement)sourceFileElement, childIndex, targetFile);
            }
            return null;
        }
        else {
            return backupFile((BackuperFileElement)sourceFileElement, childIndex, targetFile);
        }
    }


    private AbstractFileElement backupDirectory(BackuperDirElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        File parentFile = targetFile.getParentFile();
        boolean existsParentDirectory = parentFile.exists();
        String parentPath = parentFile.getAbsolutePath();
        if (existsParentDirectory && !parentPath.equals(targetRootPath)) {
            return synchronizeDir(sourceFileElement, childIndex, targetFile);
        }
        else {
            return doDrain(sourceFileElement);
        }
    }


    private AbstractFileElement backupFile(BackuperFileElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        boolean existsParentDirectory = targetFile.getParentFile().exists();
        AbstractFileElement syncFile;
        if (existsParentDirectory) {
            syncFile = synchronizeFile(sourceFileElement, childIndex, targetFile);
        }
        else {
            syncFile = doDrain(sourceFileElement);
        }
        targetFileMap.putNew((FileElement)syncFile);
        return syncFile;
    }


    private AbstractFileElement doDrain(AbstractFileElement sourceFileElement)
          throws Exception {
        String sourceRelativePathFile = sourceRootDirElement.getFileElementRelativePath(sourceFileElement);
        File targetFile = new File(targetDrainedRootPath + sourceRelativePathFile);
        if (sourceFileElement.isDirectory()) {
            return synchronizeDir((BackuperDirElement)sourceFileElement, -1, targetFile);
        }
        else {
            return synchronizeFile((BackuperFileElement)sourceFileElement, -1, targetFile);
        }
    }


    //TODO des copier coller avec FileSynchronizer (?)
    private BackuperFileElement synchronizeFile(BackuperFileElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        FileUtils.copyFile(sourceFileElement.getFile(), targetFile);
        sourceFileElement.setSynchronized(true);
        logger.log(LOG_MESSAGE, "NEW FILE", sourceFileElement.getFile().getAbsolutePath());
        BackuperFileElement res = new BackuperFileElement(targetFile, true, true);
        DirElement parentDir = (DirElement)targetRootDirElement.getFileElement(res.getFile()
                                                                                     .getParentFile()
                                                                                     .getAbsolutePath());
        parentDir.addChild(res);
        return res;
    }


    //TODO ya du copier coller avec FileSynchronizer
    private BackuperDirElement synchronizeDir(BackuperDirElement sourceFileElement, int childIndex, File targetFile)
          throws Exception {
        targetFile.mkdir();
        sourceFileElement.setSynchronized(true);
        logger.log(LOG_MESSAGE, "NEW DIR", sourceFileElement.getFile().getAbsolutePath());
        BackuperDirElement res = new BackuperDirElement(targetFile, true, true);
        DirElement parentDir = (DirElement)targetRootDirElement.getFileElement(res.getFile()
                                                                                     .getParentFile()
                                                                                     .getAbsolutePath());
        if (childIndex == -1) {
            parentDir.addChild(res);
        }
        else {
            parentDir.addChild(res, childIndex);
        }
        return res;
    }
}
