package org.isma.dirmanager.clonefinder.gui;

import org.isma.dirmanager.gui.AbstractDirManagerTreeCellRenderer;
import org.isma.dirmanager.gui.AbstractFileTreeNode;

import java.awt.*;

import static java.awt.Color.BLACK;

public class CloneFileTreeCellRenderer extends AbstractDirManagerTreeCellRenderer {

    @Override
    protected Color getForegroundColor(AbstractFileTreeNode fileTreeNode) {
        return BLACK;
    }


    @Override
    protected String getText(AbstractFileTreeNode fileTreeNode) {
        return fileTreeNode.getFileElement().getDetail();
    }
}
