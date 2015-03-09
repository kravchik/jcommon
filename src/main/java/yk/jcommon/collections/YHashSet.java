package yk.jcommon.collections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public class YHashSet<T> extends HashSet<T> implements YSet<T> {


    public static <T> YHashSet<T> toYSet(Collection<T> source) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(source);
        return result;
    }

    public static <T> YHashSet<T> hs(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        Collections.addAll(result, tt);
        return result;
    }

    @Override
    public YSet<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterSet(this, predicate);
    }

    @Override
    public boolean any(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return true;
        return false;
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        for (T t : this) if (!predicate.test(t)) return false;
        return true;
    }

    @Override
    public <R> YSet<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapSet(this, mapper);
    }

    @Override
    public <R> YSet<R> flatMap(Function<? super T, ? extends List<? extends R>> mapper) {
        return YCollections.flatMapSet(this, mapper);
    }

    @Override
    public YList<T> sorted() {
        return YCollections.sortedCollection(this);
    }

    @Override
    public YList<T> sorted(Comparator<? super T> comparator) {
        return YCollections.sortedCollection(this, comparator);
    }

    @Override
    public T car() {
        return iterator().next();
    }

    @Override
    public YSet<T> cdr() {
        YSet<T> result = new YHashSet<>();
        Iterator<T> iterator = this.iterator();
        iterator.next();
        for (; iterator.hasNext(); ) result.add(iterator.next());
        return result;
    }

    @Override
    public T first() {
        return car();
    }

    @Override
    public T first(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return t;
        return null;
    }

    @Override
    public T last() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public T max() {
        return YCollections.maxFromCollection(this);
    }

    @Override
    public T min() {
        return YCollections.minFromCollection(this);
    }

    @Override
    public YArrayList<T> toList() {
        return YArrayList.toYList(this);
    }

    @Override
    public YSet<T> sub(T t) {
        return YCollections.subSet(this, t);
    }

    @Override
    public YSet<T> sub(Set<T> tt) {
        return YCollections.subSet(this, tt);
    }

    @Override
    public YSet<T> join(Collection<T> c) {
        return YCollections.appendSet(this, c);
    }

    @Override
    public YSet<T> join(T t) {
        return YCollections.appendSet(this, t);
    }
}
