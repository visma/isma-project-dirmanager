package org.isma.dirmanager.util;

public interface ILogger {
    void log(String log, Object... params) throws Exception;

    void warn(String log, Object... params) throws Exception;
}
