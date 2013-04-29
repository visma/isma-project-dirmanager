package org.isma.dirmanager;

import org.isma.core.Configuration;

import java.io.File;

public interface IDirConfiguration extends Configuration {
    String getSourceFilePath();


    File getSource();


    String getTargetFilePath();


    File getTarget();
}
