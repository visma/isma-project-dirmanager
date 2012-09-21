package org.isma.dirmanager.util;

public interface ILogger {
    public void log(String log, Object... params) throws Exception;

    public void warn(String log, Object... params) throws Exception;
}
