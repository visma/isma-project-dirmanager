package org.isma.dirmanager;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElement;
import org.isma.dirmanager.util.GlobalProperties;
import org.isma.tests.AbstractFileTestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractDirManagerFileTestCase extends AbstractFileTestCase {
    protected static final String FILE_EMPTY_1 = "empty.txt";
    protected static final String FILE_EMPTY_2 = "empty_2.txt";
    protected static final String FILE_SHORT_1 = "five_char_file.txt";
    protected static final String FILE_SHORT_1_COPY = "five_char_file_copy.txt";
    protected static final String FILE_SHORT_2 = "five_char_file_2.txt";
    protected static final String FILE_SHORT_TEN_CHAR = "ten_char_file.txt";

    private IFileTestCaseFactory factory;


    protected AbstractDirManagerFileTestCase() {
        factory = getFileTestCaseFactory();
    }


    protected IFileTestCaseFactory getFileTestCaseFactory() {
        return new DefaultFileTestCaseFactory();
    }


    protected FileElement buildFileElement(String name) throws Exception {
        File file = copyFile("", name);
        return factory.buildFileElement(file);
    }


    protected FileElement buildFileElement(String dir, String name) throws Exception {
        File file = copyFile(dir, name);
        return factory.buildFileElement(file);
    }


    protected DirElement buildDir(String name) {
        File dir = mkdir(name);
        return factory.buildDirElement(dir);
    }


    protected DirElement buildRootDir() {
        File root = new File(TEST_ROOT_DIR);
        return factory.buildDirElement(root);
    }


    protected void assertFile(AbstractFileElement fileElement, String relativeFilePath) {
        String absolutePath = suppressDoubleBackSlash(fileElement.getFile().getAbsolutePath() + "\\");
        String endSuffix = suppressDoubleBackSlash(TEST_ROOT_DIR + relativeFilePath + "\\");
        String errorMessage = String.format("'%s' does not end with '%s'", absolutePath, endSuffix);
        assertTrue(errorMessage, absolutePath.endsWith(endSuffix));
    }


    protected void assertFileExists(String relativeFilePath) {
        assertTrue(new File(TEST_ROOT_DIR + "\\" + relativeFilePath).exists());
    }


    protected void assertFileContent(StringBuilder expectedContent, File actualFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(actualFile);
        try {
            StringBuilder actualContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                actualContent.append(scanner.nextLine() + GlobalProperties.getLineSeparator());
            }
            assertEquals(expectedContent.toString(), actualContent.toString());
        } finally {
            scanner.close();
        }
    }


    private String suppressDoubleBackSlash(String str) {
        return str.replace("\\\\", "\\");
    }


    protected String getAbsolutePathTestRootDir() {
        return System.getProperty("user.dir") + "\\" + TEST_ROOT_DIR;
    }
}
