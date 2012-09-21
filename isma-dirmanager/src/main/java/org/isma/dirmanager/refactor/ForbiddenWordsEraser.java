package org.isma.dirmanager.refactor;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ForbiddenWordsEraser implements RenamingLayer {
    private List<String> forbiddenWords;


    public ForbiddenWordsEraser(List<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }


    public String rename(String name) {
        for (String word : forbiddenWords) {
            if (StringUtils.containsIgnoreCase(name, word)) {
                name = name.replaceAll("(?i)" + word, "");
            }
        }
        name = name.replaceAll("  ", " ");

        int lastIndexOfExtension = name.lastIndexOf('.');
        if (lastIndexOfExtension == -1) {
            return name;
        }
        if (name.charAt(lastIndexOfExtension - 1) == ' ') {
            name = name.substring(0, lastIndexOfExtension - 1) +
                   name.substring(lastIndexOfExtension, name.length());
        }
        return name;
    }
}
