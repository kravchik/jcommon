package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YArrayList.toYList;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.toYSet;

@SuppressWarnings("UnusedDeclaration")
public interface YCollection<T> extends Collection<T> {

    YCollection<T> filter(Predicate<? super T> predicate);
    <R extends T> YCollection<R> filterByClass(Class<R> clazz);

    default boolean any(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return true;
        return false;
    }

    default boolean all(Predicate<? super T> predicate) {
        for (T t : this) if (!predicate.test(t)) return false;
        return true;
    }

    <R> YCollection<R> map(Function<? super T, ? extends R> mapper);
    default <R> YCollection<R> mapWithIndex(BiFunction<Integer, ? super T, ? extends R> mapper) {
        Iterator<T> it = iterator();
        YCollection<R> result = al();
        for (int i = 0; i < size(); i++) {
            result.add(mapper.apply(i, it.next()));
        }
        return result;
    };
    <R> YCollection<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);
    default <R> R reduce(R first, BiFunction<R, T, R> folder) {
        R result = first;
        for (T t : this) result = folder.apply(result, t);
        return result;
    }
    default T reduce(BiFunction<T, T, T> folder) {
        if (isEmpty()) return null;
        Iterator<T> i = iterator();
        T result = i.next();
        while (i.hasNext()) result = folder.apply(result, i.next());
        return result;
    }

    default T car() {
        return iterator().next();
    }
    default T cadr() {
        Iterator<T> iterator = iterator();
        iterator.next();
        return iterator.next();
    }
    YCollection<T> cdr();
    default T first() {return car();}
    default T first(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return t;
        return null;
    }
    default T last(Predicate<? super T> predicate) {
        T result = null;
        for (T t : this) if (predicate.test(t)) result = t;
        return result;
    }

    default T max() {
        return YCollections.maxFromCollection(this);
    }
    default T max(Comparator<? super T> comparator) {
        return YCollections.maxFromCollection(this, comparator);
    }
    default T max(Function<T, Comparable> evaluator) {
        return YCollections.maxFromCollection(this, (t1, t2) -> evaluator.apply(t1).compareTo(evaluator.apply(t2)));
    }
    default <R extends Comparable> R maxMap(Function<T, R> evaluator) {
        R max = null;
        for (T t : this) {
            R candidate = evaluator.apply(t);
            max = max == null || max.compareTo(candidate) < 0 ? candidate : max;
        }
        return max;
    }
    default T min() {
        return YCollections.minFromCollection(this);
    }
    default T min(Comparator<? super T> comparator) {
        return YCollections.minFromCollection(this, comparator);
    }
    default T min(Function<T, Comparable> evaluator) {
        return YCollections.minFromCollection(this, (t1, t2) -> evaluator.apply(t1).compareTo(evaluator.apply(t2)));
    }
    default <R extends Comparable> R minMap(Function<T, R> evaluator) {
        R min = null;
        for (T t : this) {
            R candidate = evaluator.apply(t);
            min = min == null || min.compareTo(candidate) > 0 ? candidate : min;
        }
        return min;
    }

    default YSet<T> toSet() {
        return toYSet(this);
    }
    default YList<T> toList() {
        return toYList(this);
    }

    YCollection<T> with(Collection<T> c);
    YCollection<T> with(T t);
    @SuppressWarnings("unchecked")
    YCollection<T> with(T... t);

    YCollection<T> without(Collection<T> c);
    YCollection<T> without(T t);
    @SuppressWarnings("unchecked")
    YCollection<T> without(T... t);

    //TODO reversed
    //YSet returns not YSet but YList, because sorting algorithm itself creates list  //TODO fix this?
    default YList<T> sorted() {
        return YCollections.sortedCollection(this);
    }

    default YList<T> sorted(Comparator<? super T> comparator) {
        return YCollections.sortedCollection(this, comparator);
    }

    default YList<T> sorted(Function<T, Comparable> evaluator) {
        return YCollections.sortedCollection(this, (v1, v2) -> evaluator.apply(v1).compareTo(evaluator.apply(v2)));
    }

    YCollection<T> take(int count);

    default public int count(Predicate<? super T> predicate) {
        int result = 0;
        for (T t : this) if (predicate.test(t)) result++;
        return result;
    }

    @SuppressWarnings("unchecked")
    default public boolean containsAll(T... tt) {
        for (T t : tt) if (!contains(t)) return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    default public boolean containsAny(Collection<? extends T> tt) {
        for (T t : tt) if (contains(t)) return true;
        return false;
    }

    @SuppressWarnings("unchecked")
    default public boolean containsAny(T... tt) {
        for (T t : tt) if (contains(t)) return true;
        return false;
    }

    default public String toString(String separator) {
        StringBuilder sb = new StringBuilder("");
        boolean was = false;
        for (Object o : this) {
            if (was) sb.append(separator);
            sb.append(o);
            was = true;
        }
        return sb.toString();

    }

    default public String toString(String prefix, String appender) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Object o : this) {
            if (first) first = false;
            else sb.append(appender);
            sb.append(prefix).append(o == null ? "null" : o.toString());
        }
        return sb.toString();
    }

    default public <V> YMap<T, V> toMapKeys(Function<T, V> f) {
        YMap<T, V> result = hm();
        for (T k : this) result.put(k, f.apply(k));
        return result;
    }

    default <V> YMap<T, V> mapKeys(Function<T, V> f) {
        YMap<T, V> result = hm();
        for (T k : this) result.put(k, f.apply(k));
        return result;
    }

    default boolean notEmpty() {
        return !isEmpty();
    }
}
