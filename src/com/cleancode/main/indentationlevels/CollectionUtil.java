package com.cleancode.main.indentationlevels;

import java.util.Collection;

/**
 * Created by mwittman on 26/03/2014.
 */
public class CollectionUtil {
    public static int calculateSum(Collection <Integer> items) {
        int sum = 0;
        for(int item : items)
            sum += item;
        return sum;
    }
}
