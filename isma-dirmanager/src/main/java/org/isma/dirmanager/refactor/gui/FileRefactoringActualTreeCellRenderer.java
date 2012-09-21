package org.isma.dirmanager.refactor.gui;

import org.isma.dirmanager.gui.AbstractDirManagerTreeCellRenderer;
import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.refactor.model.Refactorable;

import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

public class FileRefactoringActualTreeCellRenderer extends AbstractDirManagerTreeCellRenderer {

    @Override
    protected Color getForegroundColor(AbstractFileTreeNode fileTreeNode) {
        Refactorable fileElement = (Refactorable)fileTreeNode.getFileElement();
        if (fileElement.isRefactored()){
            return Color.GRAY;
        }
        if (fileElement.isEligible() && !fileElement.isRefactored()) {
            return RED;
        }
        else {
            return BLACK;
        }
    }


    @Override
    protected String getText(AbstractFileTreeNode fileTreeNode) {
        Refactorable fileElement = (Refactorable)fileTreeNode.getFileElement();
        return fileElement.getOldName();
    }
}
