package org.isma.dirmanager.gui;

import javax.swing.*;

public enum DirManagerIconEnum {
    APPLICATION_ICON("images/icon.png"),

    MOVIE_FILE("images/movie.png"),
    MUSIC_FILE("images/music.png"),
    ARCHIVE_FILE("images/archive.png"),
    QUESTON_FILE("images/question.png"),
    SOURCECODE_FILE("images/sourcecode.png"),
    EXCEL_FILE("images/excel.png"),
    IMAGE_FILE("images/image.png"),
    TEXT_FILE("images/text.png"),
    PDF_FILE("images/pdf.png"),
    WEB_ICON("images/web.png"),
    XML_ICON("images/xml.png"),

    EXPAND("images/expand.png"),
    COLLAPSE("images/collapse.png"),

    PREFERENCES("images/preferences.png");

    private String path;
    private ImageIcon imageIcon;


    DirManagerIconEnum(String path) {
        this.path = path;
        this.imageIcon = new ImageIcon(getClass().getClassLoader().getResource(getPath()));
    }


    public String getPath() {
        return path;
    }


    public ImageIcon getImageIcon() {
        return imageIcon;
    }


    public static Icon getIcon(String extension) {
        for (DirManagerIconEnum iconEnum : values()) {
            if (iconEnum.path.startsWith("images/" + extension + ".")) {
                return iconEnum.getImageIcon();
            }
        }
        return QUESTON_FILE.getImageIcon();
    }
}