package com.findwise;

import java.util.Comparator;

public class IndexEntryComparator implements Comparator<IndexEntry> {

    @Override
    public int compare(IndexEntry o1, IndexEntry o2) {
        return Double.compare(o2.getScore(), o1.getScore());
    }
}
