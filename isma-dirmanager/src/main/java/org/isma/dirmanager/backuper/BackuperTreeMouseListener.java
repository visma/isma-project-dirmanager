package org.isma.dirmanager.backuper;

import org.isma.dirmanager.backuper.actions.DeleteFileAction;
import org.isma.dirmanager.backuper.actions.OpenFileAction;
import org.isma.dirmanager.backuper.actions.RenameAction;
import org.isma.dirmanager.backuper.model.BackupableFileElement;
import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BackuperTreeMouseListener extends MouseAdapter {
    private DefaultTreeModel treeModel;
    private DefaultTreeModel otherTreeModel;
    private DirectoryTreeNode rootTreeNode;
    private DirectoryTreeNode otherRootTreeNode;


    public BackuperTreeMouseListener(DefaultTreeModel treeModel,
                                     DefaultTreeModel otherTreeModel,
                                     DirectoryTreeNode treeNode,
                                     DirectoryTreeNode otherTreeNode) {
        this.treeModel = treeModel;
        this.otherTreeModel = otherTreeModel;
        this.rootTreeNode = treeNode;
        this.otherRootTreeNode = otherTreeNode;
    }


    @Override
    public void mouseClicked(final MouseEvent e) {
        doHandleClick(e);
    }


    private void doHandleClick(final MouseEvent mouseEvent) {
        final JTree tree = (JTree)mouseEvent.getSource();
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            TreePath tp = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            if (tp == null) {
                return;
            }
            final AbstractFileTreeNode fileTreeNode = (AbstractFileTreeNode)tp.getLastPathComponent();
            final BackupableFileElement fileElement = (BackupableFileElement)fileTreeNode.getFileElement();
            JPopupMenu menu = buildPopupMenu(fileTreeNode, fileElement);
            menu.show(tree, mouseEvent.getX(), mouseEvent.getY());
        }
    }


    private JPopupMenu buildPopupMenu(AbstractFileTreeNode fileTreeNode, BackupableFileElement fileElement) {
        final String fileName = fileElement.getName();
        JPopupMenu menu = new JPopupMenu();

        JMenuItem openItem = new JMenuItem("Open '" + fileName + "'");
        JMenuItem renameItem = new JMenuItem("Rename '" + fileName + "'");
        JMenuItem deleteItem = new JMenuItem("Delete '" + fileName + "'");

        openItem.addActionListener(new OpenFileAction(fileElement));
        renameItem.addActionListener(new RenameAction(treeModel,
                                                      otherTreeModel,
                                                      rootTreeNode,
                                                      otherRootTreeNode,
                                                      fileTreeNode));
        deleteItem.addActionListener(new DeleteFileAction(treeModel,
                                                          otherTreeModel,
                                                          rootTreeNode,
                                                          otherRootTreeNode,
                                                          fileTreeNode));
        menu.add(openItem);
        menu.add(renameItem);
        menu.add(deleteItem);
        return menu;
    }
}
