package org.isma.dirmanager.clonefinder.model;

public class CloneRootDirElement extends CloneDirElement {
    @Override
    public String getDetail() {
        return getName();
    }

    @Override
    public String getName() {
        return "root";
    }
}
