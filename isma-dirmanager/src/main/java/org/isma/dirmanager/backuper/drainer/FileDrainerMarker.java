package org.isma.dirmanager.backuper.drainer;

import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileMap;
import org.isma.dirmanager.util.ILogger;

import java.io.File;

public class FileDrainerMarker {
    private DirElement sourceRootDirElement;
    private DirElement targetRootDirElement;
    private FileMap targetFileMap;
    private ILogger logger;


    public FileDrainerMarker(DirElement sourceRootDirElement,
                             DirElement targetRootDirElement,
                             ILogger logger) {
        this.sourceRootDirElement = sourceRootDirElement;
        this.targetRootDirElement = targetRootDirElement;
        this.targetFileMap = new FileMap(targetRootDirElement);
        this.logger = logger;
    }


    public void markUnsynchronisation(AbstractFileElement sourceFileElement) throws Exception {
        String targetRootPath = targetRootDirElement.getFile().getAbsolutePath();
        String sourceRelativePathFile = sourceRootDirElement.getFileElementRelativePath(sourceFileElement);
        String targetAbsolutePathFile = targetRootPath + "\\" + sourceRelativePathFile;

        boolean synchro = isSynchro(sourceFileElement, targetAbsolutePathFile);
        ((BackupableFileElement)sourceFileElement).setSynchronized(synchro);
        logger.log("markSynchronisation '%s' on %s\n", synchro, sourceFileElement.getFile().getAbsolutePath());
    }


    private boolean isSynchro(AbstractFileElement sourceFileElement, String targetAbsolutePathFile) {
        File targetFile = new File(targetAbsolutePathFile);
        if (targetFile.exists()) {
            return true;
        }
        if (sourceFileElement.isDirectory()) {
            return true;
        }
        return targetFileMap.exists(sourceFileElement);
    }
}
