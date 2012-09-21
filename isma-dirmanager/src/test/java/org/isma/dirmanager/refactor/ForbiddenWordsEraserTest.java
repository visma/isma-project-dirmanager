package org.isma.dirmanager.refactor;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ForbiddenWordsEraserTest extends TestCase {

    public void testEraser() throws Exception {
        List<String> forbiddenWordList = buildForbiddenWordList();

        RenamingLayer eraser = new ForbiddenWordsEraser(forbiddenWordList);
        assertEquals("la soupe aux choux Encoded by truc.avi",
                     eraser.rename("la soupe aux choux DvdRip Encoded by truc.avi"));
        assertEquals("the killer - John Woo.txt",
                     eraser.rename("the killer - John Woo DvdRip.txt"));
        assertEquals("withoutExtension", eraser.rename("withoutExtensionDvdRip"));
    }


    private List<String> buildForbiddenWordList() {
        List<String> list = new ArrayList<String>();
        list.add("dvdrip");
        return list;
    }
}
