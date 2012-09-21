package org.isma.dirmanager.tools.virtualize;

import org.isma.dirmanager.DirTreeLoader;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.dirmanager.tools.FileElementVirtualizeVisitor;
import org.isma.utils.ClipBoardUtils;
import org.isma.utils.zip.Zip;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static org.isma.utils.io.FileUtils.forceDeleteIfExists;
public class VirtualizeLogic {
    public static final String VIRTUAL_DIRNAME = "virtual_dir";
    public static final String VIRTUAL_DIR_ARCHIVE = VIRTUAL_DIRNAME + ".zip";
    private final File tmpDir;


    public VirtualizeLogic(final File tmpDir) {
        this.tmpDir = tmpDir;
    }


    public void virtualize(File dirToVirtualize) throws Exception {
        File virtualDir = new File(tmpDir, VIRTUAL_DIRNAME);
        File virtualDirArchive = new File(tmpDir, VIRTUAL_DIR_ARCHIVE);

        forceDeleteIfExists(virtualDir);
        forceDeleteIfExists(virtualDirArchive);

        DirElement dirElement = new DirTreeLoader().loadDirTree(dirToVirtualize);
        FileElementVisitor visitor = new FileElementVirtualizeVisitor(dirElement, virtualDir);
        dirElement.accept(visitor);

        createZip(virtualDirArchive, virtualDir);
        copyPathToClipBoard(virtualDirArchive);

        forceDeleteIfExists(virtualDir);
    }


    private void copyPathToClipBoard(File virtualDirArchive) {
        ClipBoardUtils.copyToClipboard(virtualDirArchive.getAbsolutePath());
        JOptionPane.showMessageDialog(null,
                                      VIRTUAL_DIR_ARCHIVE + " path copied to clipboard",
                                      "archive created",
                                      JOptionPane.INFORMATION_MESSAGE);
    }


    private void createZip(File destinationFile, File inputDirectory) throws IOException {
        new Zip().zip(inputDirectory.getAbsolutePath(), destinationFile.getAbsolutePath());
    }
}
