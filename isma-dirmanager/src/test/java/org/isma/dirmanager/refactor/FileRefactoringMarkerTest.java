package org.isma.dirmanager.refactor;

import junit.framework.TestCase;
import org.isma.dirmanager.model.FileElement;

import java.io.File;

public class FileRefactoringMarkerTest extends TestCase {

    public void testNewName() throws Exception {
        FileRefactoringMarker marker = new FileRefactoringMarker(buildRenamingLayers());
        assertEquals("xyz", marker.getNewName(new FileElement(new File("abc"))));
    }


    private RenamingLayer[] buildRenamingLayers() {
        return new RenamingLayer[]{new ChartoChar('a', 'x'), new ChartoChar('b', 'y'), new ChartoChar('c', 'z')};
    }


    private class ChartoChar implements RenamingLayer {
        private char oldChar;
        private char newChar;


        private ChartoChar(char oldChar, char newChar) {
            this.oldChar = oldChar;
            this.newChar = newChar;
        }


        public String rename(String name) {
            return name.replace(oldChar, newChar);
        }
    }
}
