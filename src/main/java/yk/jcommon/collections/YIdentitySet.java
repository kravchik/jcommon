package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/23/14
 * Time: 12:32 PM
 */
public class YIdentitySet<T> implements YSet<T> {
    private Map<T, Object> m = new IdentityHashMap<>();
    private final Object CONTAINS = new Object();

    @Override
    public int size() {
        return m.size();
    }

    @Override
    public boolean isEmpty() {
        return m.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return m.containsKey(o);
    }

    @Override
    public Iterator<T> iterator() {
        return m.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return m.keySet().toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return m.keySet().toArray(a);
    }

    @Override
    public boolean add(T t) {
        return m.put(t, CONTAINS) == null;
    }

    @Override
    public boolean remove(Object o) {
        return m.remove(o) == CONTAINS;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return m.keySet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean res = false;
        for (T o : c) res = res || add(o);
        return res;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return m.keySet().removeAll(c);
    }

    @Override
    public void clear() {
        m.clear();
    }

    @Override
    public YSet<T> filter(Predicate<? super T> predicate) {
        throw BadException.notImplemented();
    }

    @Override
    public boolean any(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return true;
        return false;
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        throw BadException.notImplemented();
    }

    @Override
    public <R> YSet<R> map(Function<? super T, ? extends R> mapper) {
        throw BadException.notImplemented();
    }

    @Override
    public <R> YSet<R> flatMap(Function<? super T, ? extends List<? extends R>> mapper) {
        throw BadException.notImplemented();
    }

    @Override
    public YList<T> sorted() {
        throw BadException.notImplemented();
    }

    @Override
    public YList<T> sorted(Comparator<? super T> comparator) {
        throw BadException.notImplemented();
    }

    @Override
    public T car() {
        throw BadException.notImplemented();
    }

    @Override
    public YSet<T> cdr() {
        throw BadException.notImplemented();
    }

    @Override
    public T first() {
        throw BadException.notImplemented();
    }

    @Override
    public T last() {
        throw BadException.notImplemented();
    }

    @Override
    public T max() {
        throw BadException.notImplemented();
    }

    @Override
    public T min() {
        throw BadException.notImplemented();
    }

    @Override
    public YArrayList<T> toList() {
        throw BadException.notImplemented();
    }

    @Override
    public YSet<T> sub(T t) {
        throw BadException.notImplemented();
    }

    @Override
    public YSet<T> sub(Set<T> tt) {
        throw BadException.notImplemented();
    }

    @Override
    public YSet<T> join(T t) {
        throw BadException.notImplemented();
    }

    @Override
    public T first(Predicate<? super T> predicate) {
        throw BadException.notImplemented();
    }

    @Override
    public YSet<T> join(Collection<T> c) {
        throw BadException.notImplemented();
    }
}
