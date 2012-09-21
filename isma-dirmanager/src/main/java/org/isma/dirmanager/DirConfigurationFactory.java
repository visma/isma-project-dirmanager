package org.isma.dirmanager;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Class.forName;

public class DirConfigurationFactory {
    public static final String TARGET_DIR_KEY = "target.dir";
    public static final String SOURCE_DIR_KEY = "source.dir";
    private PreferencesManager preferencesManager = new PreferencesManager();


    public IDirConfiguration buildDirConfiguration() throws Exception {
        IDirConfiguration defaultConf = buildDefaultDirConfiguration();
        return new PreferredConfiguration(defaultConf, preferencesManager);
    }


    public IDirConfiguration buildDefaultDirConfiguration() throws Exception {
        InputStream stream = forName(DirConfigurationFactory.class.getName()).getResourceAsStream("default.properties");
        final Properties properties = new Properties();
        properties.load(stream);
        return new DefaultConfiguration(properties);
    }


    private static class PreferredConfiguration implements IDirConfiguration {
        private IDirConfiguration defaultConf;
        private PreferencesManager prefsManager;


        PreferredConfiguration(IDirConfiguration defaultConf, PreferencesManager prefsManager) {
            this.defaultConf = defaultConf;
            this.prefsManager = prefsManager;
        }


        public File getTarget() {
            return new File(getTargetFilePath());
        }


        public File getSource() {
            return new File(getSourceFilePath());
        }


        public String getSourceFilePath() {
            return prefsManager.get(SOURCE_DIR_KEY, defaultConf.getSourceFilePath());
        }


        public String getTargetFilePath() {
            return prefsManager.get(TARGET_DIR_KEY, defaultConf.getTargetFilePath());
        }
    }

    private static class DefaultConfiguration implements IDirConfiguration {
        private final Properties properties;


        DefaultConfiguration(Properties properties) {
            this.properties = properties;
        }


        public File getTarget() {
            return new File(getTargetFilePath());
        }


        public String getSourceFilePath() {
            return (String)properties.get(SOURCE_DIR_KEY);
        }


        public File getSource() {
            return new File(getSourceFilePath());
        }


        public String getTargetFilePath() {
            return (String)properties.get(TARGET_DIR_KEY);
        }
    }
}
