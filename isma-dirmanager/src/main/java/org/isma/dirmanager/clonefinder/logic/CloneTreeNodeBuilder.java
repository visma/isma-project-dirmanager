package org.isma.dirmanager.clonefinder.logic;

import org.isma.dirmanager.FileTreeNodeBuilder;
import org.isma.dirmanager.clonefinder.CloneFinder;
import org.isma.dirmanager.clonefinder.Clones;
import org.isma.dirmanager.clonefinder.model.CloneDirElement;
import org.isma.dirmanager.clonefinder.model.CloneFileElement;
import org.isma.dirmanager.clonefinder.model.CloneRootDirElement;
import org.isma.dirmanager.gui.DirectoryTreeNode;
import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileComparator;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.ReverseComparator;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class CloneTreeNodeBuilder {
    private ReverseComparator<AbstractFileElement> comparator
          = new ReverseComparator<AbstractFileElement>(new CloneDirectoryElementComparator());
    private FileTreeNodeBuilder fileTreeNodeBuilder = new FileTreeNodeBuilder();


    CloneRootDirElement buildCloneRootDirElement(DirElement dirElement) {
        CloneFinder<FileElement> cloneFinder = new CloneFinder<FileElement>(new FileComparator());
        List<FileElement> fileElements = dirElement.getDepthFileElementList();
        List<Clones<FileElement>> cloneList = cloneFinder.extractClones(fileElements);

        CloneRootDirElement cloneRootDirectory = new CloneRootDirElement();
        for (Clones<FileElement> clones : cloneList) {
            CloneDirElement cloneDir = new CloneDirElement();
            cloneRootDirectory.addFileElement(cloneDir);
            for (FileElement cloneFile : clones.getClones()) {
                cloneDir.addFileElement(new CloneFileElement(cloneFile.getFile()));
            }
        }
        List<AbstractFileElement> cloneDirectories = cloneRootDirectory.getElements();
        sort(cloneDirectories, comparator);
        return cloneRootDirectory;
    }


    public DirectoryTreeNode buildCloneTree(DirElement dirElement) {
        CloneRootDirElement cloneRootDirectory = buildCloneRootDirElement(dirElement);
        return fileTreeNodeBuilder.buildRootFileTreeNode(cloneRootDirectory);
    }


    private static class CloneDirectoryElementComparator implements Comparator<AbstractFileElement> {

        public int compare(AbstractFileElement o1, AbstractFileElement o2) {
            if (!o1.isDirectory() || !o2.isDirectory()) {
                throw new RuntimeException(
                      getClass().getName() + " : ce comparateur ne doit être utilisé qu'avec des repertoires");
            }
            DirElement dir1 = (DirElement)o1;
            DirElement dir2 = (DirElement)o2;
            long sizeDiff = dir1.getElements().get(0).getFile().length() - dir2.getElements()
                  .get(0)
                  .getFile()
                  .length();
            if (sizeDiff > 1) {
                return 1;
            }
            else if (sizeDiff < 1) {
                return -1;
            }
            return 0;
        }
    }
}
