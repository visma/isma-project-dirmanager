package org.isma.dirmanager.backuper;

import org.isma.core.ApplicationContext;
import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.DirTreeLoader;
import org.isma.dirmanager.FileTreeNodeBuilder;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.backuper.gui.BackuperForm;
import org.isma.dirmanager.backuper.gui.ProgressBarLogger;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.gui.EmptyNode;
import org.isma.dirmanager.logic.FileElementTreeModel;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.guitoolkit.util.JTreeUtils;

import javax.swing.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static org.isma.guitoolkit.GuiUtils.plugDirectoryChooser;

public abstract class AbstractBackupLogic extends AbstractDirManagerLogic<BackuperForm> {
    private ApplicationContext<IDirConfiguration> context;

    protected ProgressBarLogger logger = new ProgressBarLogger();

    protected DirectoryTreeNode rootSourceDirectoryTreeNode;
    protected DirectoryTreeNode rootTargetDirectoryTreeNode;

    private final FileTreeNodeBuilder fileTreeNodeBuilder = new FileTreeNodeBuilder();
    protected DirElement rootSourceDirElement;
    protected DirElement rootTargetDirElement;

    private DirTreeLoader treeLoader;


    protected AbstractBackupLogic(ApplicationContext<IDirConfiguration> context) {
        super(context.getConfiguration());
        this.context = context;
        this.treeLoader = new DirTreeLoader(new BackuperFileFactory(), logger);
        initForm();
        treeLoader = new DirTreeLoader(new BackuperFileFactory(), logger);
    }


    @Override
    protected BackuperForm buildForm() {
        return new BackuperForm();
    }


    public JComponent getGui() {
        return form.getMainPanel();
    }


    protected abstract FileElementVisitor buildMarkerElementVisitor(DirElement sourceDir, DirElement targetDir);


    private void loadSynchronisationState(DirElement dirSource, DirElement dirTarget) throws Exception {
        logger.log("Detection des desynchronisations sur %s\n", dirSource.getFile());
        dirSource.accept(buildMarkerElementVisitor(dirSource, dirTarget));
        logger.log("Detection des desynchronisations sur %s\n", dirTarget.getFile());
        dirTarget.accept(buildMarkerElementVisitor(dirTarget, dirSource));
        logger.log("Fin de la detection des desynchronisations\n");
    }


    protected abstract void backup() throws Exception;


    private void initForm() {
        addListeners();
        form.getSourceTree().setModel(new FileElementTreeModel(new EmptyNode()));
        form.getTargetTree().setModel(new FileElementTreeModel(new EmptyNode()));
        form.getSourceTree().setCellRenderer(new FileBackuperTreeCellRenderer());
        form.getTargetTree().setCellRenderer(new FileBackuperTreeCellRenderer());

        new JSliderInitiliazerTimer();
    }


    //TODO trouver une méthode plus académique...
    class JSliderInitiliazerTimer extends Timer {
        JSliderInitiliazerTimer() {
            super(true);
            schedule(new TimerTask() {
                @Override
                public void run() {
                    initializeJSliderDividerLocation();
                }
            }, 1000);
        }
    }


    private void initializeJSliderDividerLocation() {
        System.out.println("in");
        if (form.getMainPanel().getRootPane() != null) {
            System.out.println("in2");
            final int width = form.getMainPanel().getRootPane().getWidth();
            System.out.println("in2 width" + width);
            form.getSliderPane().setDividerLocation(width / 2);
        }
    }


    private void initTrees() throws Exception {
        logger.log("Creation des arborescences ...\n");
        rootSourceDirectoryTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(rootSourceDirElement);
        rootTargetDirectoryTreeNode = fileTreeNodeBuilder.buildRootFileTreeNode(rootTargetDirElement);
        logger.finish("Creation des arborescences terminée.\n");
        final FileElementTreeModel sourceTreeModel = new FileElementTreeModel(rootSourceDirectoryTreeNode);
        final FileElementTreeModel targetTreeModel = new FileElementTreeModel(rootTargetDirectoryTreeNode);
        form.getSourceTree().setModel(sourceTreeModel);
        form.getTargetTree().setModel(targetTreeModel);
        form.getSourceTree().addMouseListener(new BackuperTreeMouseListener(sourceTreeModel,
                                                                            targetTreeModel,
                                                                            rootSourceDirectoryTreeNode,
                                                                            rootTargetDirectoryTreeNode));
        form.getTargetTree().addMouseListener(new BackuperTreeMouseListener(targetTreeModel,
                                                                            sourceTreeModel,
                                                                            rootTargetDirectoryTreeNode,
                                                                            rootSourceDirectoryTreeNode));
    }


    private void addListeners() {
        plugDirectoryChooser(form.getMainPanel(), form.getSourceBrowseButton(), form.getSourceTextField());
        plugDirectoryChooser(form.getMainPanel(), form.getTargetBrowseButton(), form.getTargetTextField());
    }


    private void load() throws Exception {
        resetProgressBar(-1);

        loadDirectories();
        initTrees();

        logger.finish("Chargement des arborescences terminé");
        logger.getProgressBar().setIndeterminate(false);
    }


    protected void loadDirectories() throws Exception {
        File source = new File(form.getSourceTextField().getText());
        logger.log("Chargement de l'arborescence source : %s\n", source);
        rootSourceDirElement = treeLoader.loadDirTree(source);

        File target = new File(form.getTargetTextField().getText());
        logger.log("Chargement de l'arborescence cible : %s\n", target);
        rootTargetDirElement = treeLoader.loadDirTree(target);

        loadSynchronisationState(rootSourceDirElement, rootTargetDirElement);
    }


    protected void resetProgressBar(int expectedSteps) {
        boolean isIndeterminate = expectedSteps == -1;
        logger.getProgressBar().setIndeterminate(isIndeterminate);
        logger.reset(expectedSteps);
        form.getProgressBarPanel().remove(logger);
        form.getProgressBarPanel().add(logger);
    }


    @Override
    public void onConfigurationChange() {
        form.getSourceTextField().setText(conf.getSourceFilePath());
        form.getTargetTextField().setText(conf.getTargetFilePath());
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


    @Override
    public boolean canRun() {
        return true;
    }


    @Override
    public void onLoad() throws Exception {
        final String sourcePath = form.getSourceTextField().getText();
        final String targetPath = form.getTargetTextField().getText();
        if (checkDirectory(sourcePath) && checkDirectory(targetPath) && checkNoSubDirectory(sourcePath, targetPath)) {
            load();
        }
    }


    @Override
    public void onRun() throws Exception {
        backup();
    }


    @Override
    public void onExpand() {
        JTreeUtils.expandAll(form.getSourceTree());
        JTreeUtils.expandAll(form.getTargetTree());
    }


    @Override
    public void onCollapse() {
        JTreeUtils.collapseAll(form.getSourceTree());
        JTreeUtils.collapseAll(form.getTargetTree());
    }
}
