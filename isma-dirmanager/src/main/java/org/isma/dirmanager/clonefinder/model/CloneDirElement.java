package org.isma.dirmanager.clonefinder.model;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;

public class CloneDirElement extends DirElement {

    public CloneDirElement() {
        super(null);
    }


    @Override
    public String getDetail() {
        AbstractFileElement aClone = getElements().get(0);
        String fileName = getShortestCloneName();
        return String.format("%s -- Size : %s Kb", fileName, (aClone.getFile().length() / 1024));
    }


    private String getShortestCloneName() {
        String fileName = null;
        for (AbstractFileElement clone : getElements()) {
            if (fileName == null) {
                fileName = clone.getName();
            }
            else if (clone.getName().length() < fileName.length()) {
                fileName = clone.getName();
            }
        }
        return fileName;
    }
}
