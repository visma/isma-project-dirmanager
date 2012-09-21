package org.isma.dirmanager.backuper.synchronizer;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.dirmanager.util.ILogger;

public class FileSynchronisationMarkerElementVisitor implements FileElementVisitor {
    private FileSynchronisationMarker fileSynchronisationMarker;


    public FileSynchronisationMarkerElementVisitor(DirElement sourceRootDirElement,
                                                   DirElement targetRootDirElement,
                                                   ILogger logger) {
        this.fileSynchronisationMarker = new FileSynchronisationMarker(sourceRootDirElement,
                                                                       targetRootDirElement, logger);
    }


    public void visit(AbstractFileElement sourcefileElement) throws Exception {
        checkUnsynchronisation(sourcefileElement);

        if (sourcefileElement instanceof DirElement) {
            for (AbstractFileElement subFileElement : ((DirElement)sourcefileElement).getElements()) {
                visit(subFileElement);
            }
        }
    }


    private void checkUnsynchronisation(AbstractFileElement sourceFileElement) throws Exception {
        fileSynchronisationMarker.markSynchronisation(sourceFileElement);
    }
}
