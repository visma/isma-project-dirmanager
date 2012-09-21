package org.isma.dirmanager.clonefinder;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CloneFinderTest extends TestCase {

    public void testExtractNoClones() throws Exception {
        CloneFinder<String> finder = new CloneFinder<String>(new StringComparator());
        List<Clones<String>> clonesList = finder.extractClones(buildNoClonesList());
        assertEquals(0, clonesList.size());
    }


    public void testExtractClones() throws Exception {
        CloneFinder<String> finder = new CloneFinder<String>(new StringComparator());
        List<Clones<String>> clonesList = finder.extractClones(buildClonesList());
        assertEquals(2, clonesList.size());
        Clones<String> tataClones = clonesList.get(0);
        Clones<String> totoClones = clonesList.get(1);

        assertEquals(2, tataClones.getClones().size());
        assertEquals("tata", tataClones.getClones().get(0));
        assertEquals("tata", tataClones.getClones().get(1));

        assertEquals(3, totoClones.getClones().size());
        assertEquals("toto", totoClones.getClones().get(0));
        assertEquals("toto", totoClones.getClones().get(1));
        assertEquals("toto", totoClones.getClones().get(2));

    }


    private List<String> buildNoClonesList() {
        List<String> list = new ArrayList<String>();
        list.add("toto");
        list.add("tata");
        list.add("titi");
        return list;
    }


    private List<String> buildClonesList() {
        List<String> list = new ArrayList<String>();
        list.add("toto");
        list.add("tata");
        list.add("toto");
        list.add("tata");
        list.add("titi");
        list.add("toto");
        return list;
    }


    private class StringComparator implements Comparator<String> {
        public int compare(String o1, String o2) {
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            return o1.compareTo(o2);
        }
    }
}
