package org.isma.dirmanager.tools;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;

import java.io.File;
import java.io.IOException;

public class FileElementVirtualizeVisitor implements FileElementVisitor {
    private DirElement rootDirToVirtualize;
    private File virtualDir;


    public FileElementVirtualizeVisitor(DirElement rootDirToVirtualize, File virtualDir) {
        this.rootDirToVirtualize = rootDirToVirtualize;
        this.virtualDir = virtualDir;
        virtualDir.mkdir();
    }


    public void visit(AbstractFileElement fileElement) throws Exception {
        virtualize(fileElement);
        if (fileElement instanceof DirElement) {
            for (AbstractFileElement subFileElement : ((DirElement)fileElement).getElements()) {
                visit(subFileElement);
            }
        }
    }


    private void virtualize(AbstractFileElement fileElement) throws IOException {
        if (rootDirToVirtualize == fileElement) {
            return;
        }
        String absoluteVirtualDirPath = virtualDir.getAbsolutePath();
        String relativePath = rootDirToVirtualize.getFileElementRelativePath(fileElement);

        String filePathToVirtualize = absoluteVirtualDirPath + "\\" + relativePath;
        System.out.println("virtualize " + fileElement.getName() + " ...");
        if (fileElement.isDirectory()) {
            new File(filePathToVirtualize).mkdir();
        }
        else {
            new File(filePathToVirtualize).createNewFile();
        }
    }
}
