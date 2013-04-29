package org.isma.dirmanager.backuper.model;

//TODO trouver un truc pour plus perdre de temps a caster ça en AbstractFileElement, c'est débile
public interface BackupableFileElement {

    boolean isSynchronized();

    boolean isNew();

    void setSynchronized(boolean aSynchronized);

    String getName();

}
