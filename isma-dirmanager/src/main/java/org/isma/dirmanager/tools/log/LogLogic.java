package org.isma.dirmanager.tools.log;

import org.isma.dirmanager.DirTreeLoader;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.dirmanager.tools.FileElementPrinterVisitor;
import org.isma.dirmanager.tools.FileLogger;
import org.isma.utils.ClipBoardUtils;

import javax.swing.*;
import java.io.File;

import static org.isma.utils.io.FileUtils.forceDeleteIfExists;
public class LogLogic {
    private static final String LOG_FILENAME = "dirmanager_treeStructure.txt";
    private final FileLogger logger = new FileLogger();
    private File tmpDir;


    public LogLogic(final File tmpDir) {
        this.tmpDir = tmpDir;
    }


    public void log(File dirToLog) throws Exception {
        File logFile = new File(tmpDir, LOG_FILENAME);
        forceDeleteIfExists(logFile);
        logger.open(logFile);
        DirElement dirElement = new DirTreeLoader().loadDirTree(dirToLog);
        FileElementVisitor visitor = new FileElementPrinterVisitor(logger);
        dirElement.accept(visitor);
        logger.close();
        copyPathToClipBoard(logFile);
    }


    private void copyPathToClipBoard(File virtualDirArchive) {
        ClipBoardUtils.copyToClipboard(virtualDirArchive.getAbsolutePath());
        JOptionPane.showMessageDialog(null,
                                      LOG_FILENAME + " path copied to clipboard",
                                      "log file created",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
}
