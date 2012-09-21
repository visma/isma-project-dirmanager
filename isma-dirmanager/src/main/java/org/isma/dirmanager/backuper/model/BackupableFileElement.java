package org.isma.dirmanager.backuper.model;

//TODO trouver un truc pour plus perdre de temps a caster ça en AbstractFileElement, c'est débile
public interface BackupableFileElement {

    public boolean isSynchronized();

    public boolean isNew();

    public void setSynchronized(boolean aSynchronized);

    public String getName();

}
