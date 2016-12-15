package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class YHashSetWrapper<T> implements YSet<T> {
    private Set<T> original;

    public YHashSetWrapper(Set<T> original) {
        this.original = original;
    }

    @Override
    public YSet<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterSet(original, predicate);
    }

    @Override
    public <R> YSet<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapSet(original, mapper);
    }

    @Override
    public <R> YSet<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMapSet(original, mapper);
    }

    @Override
    public YSet<T> cdr() {
        YSet<T> result = new YHashSet<>();
        Iterator<T> iterator = original.iterator();
        iterator.next();
        for (; iterator.hasNext(); ) result.add(iterator.next());
        return result;
    }

    @Override
    public YSet<T> with(Collection<T> c) {
        return YCollections.appendSet(original, c);
    }

    @Override
    public YSet<T> with(T t) {
        return YCollections.appendSet(original, t);
    }

    @Override
    public YSet<T> with(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(original);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    @Override
    public YSet<T> without(Collection<T> tt) {
        return YCollections.subSet(original, tt);
    }

    @Override
    public YSet<T> without(T t) {
        return YCollections.subSet(original, t);
    }

    @Override
    public int size() {
        return original.size();
    }

    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return original.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return original.iterator();
    }

    @Override
    public Object[] toArray() {
        return original.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return original.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw BadException.die("cannot modify wrapper");
    }

    @Override
    public boolean remove(Object o) {
        throw BadException.die("cannot modify wrapper");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return original.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw BadException.die("cannot modify wrapper");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return original.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw BadException.die("cannot modify wrapper");
    }

    @Override
    public void clear() {
        throw BadException.die("cannot modify wrapper");
    }
}
