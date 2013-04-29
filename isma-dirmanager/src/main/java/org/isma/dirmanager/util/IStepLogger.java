package org.isma.dirmanager.util;

public interface IStepLogger extends ILogger {
    void logStep(String log, Object... params);
}
