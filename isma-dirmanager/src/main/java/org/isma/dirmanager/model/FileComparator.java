package org.isma.dirmanager.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;

public class FileComparator implements Comparator<FileElement> {
    public int compare(FileElement o1, FileElement o2) {
        if (o1 == null) {
            return -1;
        }
        else if (o2 == null) {
            return 1;
        }
        File file1 = o1.getFile();
        File file2 = o2.getFile();
        long sizeDiff = file1.length() - file2.length();
        if (sizeDiff != 0) {
            return sizeDiff > 0 ? 1 : -1;
        }
        try {
            return compareContent(file1, file2);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("erreur lors de la comparaison de fichiers", e);
        }
    }


    private int compareContent(File file1, File file2) throws IOException {
        BufferedInputStream bis1 = null;
        BufferedInputStream bis2 = null;
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(file1);
            fis2 = new FileInputStream(file2);
            bis1 = new BufferedInputStream(fis1);
            bis2 = new BufferedInputStream(fis2);
            byte[] byteArray1 = new byte[1024 * 1024 * 2];
            byte[] byteArray2 = new byte[1024 * 1024 * 2];
            bis1.read(byteArray1);
            bis2.read(byteArray2);
            return compare(byteArray1, byteArray2);
        }
        finally {
            if (fis1 != null) {
                fis1.close();
            }
            if (fis2 != null) {
                fis2.close();
            }
            if (bis1 != null) {
                bis1.close();
            }
            if (bis2 != null) {
                bis2.close();
            }
        }
    }


    private int compare(byte[] b1, byte[] b2) {
        for (int i = 0; i < b1.length; i++) {
            int diff = b1[i] - b2[i];
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }
}
