package yk.jcommon.utils;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 08/02/16
 * Time: 12:44
 */
public class Zip2<A, B> implements Iterable<Zip2<A, B>> , Iterator<Zip2<A, B>> {
    private Iterator<A> aa;
    private Iterator<B> bb;

    public int i = -1;
    public A a;
    public B b;

    public Zip2(Iterable<A> aa, Iterable<B> bb) {
        this.aa = aa.iterator();
        this.bb = bb.iterator();
    }

    @Override
    public Iterator<Zip2<A, B>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return aa.hasNext() && bb.hasNext();
    }

    @Override
    public Zip2<A, B> next() {
        i++;
        a = aa.next();
        b = bb.next();
        return this;
    }
}
