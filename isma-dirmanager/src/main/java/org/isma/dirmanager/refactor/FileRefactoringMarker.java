package org.isma.dirmanager.refactor;

import org.isma.dirmanager.model.AbstractFileElement;

public class FileRefactoringMarker {
    private RenamingLayer[] renamingLayers;

    public FileRefactoringMarker(RenamingLayer... renamingLayers) {
        this.renamingLayers = renamingLayers;
    }

    public String getNewName(AbstractFileElement fileElement) {
        if (fileElement.isDirectory()) {
            return null;
        }
        String originalFilename = fileElement.getFile().getName();
        String filename = originalFilename;
        for (RenamingLayer renamingLayer : renamingLayers) {
            filename = renamingLayer.rename(filename);
        }
        return originalFilename.equals(filename) ? null : filename;
    }
}
