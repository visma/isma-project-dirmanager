package org.isma.dirmanager.util;

public class DefaultLogger implements IStepLogger {
    //TODO utiliser log4j a terme ou un truc comme ça quoi...
    public void log(String log, Object... params) {
        System.out.printf(log, params);
    }

    public void warn(String log, Object... params) throws Exception {
        System.err.printf(log, params);
    }



    public void logStep(String log, Object... params) {
        log(log, params);
    }
}
