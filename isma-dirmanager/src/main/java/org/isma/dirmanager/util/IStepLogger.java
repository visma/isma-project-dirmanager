package org.isma.dirmanager.util;

public interface IStepLogger extends ILogger {
    public void logStep(String log, Object... params);
}
