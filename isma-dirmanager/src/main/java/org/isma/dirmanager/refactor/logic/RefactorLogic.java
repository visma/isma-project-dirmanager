package org.isma.dirmanager.refactor.logic;

import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.DirTreeLoader;
import org.isma.dirmanager.FileTreeNodeBuilder;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.EmptyNode;
import org.isma.dirmanager.logic.FileElementTreeModel;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.refactor.FileRefactoringMarker;
import org.isma.dirmanager.refactor.ForbiddenWordsEraser;
import org.isma.dirmanager.refactor.LowCaseExtensionRenamer;
import org.isma.dirmanager.refactor.RefactorListener;
import org.isma.dirmanager.refactor.gui.*;
import org.isma.dirmanager.util.DefaultLogger;
import org.isma.dirmanager.util.ILogger;
import org.isma.guitoolkit.GuiUtils;
import org.isma.guitoolkit.util.JTreeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class RefactorLogic extends AbstractDirManagerLogic<RefactorForm> implements RefactorListener {
    private ILogger logger = new DefaultLogger();
    private FileTreeNodeBuilder refactorFileTreeNodeBuilder
          = new FileTreeNodeBuilder(new RefactorableFileTreeNodeFactory());

    private DirElement resultDirElement;

    private DefaultTreeModel resultTreeModel;

    private DirectoryTreeNode resultDirNode;
    private FileRefactoringMarker fileRenamer;

    private int refactoringToDoCount = 0;


    public RefactorLogic(IDirConfiguration configuration) {
        super(configuration);
        initRules();
        initForm();
    }


    @Override
    protected RefactorForm buildForm() {
        return new RefactorForm();
    }


    private void initRules() {
        //TODO initialiser la liste ailleurs, c'est crade la...
        List<String> forbiddenWords = new ArrayList<String>();
        forbiddenWords.add("dvdrip");
        fileRenamer = new FileRefactoringMarker(new LowCaseExtensionRenamer(),
                                                new ForbiddenWordsEraser(forbiddenWords));
    }


    private void initForm() {
        addListeners();
        synchronizeScrollPanes();
        resultTreeModel = new FileElementTreeModel(new EmptyNode());
        form.getActualTree().setModel(resultTreeModel);
        form.getResultTree().setModel(resultTreeModel);
        form.getActualTree().setCellRenderer(new FileRefactoringActualTreeCellRenderer());
        form.getResultTree().setCellRenderer(new FileRefactoringResultTreeCellRenderer());
    }


    private void synchronizeScrollPanes() {
        form.getActualScrollPane()
              .getVerticalScrollBar()
              .setModel(form.getResultScrollPane().getVerticalScrollBar().getModel());
    }


    private void addListeners() {
        GuiUtils.plugDirectoryChooser(form.getMainPanel(), form.getBrowseButton(), form.getDirectoryTextField());
        form.getActualTree().addTreeExpansionListener(new TwinTreeExpansionListener(form.getResultTree()));
        form.getResultTree().addTreeExpansionListener(new TwinTreeExpansionListener(form.getActualTree()));
    }


    private void simulation() throws Exception {
        markNewNames();
        showMessageDialog(form.getMainPanel(),
                          refactoringToDoCount == 0 ? "No malformated files found" :
                          refactoringToDoCount + " malformated files found");
    }


    private void rename() throws Exception {
        FileElementRefactoringVisitor visitor = new FileElementRefactoringVisitor(resultDirNode,
                                                                                  resultTreeModel);
        resultDirNode.accept(visitor);
        refactoringToDoCount = 0;
    }


    private void markNewNames() throws Exception {
        FileElementRefactoringMarkerVisitor visitor = new FileElementRefactoringMarkerVisitor(this,
                                                                                              fileRenamer,
                                                                                              resultTreeModel);
        resultDirNode.accept(visitor);
    }


    private void load() throws Exception {
        refactoringToDoCount = 0;
        loadDirectories();
        initTrees();
        simulation();
    }


    private void initTrees() throws Exception {
        logger.log("Creation des arborescences ...\n");
        resultDirNode = refactorFileTreeNodeBuilder.buildRootFileTreeNode(resultDirElement);
        logger.log("Creation des arborescences terminée.\n");
        resultTreeModel = new FileElementTreeModel(resultDirNode);
        form.getActualTree().setModel(resultTreeModel);
        form.getResultTree().setModel(resultTreeModel);
    }


    private void loadDirectories() throws Exception {
        DirTreeLoader treeLoader = new DirTreeLoader(new RefactorableFileFactory());

        String rootPath = form.getDirectoryTextField().getText();
        File root = new File(rootPath);
        resultDirElement = treeLoader.loadDirTree(root);
    }


    public void existRefactoring() {
        refactoringToDoCount++;
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
    public void onRun() throws Exception {
        rename();
    }


    @Override
    public void onExpand() {
        JTreeUtils.expandAll(form.getActualTree());
        JTreeUtils.expandAll(form.getResultTree());
    }


    @Override
    public void onCollapse() {
        JTreeUtils.collapseAll(form.getActualTree());
        JTreeUtils.collapseAll(form.getResultTree());
    }


    @Override
    public boolean canRun() {
        return true;
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
