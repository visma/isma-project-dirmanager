package org.isma.dirmanager.backuper;

import org.isma.dirmanager.model.AbstractFileElement;

public interface IFileBackuper {

    public AbstractFileElement backup(AbstractFileElement fileElement, int childIndex) throws Exception;
}
