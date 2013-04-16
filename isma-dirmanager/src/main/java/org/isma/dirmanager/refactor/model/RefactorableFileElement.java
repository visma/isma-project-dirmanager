package org.isma.dirmanager.refactor.model;

import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class RefactorableFileElement extends FileElement implements Refactorable {
    private final String oldName;
    private String newName;
    private boolean saved;


    public RefactorableFileElement(File file) {
        super(file);
        oldName = file.getName();
    }


    public void setNewName(String newName) {
        this.newName = newName;
    }


    @Override
    public File getFile() {
        File originalFile = super.getFile();
        if (newName != null) {
            return new File(originalFile.getParent() + "/" + newName);
        }
        else {
            return originalFile;
        }
    }


    public String getOldName() {
        return oldName;
    }


    public boolean isRefactored() {
        return newName != null && !oldName.equals(newName) && saved;
    }


    public boolean isEligible() {
        return newName != null;
    }


    public void save() {
        File oldFile = super.getFile();
        File newFile = getFile();
        System.out.printf("%s renommé en %s\n", oldFile.getAbsolutePath(), newFile.getAbsolutePath());
        saved = oldFile.renameTo(newFile);
        if (!saved) {
            throw new RuntimeException("erreur lors du renommage");
        }
        //Le renommage n'update pas le fichier source, ça craint (au moins pour les miniscules/majuscules)
        setFile(newFile);
    }
}
