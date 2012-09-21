package org.isma.dirmanager;

import org.isma.core.Configuration;

import java.io.File;

public interface IDirConfiguration extends Configuration {
    public String getSourceFilePath();


    public File getSource();


    public String getTargetFilePath();


    public File getTarget();
}
