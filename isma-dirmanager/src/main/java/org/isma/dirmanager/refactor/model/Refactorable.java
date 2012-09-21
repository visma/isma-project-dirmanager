package org.isma.dirmanager.refactor.model;

public interface Refactorable {
    public String getOldName();

    public boolean isRefactored();

    public boolean isEligible();
}
