package yk.jcommon.utils;

import java.util.Iterator;

/**
 * Kravchik Yuri
 * Date: 08.05.2012
 * Time: 10:33 PM
 */
public class WasIterator<T> implements Iterable<T>, Iterator<T> {
    private Iterable<T> b;
    private Iterator<T> it;
    private boolean again;

    public WasIterator(Iterable<T> b) {
        this.b = b;
    }

    public Iterator<T> iterator() {
        it = b.iterator();
        again = false;
        return this;
    }

    public boolean hasNext() {
        return it.hasNext() || again;
    }

    public T next() {
        if (!it.hasNext() && again) {
            it = b.iterator();
            again = false;
        }
        return it.next();
    }

    public void again() {
        again = true;
    }

    public void remove() {
        throw new RuntimeException("undefined");
    }

//    @Override
//    public void forEach(Block<? super T> block) {
//    }
}
