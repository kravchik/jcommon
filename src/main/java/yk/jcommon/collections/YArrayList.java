package yk.jcommon.collections;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:26 PM
 */
public class YArrayList<T> extends ArrayList<T> implements YList<T> {

    public YArrayList() {
    }

    public YArrayList(Collection<? extends T> c) {
        super(c);
    }

    public static <T> YArrayList<T> al(Collection<T> source) {
        YArrayList<T> result = new YArrayList<>();
        result.addAll(source);
        return result;
    }

    @SafeVarargs
    public static <T> YArrayList<T> al(T... tt) {
        YArrayList<T> result = new YArrayList<>();
        result.addAll(Arrays.asList(tt));
        return result;
    }

    @Override
    public YArrayList<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterList(this, predicate);
    }

    @Override
    public int count(Predicate<? super T> predicate) {
        int result = 0;
        for (T t : this) if (predicate.test(t)) result++;
        return result;
    }

    @Override
    public <R extends T> YList<R> filterByClass(Class<R> clazz) {
        return (YList<R>) filter(el -> clazz.isAssignableFrom(el.getClass()));
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
    public <R> YList<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapList(this, mapper);
    }

    @Override
    public <R> YList<R> flatMap(Function<? super T, ? extends List<? extends R>> mapper) {
        return YCollections.flatMapList(this, mapper);
    }

    @Override
    public <R> R foldLeft(R first, BiFunction<R, T, R> folder) {
        if (size() == 0) return first;
        R result = first;
        for (int i = 1; i < size(); i++) result = folder.apply(result, get(i));
        return result;
    }

    @Override
    public T max() {
        return YCollections.maxFromList(this);
    }

    @Override
    public T max(Comparator<? super T> comparator) {
        return YCollections.maxFromList(this, comparator);
    }

    @Override
    public T min() {
        return YCollections.minFromList(this);
    }

    @Override
    public T min(Comparator<? super T> comparator) {
        return YCollections.minFromList(this, comparator);
    }

    @Override
    public YList<T> allMin(Comparator<? super T> comparator) {
        if (isEmpty()) return this;
        YList<T> result = al();
        T cur = null;
        for (T t : sorted(comparator)) {
            if (cur == null || comparator.compare(cur, t) == 0) {
                cur = t;
                result.add(t);
            } else {
                break;
            }
        }
        return result;
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
    public YList<T> sorted(Function<T, Float> evaluator) {
        return YCollections.sortedCollection(this, (v1, v2) -> Float.compare(evaluator.apply(v1), evaluator.apply(v2)));
    }

    @Override
    public T car() {
        return get(0);
    }

    @Override
    public YArrayList<T> cdr() {
        return YCollections.cdr(this);
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
        return get(size() - 1);
    }

    @Override
    public YSet<T> toSet() {
        return YCollections.collectionToHashSet(this);
    }

    @Override
    public YList<T> subList(int fromIndex, int toIndex) {
        return YCollections.subListFromList(this, fromIndex, toIndex);
    }

    @Override
    public YList<T> join(Collection<T> c) {
        YList<T> result = al();
        result.addAll(this);
        result.addAll(c);
        return result;
    }

    @Override
    public YList<T> sub(T t) {
        YList<T> result = al();
        for (T tt : this) if (tt != t) result.add(tt);
        return result;
    }

    @Override
    public String join(String separator) {
        boolean was = false;
        String result = "";
        for (Object o : this) {
            if (was) result += separator;
            result += o;
            was = true;
        }
        return result;

    }

    @Override
    public String join(String prefix, String appender) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Object o : this) {
            if (first) first = false;
            else sb.append(appender);
            sb.append(prefix).append(o == null ? "null" : o.toString());
        }
        return sb.toString();
    }

    @Override
    public YList<T> take(int count) {
        YList result = al();
        for (int i = 0; i < count; i++) {
            if (i >= size()) break;
            result.add(get(i));
        }
        return result;
    }

    @Override
    public YList<YList<T>> shuffle(YList<T> other) {
        YList<YList<T>> result = al();
        for (T t : this) for (T o : other) result.add(al(t, o));
        return result;
    }
}
