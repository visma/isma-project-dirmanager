package org.isma.dirmanager.clonefinder.logic;

import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.DirTreeLoader;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.clonefinder.gui.CloneFileTreeCellRenderer;
import org.isma.dirmanager.clonefinder.gui.CloneFinderForm;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.EmptyNode;
import org.isma.dirmanager.model.DirElement;
import org.isma.guitoolkit.util.JTreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;

import static org.isma.guitoolkit.GuiUtils.plugDirectoryChooser;

public class CloneFinderLogic extends AbstractDirManagerLogic<CloneFinderForm> {
    private JTree tree;
    private DefaultTreeModel treeModel;


    public CloneFinderLogic(IDirConfiguration configuration) {
        super(configuration);
        tree = form.getDirectoryTree();
        initForm();
    }


    private void initForm() {
        addListeners();
        treeModel = new DefaultTreeModel(new EmptyNode());
        tree.setModel(treeModel);
        tree.setCellRenderer(new CloneFileTreeCellRenderer());
        tree.addMouseListener(new CloneFileMouseListener());
    }


    @Override
    protected CloneFinderForm buildForm() {
        return new CloneFinderForm();
    }


    private void addListeners() {
        plugDirectoryChooser(form.getMainPanel(), form.getBrowseButton(), form.getDirectoryTextField());
    }


    private void load() throws Exception {
        File directory = new File(form.getDirectoryTextField().getText());
        DirElement rootDirElement = new DirTreeLoader().loadDirTree(directory);
        DirectoryTreeNode rootDirectoryTreeNode = new CloneTreeNodeBuilder().buildCloneTree(rootDirElement);
        treeModel = new DefaultTreeModel(rootDirectoryTreeNode);
        tree.setModel(treeModel);
    }


    public JPanel getGui() {
        return form.getMainPanel();
    }


    @Override
    public void onConfigurationChange() {
        form.getDirectoryTextField().setText(conf.getSourceFilePath());
    }


    @Override
    public void onLoad() throws Exception {
        if (checkDirectory(form.getDirectoryTextField().getText())) {
            load();
        }
    }


    @Override
    public void onExpand() {
        JTreeUtils.expandAll(form.getDirectoryTree());
    }


    @Override
    public void onCollapse() {
        JTreeUtils.collapseAll(form.getDirectoryTree());
    }


    @Override
    public boolean canExpand() {
        return true;
    }


    @Override
    public boolean canCollapse() {
        return true;
    }


    @Override
    public boolean canLoad() {
        return true;
    }
}
