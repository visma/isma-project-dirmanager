package org.isma.dirmanager.clonefinder;

import java.util.*;

import static java.util.Collections.sort;
public class CloneFinder<E> {
    private Comparator<E> comparator;


    public CloneFinder(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public List<Clones<E>> extractClones(List<E> elements) {
        sort(elements, comparator);
        Map<E, List<E>> elementMap = new HashMap<E, List<E>>();

        E lastElement = null;
        List<E> clones = null;
        for (E element : elements) {
            if (comparator.compare(lastElement, element) != 0) {
                clones = new ArrayList<E>();
                elementMap.put(element, clones);
            }
            clones.add(element);
            lastElement = element;
        }
        return buildClones(elementMap);
    }


    private List<Clones<E>> buildClones(Map<E, List<E>> elementMap) {
        List<Clones<E>> clonesList = new ArrayList<Clones<E>>();
        for (List<E> clones : elementMap.values()) {
            if (clones.size() > 1) {
                clonesList.add(new Clones<E>(clones, comparator));
            }
        }
        sort(clonesList);
        return clonesList;
    }
}
