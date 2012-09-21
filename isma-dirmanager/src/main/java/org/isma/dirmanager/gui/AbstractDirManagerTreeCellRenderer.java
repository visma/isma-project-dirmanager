package org.isma.dirmanager.gui;

import org.isma.guitoolkit.menus.actions.GuiToolkitIconEnum;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public abstract class AbstractDirManagerTreeCellRenderer implements TreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        AbstractFileTreeNode node = (AbstractFileTreeNode)value;
        if (node instanceof EmptyNode) {
            return new JLabel("empty");
        }
        String text = getText(node);
        Icon icon = getIcon(node, expanded);
        return buildLabel(text, icon, node, hasFocus);
    }


    private JLabel buildLabel(String text, Icon icon, AbstractFileTreeNode fileTreeNode, boolean hasFocus) {
        JLabel component = new JLabel(text, icon, JLabel.HORIZONTAL);
        component.setBackground(Color.WHITE);
        component.setForeground(getForegroundColor(fileTreeNode));
        if (hasFocus) {
            Color bgColor = component.getBackground();
            Color fgColor = component.getForeground();
            component.setBackground(fgColor);
            component.setForeground(bgColor);
            component.setOpaque(true);
        }
        return component;
    }


    protected abstract Color getForegroundColor(AbstractFileTreeNode fileTreeNode);


    protected String getText(AbstractFileTreeNode fileTreeNode) {
        return fileTreeNode.getFileElement().getName();
    }


    private Icon getIcon(AbstractFileTreeNode node, boolean expanded) {
        Icon icon;
        if (node instanceof EmptyNode) {
            return DirManagerIconEnum.QUESTON_FILE.getImageIcon();
        }
        if (node.isLeaf()) {
            icon = FileIconEnum.getIcon(node.getFileElement().getFile());
        }
        else if (expanded) {
            icon = GuiToolkitIconEnum.FOLDER_EXPANDED.getImageIcon();
        }
        else {
            icon = GuiToolkitIconEnum.FOLDER_COLLAPSED.getImageIcon();
        }
        return icon;
    }
}
