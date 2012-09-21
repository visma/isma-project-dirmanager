package org.isma.dirmanager.refactor;

public class LowCaseExtensionRenamer implements RenamingLayer {

    public String rename(String name) {
        int lastIndexOfExtension = name.lastIndexOf('.');
        if (lastIndexOfExtension == -1) {
            return name;
        }
        String prefix = name.substring(0, lastIndexOfExtension);
        String suffix = name.substring(lastIndexOfExtension, name.length());
        suffix = suffix.toLowerCase();

        return prefix + suffix;
    }
}
