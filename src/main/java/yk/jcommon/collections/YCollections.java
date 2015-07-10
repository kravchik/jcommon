package yk.jcommon.collections;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YArrayList.toYList;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 5:48 PM
 */
public class YCollections {
    public static <T> YArrayList<T> filterList(List<T> l, Predicate<? super T> predicate) {
        YArrayList result = new YArrayList();
        for (int i = 0, lSize = l.size(); i < lSize; i++) {
            T t = l.get(i);
            if (predicate.test(t)) result.add(t);
        }
        return result;
    }

    public static <T> YHashSet<T> filterSet(Collection<T> l, Predicate<? super T> predicate) {
        YHashSet result = new YHashSet();
        for (T t : l) if (predicate.test(t)) result.add(t);
        return result;
    }

    static <T, R> YList<R> mapList(List<T> source, Function<? super T, ? extends R> mapper) {
        YArrayList<R> result = new YArrayList();
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            result.add(mapper.apply(source.get(i)));
        }
        return result;
    }

    static <T, R> YSet<R> mapSet(Set<T> source, Function<? super T, ? extends R> mapper) {
        YHashSet<R> result = new YHashSet();
        for (T t : source) result.add(mapper.apply(t));
        return result;
    }

    static <T, R> YList<R> flatMapList(List<T> source, Function<? super T, ? extends Collection<? extends R>> mapper) {
        YArrayList<R> result = new YArrayList();
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            for (R r : mapper.apply(source.get(i))) result.add(r);
        }
        return result;
    }

    static <T, R> YHashSet<R> flatMapSet(Set<T> source, Function<? super T, ? extends Collection<? extends R>> mapper) {
        YHashSet<R> result = new YHashSet();
        for (T t : source) {
            for (R r : mapper.apply(t)) result.add(r);
        }
        return result;
    }

    static <T> YList<T> sortedCollection(Collection<T> source) {
        YList<T> result = toYList(source);
        Collections.sort((List)result);
        return result;
    }

    static <T> YList<T> sortedCollection(Collection<T> source, Comparator<? super T> comparator) {
        YList<T> result = toYList(source);
        Collections.sort(result, comparator);
        return result;
    }

    static <T> T maxFromList(List<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || ((Comparable<T>)t).compareTo(result) > 0) result = t;
        }

        return result;
    }

    static <T> T maxFromList(List<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || comparator.compare(t, result) > 0) result = t;
        }

        return result;
    }

    static <T> T maxFromCollection(Collection<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (T t : source) {
            if (result == null || comparator.compare(t, result) > 0) result = t;
        }
        return result;
    }

    static <T> T minFromList(List<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || comparator.compare(t, result) < 0) result = t;
        }

        return result;
    }

    static <T> T minFromCollection(Collection<T> source, Comparator<? super T> comparator) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (T t : source) {
            if (result == null || comparator.compare(t, result) < 0) result = t;
        }
        return result;
    }

    static <T> T maxFromCollection(Collection<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T result = null;
        for (T t : source) if (result == null || ((Comparable<T>)t).compareTo(result) > 0) result = t;
        return result;
    }

    static <T> T minFromList(List<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T result = null;
        for (int i = 0, sourceSize = source.size(); i < sourceSize; i++) {
            T t = source.get(i);
            if (result == null || ((Comparable<T>)t).compareTo(result) < 0) result = t;
        }

        return result;
    }

    static <T> T minFromCollection(Collection<T> source) {
        if (source.isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T result = null;
        for (T t : source) if (result == null || ((Comparable<T>)t).compareTo(result) < 0) result = t;
        return result;
    }

    public static <T> YArrayList<T> cdr(List<T> source) {
        YArrayList<T> result = new YArrayList();
        for (int i = 1, sourceSize = source.size(); i < sourceSize; i++) {
            result.add(source.get(i));
        }
        return result;
    }

    static <T> YList<T> subListFromList(List<T> source, int fromIndex, int toIndex) {
        YList<T> result = YArrayList.al();
        for (int i = fromIndex; i < toIndex; i++) result.add(source.get(i));
        return result;
    }

    public static <T> YSet subSet(Collection<T> c, T t) {
        YHashSet<T> result = new YHashSet<>();
        for (T t1 : c) if (!t1.equals(t)) result.add(t1);
        return result;
    }

    public static <T> YSet subSet(Collection<T> c, Collection<T> t) {
        YHashSet<T> result = new YHashSet<>();
        for (T t1 : c) if (!t.contains(t1)) result.add(t1);
        return result;
    }

    public static <T> YSet<T> appendSet(Collection<T> ts, Collection<T> tt) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(ts);
        result.addAll(tt);
        return result;
    }

    public static <T> YSet<T> appendSet(Collection<T> ts, T t) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(ts);
        result.add(t);
        return result;
    }

    public static <T> YSet<YMap<T, T>> scramble(YSet<T> aa, YSet<T> bb) {
        return scramble(aa, bb, YHashMap.hm());
    }

    public static <T> YSet<YMap<T, T>> scramble(YSet<T> aa, YSet<T> bb, YMap<T, T> prev) {
        if (aa.isEmpty() || bb.isEmpty()) return YHashSet.hs(prev);
        YSet<YMap<T, T>> result = YHashSet.hs();
        T car = aa.car();
        YSet<T> cdr = aa.cdr();
        for (T b : bb) result.addAll(scramble(cdr, bb.without(b), prev.with(car, b)));
        return result;
    }



}
