package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YArrayList.toYList;
import static yk.jcommon.collections.YHashSet.hs;

public class YHashSetWrapper<T> implements YSet<T> {
    //TODO move most functions without use of 'original' to default implementation
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
    public YSet<T> toSet() {
        return this;
    }

    @Override
    public YList<T> toList() {
        return toYList(original);
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
    public YSet<T> without(T... tt) {
        return without(hs(tt));
    }

    @Override
    public YList<T> sorted() {
        return YCollections.sortedCollection(original);
    }

    @Override
    public YList<T> sorted(Comparator<? super T> comparator) {
        return YCollections.sortedCollection(original, comparator);
    }

    @Override
    public YCollection<T> take(int count) {
        YSet result = hs();
        Iterator<T> it = iterator();
        for (int i = 0; i < count && it.hasNext(); i++) result.add(it.next());
        return result;
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
        throw BadException.die("cannot modify wrapper");
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
