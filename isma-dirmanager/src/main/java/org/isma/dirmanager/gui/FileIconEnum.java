package org.isma.dirmanager.gui;

import org.isma.utils.io.FileUtils;

import javax.swing.*;
import java.io.File;

public enum FileIconEnum {
    MOVIE_ICON(DirManagerIconEnum.MOVIE_FILE, "avi", "mpg", "mpeg", "mp4"),
    MUSIC_ICON(DirManagerIconEnum.MUSIC_FILE, "mp3", "wav", "ogg"),
    ARCHIVE_ICON(DirManagerIconEnum.ARCHIVE_FILE, "zip", "rar", "7z", "cab"),
    SOURCECODE_ICON(DirManagerIconEnum.SOURCECODE_FILE, "java", "ksh", "cmd", "bat", "js"),
    EXCEL_ICON(DirManagerIconEnum.EXCEL_FILE, "xls", "csv"),
    IMAGE_ICON(DirManagerIconEnum.IMAGE_FILE, "png", "jpg", "jpeg", "gif", "bmp"),
    TEXT_ICON(DirManagerIconEnum.TEXT_FILE, "txt", "src/main/resources-filtered/doc", "log"),
    PDF_ICON(DirManagerIconEnum.PDF_FILE, "pdf"),
    WEB_ICON(DirManagerIconEnum.WEB_ICON, "htm", "html", "css"),
    XML_ICON(DirManagerIconEnum.XML_ICON, "xml", "xslt", "xsd"),
    DEFAULT_ICON(DirManagerIconEnum.QUESTON_FILE);

    private DirManagerIconEnum iconEnum;
    private String[] extensions;


    FileIconEnum(DirManagerIconEnum iconEnum, String... extensions) {
        this.iconEnum = iconEnum;
        this.extensions = extensions;
    }


    public static ImageIcon getIcon(File file) {
        for (FileIconEnum fileIconEnum : values()) {
            String extension = FileUtils.getExtension(file);
            for (String currExtension : fileIconEnum.extensions) {
                if (extension.toLowerCase().equals(currExtension)) {
                    return fileIconEnum.iconEnum.getImageIcon();
                }
            }
        }
        return DEFAULT_ICON.iconEnum.getImageIcon();
    }


}
