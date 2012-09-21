package org.isma.dirmanager.refactor.gui;

import org.isma.dirmanager.gui.AbstractDirManagerTreeCellRenderer;
import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.refactor.model.Refactorable;

import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.GREEN;

public class FileRefactoringResultTreeCellRenderer extends AbstractDirManagerTreeCellRenderer {

    @Override
    protected Color getForegroundColor(AbstractFileTreeNode fileTreeNode) {
        Refactorable fileElement = (Refactorable)fileTreeNode.getFileElement();
        if (fileElement.isRefactored()) {
            return GREEN.darker();
        }
        else if (fileElement.isEligible()) {
            return Color.BLUE;
        }
        else {
            return BLACK;
        }
    }
}
