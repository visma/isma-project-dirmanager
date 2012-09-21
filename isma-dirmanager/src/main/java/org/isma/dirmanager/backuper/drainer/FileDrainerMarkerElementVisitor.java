package org.isma.dirmanager.backuper.drainer;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.dirmanager.util.ILogger;

public class FileDrainerMarkerElementVisitor implements FileElementVisitor {
    private FileDrainerMarker fileDrainerMarker;


    public FileDrainerMarkerElementVisitor(DirElement sourceRootDirElement,
                                           DirElement targetRootDirElement,
                                           ILogger logger) {
        this.fileDrainerMarker = new FileDrainerMarker(sourceRootDirElement, targetRootDirElement, logger);
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
        fileDrainerMarker.markUnsynchronisation(sourceFileElement);
    }
}
