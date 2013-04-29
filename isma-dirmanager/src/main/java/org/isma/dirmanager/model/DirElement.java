package org.isma.dirmanager.model;

import org.isma.dirmanager.util.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class DirElement extends AbstractFileElement {
    private List<AbstractFileElement> elements = new ArrayList<AbstractFileElement>();


    public DirElement(File directory) {
        super(directory);
    }


    public void addFileElement(AbstractFileElement fileElement) {
        elements.add(fileElement);
    }


    public List<AbstractFileElement> getElements() {
        return elements;
    }


    @Override
    public boolean isDirectory() {
        return true;
    }


    @Override
    public String getDetail() {
        return format("%s --- Size : %s Mo, Files : %s",
                      getFile().getName(),
                      getDepthSize() / (1024 * 1024),
                      getDepthAmountFiles());
    }


    private int getDepthAmountFiles() {
        int count = 0;
        for (AbstractFileElement abstractFileElement : elements) {
            if (!abstractFileElement.isDirectory()) {
                count++;
            }
            else {
                count += ((DirElement)abstractFileElement).getDepthAmountFiles();
            }
        }
        return count;
    }


    private long getDepthSize() {
        int count = 0;
        for (AbstractFileElement abstractFileElement : elements) {
            if (!abstractFileElement.isDirectory()) {
                count += abstractFileElement.getFile().length();
            }
            else {
                count += ((DirElement)abstractFileElement).getDepthSize();
            }
        }
        return count;
    }


    public String getFileElementRelativePath(AbstractFileElement fileElement) {
        String fileAbsolutePath = fileElement.getFile().getAbsolutePath();
        return fileAbsolutePath.replace(getFile().getAbsolutePath(), "");
    }


    public List<FileElement> getDepthFileElementList() {
        List<FileElement> fileElements = new ArrayList<FileElement>();
        for (AbstractFileElement abstractFileElement : elements) {
            if (!abstractFileElement.isDirectory()) {
                fileElements.add((FileElement) abstractFileElement);
            }
            else {
                fileElements.addAll(((DirElement) abstractFileElement).getDepthFileElementList());
            }
        }
        return fileElements;
    }


    public void addChild(AbstractFileElement childElement, int index) {
        DirElement parent = findParent(childElement);
        parent.getElements().add(index, childElement);
    }


    public void addChild(AbstractFileElement childElement) {
        DirElement parent = findParent(childElement);
        parent.getElements().add(elements.size(), childElement);
    }


    private DirElement findParent(AbstractFileElement fileElement) {
        String parentPath = fileElement.getFile().getParentFile().getAbsolutePath();
        if (parentPath.equals(getFile().getAbsolutePath())) {
            return this;
        }
        for (AbstractFileElement abstractFileElement : getElements()) {
            if (!abstractFileElement.isDirectory()) {
                continue;
            }
            DirElement currDir = (DirElement)abstractFileElement;
            if (fileElement.getFile().getAbsolutePath().startsWith(currDir.getFile().getAbsolutePath() + "/")) {
                return currDir.findParent(fileElement);
            }
        }
        throw new RuntimeException(format("file %s is not a child of directory : %s !",
                                          fileElement.getFile(),
                                          getFile()));
    }


    public FileElement findChild(String filename) {
        List<FileElement> fileElementList = getDepthFileElementList();
        for (FileElement fileElement : fileElementList) {
            if (fileElement.getFile().getName().equals(filename)) {
                return fileElement;
            }
        }
        return null;
    }


    public AbstractFileElement getFileElement(String childPath) {
        childPath = FileHelper.windowsUnifyVolumePath(childPath);
        String currentPath = FileHelper.windowsUnifyVolumePath(getFile().getAbsolutePath());

        if (childPath.equals(currentPath)) {
            return this;
        }
        if (!childPath.startsWith(currentPath)) {
            throw new RuntimeException(format("%s ne peut pas �tre un sous element de %s", childPath, currentPath));
        }
        for (AbstractFileElement currFileElement : elements) {
            String currFilePath = FileHelper.windowsUnifyVolumePath(currFileElement.getFile().getAbsolutePath());
            if (FileHelper.hasSamePaths(childPath, currFilePath)) {
                return currFileElement;
            }
            if (currFileElement.isDirectory() && childPath.startsWith(currFilePath)) {
                return ((DirElement)currFileElement).getFileElement(childPath);
            }
        }
        return null;
    }
}
