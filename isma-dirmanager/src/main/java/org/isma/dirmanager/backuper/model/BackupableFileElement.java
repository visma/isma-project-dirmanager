package org.isma.dirmanager.backuper.model;

//TODO trouver un truc pour plus perdre de temps a caster �a en AbstractFileElement, c'est d�bile
public interface BackupableFileElement {

    boolean isSynchronized();

    boolean isNew();

    void setSynchronized(boolean aSynchronized);

    String getName();

}
