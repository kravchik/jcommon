package yk.jcommon.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
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
    @Override
    public boolean any(Predicate<? super T> predicate) {
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            if (predicate.test(this.get(i))) return true;
        }
        return false;
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            if (!predicate.test(this.get(i))) return false;
        }
        return true;
    }

    public YArrayList() {
    }

    public YArrayList(Collection<? extends T> c) {
        super(c);
    }

    public static <T> YArrayList<T> toYList(Collection<T> source) {
        YArrayList<T> result = new YArrayList<>();
        result.addAll(source);
        return result;
    }

    @SafeVarargs
    public static <T> YArrayList<T> al(T... tt) {
        YArrayList<T> result = new YArrayList<>();
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    @Override
    public YArrayList<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterList(this, predicate);
    }

    @Override
    public <R> YList<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapList(this, mapper);
    }

    @Override
    public <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMapList(this, mapper);
    }

    @Override
    public <R> R reduce(R first, BiFunction<R, T, R> folder) {
        R result = first;
        for (int i = 0; i < size(); i++) result = folder.apply(result, get(i));
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
    public T car() {
        return get(0);
    }

    @Override
    public YArrayList<T> cdr() {
        return YCollections.cdr(this);
    }

    @Override
    public T last() {
        return get(size() - 1);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public YList<T> subList(int fromIndex, int toIndex) {
        return YCollections.subListFromList(this, fromIndex, toIndex);
    }

    @Override
    public YList<T> with(Collection<T> c) {
        YList<T> result = al();
        result.addAll(this);
        result.addAll(c);
        return result;
    }

    @Override
    public YList<T> with(T t) {
        YList<T> result = al();
        result.addAll(this);
        result.add(t);
        return result;
    }

    @SafeVarargs
    @Override
    public final YList<T> with(T... tt) {
        YList<T> result = al();
        result.addAll(this);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    @Override
    public YList<T> without(Collection<T> c) {
        YList<T> result = al();
        for (T t : this) if (!c.contains(t)) result.add(t);
        return result;
    }

    @Override
    public YList<T> without(T t) {
        YList<T> result = al();
        for (T tt : this) if (tt != t) result.add(tt);
        return result;
    }

    @SafeVarargs
    @Override
    public final YList<T> without(T... tt) {
        return without(Arrays.asList(tt));
    }

    @Override
    public YList<T> take(int count) {
        YList<T> result = al();
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

    @Override
    public YList<T> reverse() {
        YList<T> result = al();
        for (int i = this.size() - 1; i >= 0; i--) result.add(get(i));
        return result;
    }

    /**
     * Removes last component and sets it in place of required. It allows to make remove without array copy.
     */
    public T removeFast(int i) {
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException(i + " (list size: "+ size() + ")");
        T old = get(i);
        if (i < size() - 1) {
            T last = remove(size() - 1);
            set(i, last);
        } else {
            remove(i);
        }
        return old;
    }
}
