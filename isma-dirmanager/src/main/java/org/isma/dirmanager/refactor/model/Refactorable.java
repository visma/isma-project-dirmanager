package org.isma.dirmanager.refactor.model;

public interface Refactorable {
    String getOldName();

    boolean isRefactored();

    boolean isEligible();
}
