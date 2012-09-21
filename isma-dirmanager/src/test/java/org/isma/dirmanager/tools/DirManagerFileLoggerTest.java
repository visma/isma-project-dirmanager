package org.isma.dirmanager.tools;
import org.isma.dirmanager.AbstractDirManagerFileTestCase;
import org.isma.dirmanager.util.GlobalProperties;

import java.io.File;
import java.io.IOException;

public class DirManagerFileLoggerTest extends AbstractDirManagerFileTestCase {

    public void testlog() throws IOException {
        FileLogger logger = new FileLogger();

        File logFile = new File(TEST_ROOT_DIR + "\\log.log");
        logger.open(logFile);
        logger.log("test%s line1%s", "log", GlobalProperties.getLineSeparator());
        logger.log("test%s line2%s", "log", GlobalProperties.getLineSeparator());
        logger.close();

        StringBuilder expected = new StringBuilder();
        expected.append("testlog line1" + GlobalProperties.getLineSeparator());
        expected.append("testlog line2" + GlobalProperties.getLineSeparator());

        assertFileContent(expected, logFile);
    }
}
