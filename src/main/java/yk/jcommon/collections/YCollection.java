package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.*;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YArrayList.toYList;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.toYSet;

//TODO tests and remove suppress UnusedDeclaration
@SuppressWarnings("UnusedDeclaration")
public interface YCollection<T> extends Collection<T> {

    YCollection<T> emptyInstance();

    YCollection<T> filter(Predicate<? super T> predicate);

    default <K> YMap<K, YList<T>> groupBy(Function<T, K> grouper) {
        YMap<K, YList<T>> result = hm();
        for (T t : this) {
            K group = grouper.apply(t);
            YList<T> gg = result.get(group);
            if (gg == null) {
                gg = al();
                result.put(group, gg);
            }
            gg.add(t);
        }
        return result;
    }

    default boolean isAny(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return true;
        return false;
    }

    default boolean isAll(Predicate<? super T> predicate) {
        for (T t : this) if (!predicate.test(t)) return false;
        return true;
    }

    <R> YCollection<R> map(Function<? super T, ? extends R> mapper);

    //TODO test
    default <R> YCollection<R> mapWithIndex(BiFunction<Integer, ? super T, ? extends R> mapper) {
        Iterator<T> it = iterator();
        YCollection<R> result = al();
        for (int i = 0; i < size(); i++) {
            result.add(mapper.apply(i, it.next()));
        }
        return result;
    }

    //TODO test
    <R> YCollection<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);

    /**
     * The same as 'forEach', but returns 'this' so can continue using the instasnce.
     */
    default YCollection<T> forEachFun(Consumer<T> consumer) {
        for (T t : this) consumer.accept(t);
        return this;
    }

    //TODO test
    default <T2> YCollection<T> forZip(YCollection<T2> b, BiConsumer<T, T2> f) {
        Iterator<T> i1 = this.iterator();
        Iterator<T2> i2 = b.iterator();
        while(true) {
            if (i1.hasNext() != i2.hasNext()) throw new RuntimeException("Expected the same size");
            if (!i1.hasNext()) break;
            f.accept(i1.next(), i2.next());
        }
        return this;
    }

    default YCollection<T> forWithIndex(BiConsumer<Integer, ? super T> consumer) {
        Iterator<T> it = iterator();
        int i = 0;
        while(it.hasNext()) consumer.accept(i++, it.next());
        return this;
    }

    default <R> R reduce(R first, BiFunction<R, T, R> folder) {
        R result = first;
        for (T t : this) result = folder.apply(result, t);
        return result;
    }

    //TODO test
    default T reduce(BiFunction<T, T, T> folder) {
        if (isEmpty()) return null;
        Iterator<T> i = iterator();
        T result = i.next();
        while (i.hasNext()) result = folder.apply(result, i.next());
        return result;
    }

    //TODO test
    default T car() {
        return iterator().next();
    }

    //TODO test
    default T cadr() {
        Iterator<T> iterator = iterator();
        iterator.next();
        return iterator.next();
    }

    YCollection<T> cdr();

    default T first() {
        return car();
    }

    default T firstOr(T t) {
        if (isEmpty()) return t;
        return first();
    }

    default T firstOrCalc(Supplier<T> supplier) {
        if (isEmpty()) return supplier.get();
        return first();
    }

    //TODO test
    default T first(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.test(t)) return t;
        return null;
    }

    //TODO firstOr(Predicate<? super T> predicate, T default)
    //TODO lastOr(Predicate<? super T> predicate, T default)

    //TODO test
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

    default <CMP extends Comparable<CMP>> T max(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        CMP maxComparable = null;
        for (T t : this) {
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (maxComparable == null || maxComparable.compareTo(nextComparable) < 0) {
                max = t;
                maxComparable = nextComparable;
            }
        }
        return max;
    }

    default T maxByFloat(Function_float_T<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        float maxComparable = 0;
        boolean found = false;
        for (T t : this) {
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable > maxComparable) {
                max = t;
                maxComparable = nextComparable;
            }
            found = true;
        }
        return max;
    }

    default T min() {
        return YCollections.minFromCollection(this);
    }

    default T min(Comparator<? super T> comparator) {
        return YCollections.minFromCollection(this, comparator);
    }

    default <CMP extends Comparable<CMP>> T min(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        CMP minComparable = null;
        for (T t : this) {
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (minComparable == null || minComparable.compareTo(nextComparable) >= 0) {
                min = t;
                minComparable = nextComparable;
            }
        }
        return min;
    }

    default T minByFloat(Function_float_T<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        float minComparable = 0;
        boolean found = false;
        for (T t : this) {
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable < minComparable) {
                min = t;
                minComparable = nextComparable;
            }
            found = true;
        }
        return min;
    }

    default YSet<T> toSet() {
        return toYSet(this);
    }

    default YList<T> toList() {
        return toYList(this);
    }

    YCollection<T> withAll(Collection<T> c);

    YCollection<T> with(T t);

    @SuppressWarnings("unchecked")
    YCollection<T> with(T... t);

    YCollection<T> withoutAll(Collection<T> c);

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

    //TODO test
    default int count(Predicate<? super T> predicate) {
        int result = 0;
        for (T t : this) if (predicate.test(t)) result++;
        return result;
    }

    //TODO test
    @SuppressWarnings("unchecked")
    default boolean containsAll(T... tt) {
        for (T t : tt) if (!contains(t)) return false;
        return true;
    }

    //TODO test
    @SuppressWarnings("unchecked")
    default boolean containsAny(Collection<? extends T> tt) {
        for (T t : tt) if (contains(t)) return true;
        return false;
    }

    //TODO test
    @SuppressWarnings("unchecked")
    default boolean containsAny(T... tt) {
        for (T t : tt) if (contains(t)) return true;
        return false;
    }

    //TODO test
    default String toString(String infix) {
        StringBuilder sb = new StringBuilder("");
        boolean was = false;
        for (Object o : this) {
            if (was) sb.append(infix);
            sb.append(o);
            was = true;
        }
        return sb.toString();
    }

    //TODO test
    default String toStringSuffix(String suffix) {
        StringBuilder sb = new StringBuilder("");
        for (Object o : this) sb.append(o).append(suffix);
        return sb.toString();

    }

    //TODO test
    default String toStringInfix(String infix) {
        return toString(infix);
    }

    //TODO test
    default String toStringPrefixInfix(String prefix, String infix) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Object o : this) {
            if (first) first = false;
            else sb.append(infix);
            sb.append(prefix).append(o == null ? "null" : o.toString());
        }
        return sb.toString();
    }

    //TODO test
    default String toStringPrefixSuffix(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        for (Object o : this) {
            sb.append(prefix).append(o == null ? "null" : o.toString()).append(suffix);
        }
        return sb.toString();
    }

    //TODO test
    default <V> YMap<T, V> toMapKeys(Function<T, V> f) {
        YMap<T, V> result = hm();
        for (T k : this) result.put(k, f.apply(k));
        return result;
    }

    //TODO test
    default boolean notEmpty() {
        return !isEmpty();
    }

    default <V> V ifEmpty(V v, Function<YCollection<T>, V> Else) {
        if (isEmpty()) return v;
        return Else.apply(this);
    }

    default YCollection<T> assertSize(int s) {
        if (size() != s) throw new RuntimeException("Expected size " + s + " but was " + size());
        return this;
    }
}
