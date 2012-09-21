package org.isma.dirmanager.backuper;

import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.gui.AbstractDirManagerTreeCellRenderer;
import org.isma.dirmanager.gui.AbstractFileTreeNode;

import java.awt.*;

import static java.awt.Color.*;

public class FileBackuperTreeCellRenderer extends AbstractDirManagerTreeCellRenderer {
    @Override
    protected Color getForegroundColor(AbstractFileTreeNode fileTreeNode) {
        BackupableFileElement fileElement = (BackupableFileElement)fileTreeNode.getFileElement();
        if (fileElement.isNew()) {
            return GREEN.darker();
        }
        else if (fileElement.isSynchronized()) {
            return BLACK;
        }
        else {
            return BLUE;
        }
    }
}
