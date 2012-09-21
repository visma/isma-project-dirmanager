package org.isma.dirmanager.backuper.synchronizer;


import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.util.ILogger;

import java.io.File;

public class FileSynchronisationMarker {
    private DirElement sourceRootDirElement;
    private DirElement targetRootDirElement;
    private ILogger logger;

    public FileSynchronisationMarker(DirElement sourceRootDirElement,
                                     DirElement targetRootDirElement,
                                     ILogger logger) {
        this.sourceRootDirElement = sourceRootDirElement;
        this.targetRootDirElement = targetRootDirElement;
        this.logger = logger;
    }

    public void markSynchronisation(AbstractFileElement sourceFileElement) throws Exception {
        String targetRootPath = targetRootDirElement.getFile().getAbsolutePath();
        String sourceRelativePathFile = sourceRootDirElement.getFileElementRelativePath(sourceFileElement);
        String targetAbsolutePathFile = targetRootPath + "\\" + sourceRelativePathFile;

        File targetFile = new File(targetAbsolutePathFile);
        boolean synchro = targetFile.exists();
        ((BackupableFileElement) sourceFileElement).setSynchronized(synchro);
        logger.log("markSynchronisation '%s' on %s\n", synchro, sourceFileElement.getFile().getAbsolutePath());
    }
}
