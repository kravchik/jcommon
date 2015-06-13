package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

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
    YCollection<T> cdr();
    default T first() {return car();}
    default T first(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return t;
        return null;
    }

    default T max() {
        return YCollections.maxFromCollection(this);
    }
    default T max(Comparator<? super T> comparator) {
        return YCollections.maxFromCollection(this, comparator);
    }
    default T max(Function<T, Float> evaluator) {
        return YCollections.maxFromCollection(this, (t1, t2) -> Float.compare(evaluator.apply(t1), evaluator.apply(t2)));
    }
    default T min() {
        return YCollections.minFromCollection(this);
    }
    default T min(Comparator<? super T> comparator) {
        return YCollections.minFromCollection(this, comparator);
    }
    default T min(Function<T, Float> evaluator) {
        return YCollections.minFromCollection(this, (t1, t2) -> Float.compare(evaluator.apply(t1), evaluator.apply(t2)));
    }

    YSet<T> toSet();
    YList<T> toList();

    YCollection<T> with(Collection<T> c);
    YCollection<T> with(T t);
    @SuppressWarnings("unchecked")
    YCollection<T> with(T... t);

    YCollection<T> without(Collection<T> c);
    YCollection<T> without(T t);
    @SuppressWarnings("unchecked")
    YCollection<T> without(T... t);

    default YList<T> sorted() {
        return YCollections.sortedCollection(this);
    }

    default YList<T> sorted(Comparator<? super T> comparator) {
        return YCollections.sortedCollection(this, comparator);
    }

    //TODO Function<T, Comparable<T>>
    default YList<T> sorted(Function<T, Float> evaluator) {
        return YCollections.sortedCollection(this, (v1, v2) -> Float.compare(evaluator.apply(v1), evaluator.apply(v2)));
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
}
