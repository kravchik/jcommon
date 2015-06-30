package yk.jcommon.collections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YArrayList.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 5:43 PM
 */
public class YListWrapper<T> implements YList<T> {
    private List<T> l;

    public YListWrapper(List<T> l) {
        this.l = l;
    }

    public static <T> YListWrapper<T> wrap(List<T> source) {
        return new YListWrapper(source);
    }

    @Override
    public YList filter(Predicate predicate) {
        return YCollections.filterList(l, predicate);
    }

    @Override
    public <R> YList<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapList(l, mapper);
    }

    @Override
    public <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMapList(l, mapper);
    }

    @Override
    public T car() {
        return l.get(0);
    }

    @Override
    public YList<T> cdr() {
        return YCollections.cdr(l);
    }

    @Override
    public T last() {
        return l.get(l.size() - 1);
    }

    @Override
    public YList<T> allMin(Comparator<? super T> comparator) {
        return toYList(l).allMin(comparator);
    }

    @Override
    public YList<YList<T>> shuffle(YList<T> other) {
        YList<YList<T>> result = al();
        for (T t : l) for (T o : other) result.add(al(t, o));
        return result;
    }

    @Override
    public T max() {
        return YCollections.maxFromList(l);
    }

    @Override
    public T min() {
        return YCollections.minFromList(l);
    }

    @Override
    public int size() {
        return l.size();
    }

    @Override
    public boolean isEmpty() {
        return l.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return l.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return l.iterator();
    }

    @Override
    public Object[] toArray() {
        return l.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return l.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return l.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return l.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return l.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return l.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return l.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return l.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return l.retainAll(c);
    }

    @Override
    public void clear() {
        l.clear();
    }

    @Override
    public T get(int index) {
        return l.get(index);
    }

    @Override
    public T set(int index, T element) {
        return l.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        l.add(index, element);
    }

    @Override
    public T remove(int index) {
        return l.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return l.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return l.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return l.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return l.listIterator(index);
    }

    @Override
    public YList<T> subList(int fromIndex, int toIndex) {
        return YCollections.subListFromList(l, fromIndex, toIndex);
    }

    @Override
    public YSet<T> toSet() {
        return YCollections.collectionToHashSet(l);
    }

    @Override
    public YList<T> with(Collection<T> c) {
        return toYList(l).with(c);
    }

    @Override
    public YList<T> with(T t) {
        return toYList(l).with(t);
    }

    @Override
    public YList<T> with(T... t) {
        return toYList(l).with(t);
    }

    @Override
    public YList<T> without(Collection<T> c) {
        return toYList(l).without(c);
    }

    @Override
    public YList<T> without(T t) {
        return toYList(l).without(t);
    }

    @Override
    public YList<T> without(T... t) {
        return toYList(l).without(t);
    }

    @Override
    public YList<T> take(int count) {
        return toYList(l).take(count);
    }
}
