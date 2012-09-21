package org.isma.dirmanager.model;

import java.util.*;

public class FileMap {
    private Map<Long, List<FileElement>> map = new HashMap<Long, List<FileElement>>();


    public FileMap(DirElement dirElement) {
        init(dirElement);
    }


    private void init(DirElement dirElement) {
        for (FileElement fileElement : dirElement.getDepthFileElementList()) {
            put(fileElement);
        }
    }


    private void put(FileElement fileElement) {
        long length = fileElement.getFile().length();
        if (!map.containsKey(length)) {
            map.put(length, new ArrayList<FileElement>());
        }
        map.get(length).add(fileElement);
    }


    public boolean exists(AbstractFileElement abstractFileElement) {
        if (abstractFileElement.isDirectory()) {
            return abstractFileElement.getFile().exists();
        }
        FileElement fileElement = (FileElement)abstractFileElement;
        long length = fileElement.getFile().length();
        List<FileElement> fileElements = map.get(length);
        if (fileElements == null) {
            return false;
        }
        //sort is a prerequisite to binarySearch
        Collections.sort(fileElements, new FileComparator());
        return Collections.binarySearch(fileElements, fileElement, new FileComparator()) >= 0;
    }


    public boolean putNew(FileElement fileElement) {
        if (!exists(fileElement)) {
            put(fileElement);
            return true;
        }
        return false;
    }
}
