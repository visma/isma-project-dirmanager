package org.isma.dirmanager.clonefinder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Clones<C> implements Comparable<Clones<C>> {
    private List<C> clones = new ArrayList<C>();
    private Comparator<C> comparator;


    public Clones(List<C> clones, Comparator<C> comparator) {
        this.clones = clones;
        this.comparator = comparator;
    }


    public List<C> getClones() {
        return clones;
    }


    public int compareTo(Clones<C> other) {
        if (clones.isEmpty() && other.clones.isEmpty()) {
            return 0;
        }
        if (clones.isEmpty()) {
            return -1;
        }
        if (other.clones.isEmpty()) {
            return 1;
        }
        C elem = clones.get(0);
        C elem2 = other.clones.get(0);
        return comparator.compare(elem, elem2);
    }
}
