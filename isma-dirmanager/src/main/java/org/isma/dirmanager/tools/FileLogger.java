package org.isma.dirmanager.tools;

import org.isma.dirmanager.util.ILogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {
    private BufferedWriter bw;


    public void log(String log, Object... params) throws IOException {
        bw.write(String.format(log, params));
    }


    public void warn(String log, Object... params) throws Exception {
        log(log, params);
    }


    public void open(File file) throws IOException {
        bw = new BufferedWriter(new FileWriter(file));
    }


    public void close() throws IOException {
        bw.close();
    }
}
