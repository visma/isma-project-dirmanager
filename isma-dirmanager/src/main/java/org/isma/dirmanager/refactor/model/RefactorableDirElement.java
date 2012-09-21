package org.isma.dirmanager.refactor.model;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;

import java.io.File;

public class RefactorableDirElement extends DirElement implements Refactorable {
    public RefactorableDirElement(File directory) {
        super(directory);
    }

    public boolean isRefactored() {
        for (AbstractFileElement fileElement : getElements()) {
            Refactorable refactorable = (Refactorable) fileElement;
            if (refactorable.isRefactored()) {
                return true;
            }
        }
        return false;
    }

    public boolean isEligible() {
        for (AbstractFileElement fileElement : getElements()) {
            Refactorable refactorable = (Refactorable) fileElement;
            if (refactorable.isEligible()) {
                return true;
            }
        }
        return false;
    }


    public String getOldName() {
        return getName();
    }
}
