package yk.jcommon.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Kravchik Yuri
 * Date: 24.02.2012
 * Time: 8:01 PM
 */
public class I implements Iterable<I>, Iterator<I> {
    public int i = -1;
    public int m;

    public static I i(Collection c) {
        I result = new I();
        result.m = c.size();
        return result;
    }

    public Iterator<I> iterator() {
        return this;
    }

    public boolean hasNext() {
        return i < m - 1;
    }

    public I next() {
        i++;
        return this;
    }

    public void remove() {
        throw new Error();
    }

//    @Override
//    public void forEach(Block<? super I> block) {
//    }
}
