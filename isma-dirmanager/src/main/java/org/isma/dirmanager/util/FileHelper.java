package org.isma.dirmanager.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO merger avec FileUtils
public class FileHelper {
    private FileHelper() {
    }


    public static boolean isSubDirectory(File rootDir, File dir) {
        String rootPath = FileHelper.formatDirectoryPath(rootDir);
        String dirPath = FileHelper.formatDirectoryPath(dir);
        return dirPath.startsWith(rootPath);
    }


    private static String formatDirectoryPath(File dir) {
        if (!dir.isDirectory()) {
            throw new RuntimeException("must be a directory !");
        }
        return dir.getAbsolutePath() + "/";
    }


    //TODO utiliser de mani�re centralisé si possible
    public static String windowsUnifyVolumePath(String filePath) {
        Pattern pattern = Pattern.compile("^[a-z]{1}:.*$");
        Matcher matcher = pattern.matcher(filePath);
        if (matcher.matches()) {
            String prefix = filePath.substring(0, 1).toUpperCase();
            String suffix = filePath.substring(1, filePath.length());
            return prefix + suffix;
        }
        return filePath;
    }


    public static boolean hasSamePaths(String path1, String path2) {
        if (!path1.endsWith("/")) {
            path1 += "/";
        }
        if (!path2.endsWith("/")) {
            path2 += "/";
        }
        return path1.equals(path2);
    }


    public static boolean hasSamePaths(File file1, File file2) {
        return hasSamePaths(file1.getAbsolutePath(), file2.getAbsolutePath());
    }


    public static String getParentDirAbsolutePath(String childPath) {
        return new File(childPath).getParentFile().getAbsolutePath();
    }
}
