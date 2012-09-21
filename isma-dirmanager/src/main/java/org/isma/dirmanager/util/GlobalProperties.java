package org.isma.dirmanager.util;

public class GlobalProperties {
    private GlobalProperties() {
    }


    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }
}
