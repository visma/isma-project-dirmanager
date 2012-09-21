package org.isma.dirmanager;

import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.util.DefaultLogger;
import org.isma.dirmanager.util.ILogger;

import java.io.File;

public class DirTreeLoader {
    private IFileFactory fileFactory;
    private ILogger logger;


    public DirTreeLoader() {
        this(new DefaultFileFactory(), new DefaultLogger());
    }


    public DirTreeLoader(IFileFactory fileFactory) {
        this(fileFactory, new DefaultLogger());
    }


    public DirTreeLoader(IFileFactory fileFactory, ILogger logger) {
        this.fileFactory = fileFactory;
        this.logger = logger;
    }


    public DirElement loadDirTree(File directory) throws Exception {
        DirElement rootDir = fileFactory.buildDirectory(directory);
        loadTree(rootDir);
        return rootDir;
    }


    private void loadTree(DirElement rootDirElement) throws Exception {
        File[] files = rootDirElement.getFile().listFiles();
        if (files == null){
            logger.log("possible I/O error");
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                logger.log("loading '%s'\n", file.getAbsolutePath());
                DirElement dirElement = fileFactory.buildDirectory(file);
                rootDirElement.addFileElement(dirElement);
                loadTree(dirElement);
            }
            else {
                rootDirElement.addFileElement(fileFactory.buildFile(file));
            }
        }
    }
}
