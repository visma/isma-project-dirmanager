package org.isma.dirmanager.util;

import java.util.Comparator;

public class ReverseComparator<E> implements Comparator<E> {
    private Comparator<E> comparator;

    public ReverseComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int compare(E o1, E o2) {
        return comparator.compare(o2, o1);
    }
}
