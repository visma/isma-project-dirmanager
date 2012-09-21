package org.isma.dirmanager.clonefinder.logic;

import org.isma.dirmanager.gui.AbstractFileTreeNode;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.FileTreeNode;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.guitoolkit.LogicActionListener;
import org.isma.guitoolkit.error.ErrorHandler;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import static java.lang.String.format;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static org.isma.utils.io.FileUtils.forceDeleteIfExists;
import static org.isma.utils.io.FileUtils.suppressBackSlashes;

public class CloneFileMouseListener extends MouseAdapter {
    public static final String TITLE_CONFIRMATION_DIALOG = "Clone Eradication";


    @Override
    public void mouseClicked(final MouseEvent e) {
        try {
            doDeleteClone(e);
        }
        catch (Exception e1) {
            ErrorHandler.handleException(e1);
        }
    }


    private void doDeleteClone(final MouseEvent mouseEvent) {
        final JTree tree = (JTree)mouseEvent.getSource();
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem menuItem = new JMenuItem("Kill others ?");
            menuItem.addActionListener(new LogicActionListener() {
                @Override
                protected void doAction() throws Exception {
                    thereCanBeOnlyOne(tree, mouseEvent);
                }
            });
            menu.add(menuItem);
            menu.show(tree, mouseEvent.getX(), mouseEvent.getY());
        }
    }


    private void thereCanBeOnlyOne(JTree tree, MouseEvent me) throws IOException {
        DefaultTreeModel treeModel = (DefaultTreeModel)tree.getModel();
        TreePath tp = tree.getPathForLocation(me.getX(), me.getY());
        if (tp == null) {
            return;
        }
        AbstractFileTreeNode fileTreeNode = (AbstractFileTreeNode)tp.getLastPathComponent();
        AbstractFileElement fileElement = fileTreeNode.getFileElement();
        if (!fileElement.isDirectory()) {
            String message = format("Keep only '%s' ?", suppressBackSlashes(fileElement.getFile().getAbsolutePath()));
            if (showConfirmDialog(tree, message, TITLE_CONFIRMATION_DIALOG, JOptionPane.OK_CANCEL_OPTION)
                == YES_OPTION) {
                thereCanBeOnlyOne(fileTreeNode, treeModel);
            }
        }
    }


    private void thereCanBeOnlyOne(AbstractFileTreeNode fileTreeNode, DefaultTreeModel treeModel) throws IOException {
        DirectoryTreeNode cloneDirectory = (DirectoryTreeNode)fileTreeNode.getParent();
        DirectoryTreeNode cloneRootDirectory = (DirectoryTreeNode)cloneDirectory.getParent();

        Enumeration children = cloneDirectory.children();
        while (children.hasMoreElements()) {
            FileTreeNode cloneNode = (FileTreeNode)children.nextElement();
            if (!cloneNode.equals(fileTreeNode)) {
                System.out.printf("suppression de %s\n", cloneNode.getFileElement().getFile().getAbsolutePath());
                delete(cloneNode);
            }
        }
        cloneRootDirectory.deleteChild(cloneDirectory, treeModel);
    }


    private void delete(FileTreeNode cloneNode) throws IOException {
        File cloneFile = cloneNode.getFileElement().getFile();
        forceDeleteIfExists(cloneFile);
        //TODO idée à voir : effacer le fichier et l'arborescence parente tant qu'elle est vide de fichiers...
    }
}

