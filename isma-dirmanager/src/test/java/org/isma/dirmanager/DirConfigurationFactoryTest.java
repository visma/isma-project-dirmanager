package org.isma.dirmanager;
import junit.framework.TestCase;

public class DirConfigurationFactoryTest extends TestCase {
    private DirConfigurationFactory factory = new DirConfigurationFactory();


    public void testDirConfiguration() throws Exception {

        new PreferencesManager().put(DirConfigurationFactory.SOURCE_DIR_KEY, "source_dir_key");
        new PreferencesManager().put(DirConfigurationFactory.TARGET_DIR_KEY, "target_dir_key");

        IDirConfiguration conf = factory.buildDirConfiguration();
        assertEquals("source_dir_key", conf.getSourceFilePath());
        assertEquals("target_dir_key", conf.getTargetFilePath());
    }
}
