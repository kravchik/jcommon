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
 *
 * TODO remove. YList should cover everything
 */
public class YArrayList<T> extends ArrayList<T> implements YList<T> {
    @Override
    public YCollection<T> emptyInstance() {
        return al();
    }

    @Override
    public boolean isAny(Predicate<? super T> predicate) {
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            if (predicate.test(this.get(i))) return true;
        }
        return false;
    }

    @Override
    public boolean isAll(Predicate<? super T> predicate) {
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

    //TODO test
    public static <T> YArrayList<T> toYList(Collection<T> source) {
        YArrayList<T> result = new YArrayList<>();
        result.addAll(source);
        return result;
    }

    //TODO test
    @SafeVarargs
    public static <T> YArrayList<T> al(T... tt) {
        YArrayList<T> result = new YArrayList<>();
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    public static <T> YArrayList<T> al() {//to avoid creation of empty array if calling al(T... tt)
        return new YArrayList<>();
    }

    public static <T> YArrayList<T> allocate(int size) {
        YArrayList<T> result = al();
        for (int i = 0; i < size; i++) result.add(null);
        return result;
    }

    public static <T> YArrayList<T> allocate(int size, Function_T_int<T> gen) {
        YArrayList<T> result = al();
        for (int i = 0; i < size; i++) result.add(gen.apply(i));
        return result;
    }

    public static <T> YArrayList<T> times(int n, Function_T_int<T> generator) {
        YArrayList<T> result = new YArrayList<>();
        for (int i = 0; i < n; i++) result.add(generator.apply(i));
        return result;
    }

    //TODO test
    @Override
    public YArrayList<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterList(this, predicate);
    }

    //TODO test
    @Override
    public <R> YArrayList<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapList(this, mapper);
    }

    //TODO test
    @Override
    public <R> YArrayList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
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

    //TODO test
    @Override
    public YArrayList<T> allMin(Comparator<? super T> comparator) {
        if (isEmpty()) return this;
        YArrayList<T> result = al();
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

    //TODO test
    @Override
    public T car() {
        return get(0);
    }

    //TODO test
    @Override
    public YArrayList<T> cdr() {
        return YCollections.cdr(this);
    }

    @Override
    public T last() {
        return get(size() - 1);
    }

    //TODO test
    @SuppressWarnings("NullableProblems")
    @Override
    public YArrayList<T> subList(int fromIndex, int toIndex) {
        return YCollections.subListFromList(this, fromIndex, toIndex);
    }

    //TODO test
    @Override
    public YArrayList<T> withAll(Collection<T> c) {
        YArrayList<T> result = al();
        result.addAll(this);
        result.addAll(c);
        return result;
    }

    //TODO test
    @Override
    public YArrayList<T> with(T t) {
        YArrayList<T> result = al();
        result.addAll(this);
        result.add(t);
        return result;
    }

    @Override
    public YArrayList<T> withAt(int index, T t) {
        YArrayList<T> result = toYList(this);
        result.set(index, t);
        return result;
    }

    //TODO test
    @SafeVarargs
    @Override
    public final YArrayList<T> with(T... tt) {
        YArrayList<T> result = al();
        result.addAll(this);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    //TODO test
    @Override
    public YArrayList<T> without(Collection<T> c) {
        YArrayList<T> result = al();
        for (T t : this) if (!c.contains(t)) result.add(t);
        return result;
    }

    //TODO test
    @Override
    public YArrayList<T> without(T t) {
        YArrayList<T> result = al();
        for (T tt : this) if (tt != t) result.add(tt);
        return result;
    }

    //TODO test
    @SafeVarargs
    @Override
    public final YArrayList<T> without(T... tt) {
        return without(Arrays.asList(tt));
    }

    //TODO test
    @Override
    public YArrayList<T> take(int count) {
        YArrayList<T> result = al();
        for (int i = 0; i < count; i++) {
            if (i >= size()) break;
            result.add(get(i));
        }
        return result;
    }

    @Override
    public YList<YList<T>> eachToEach(YList<T> other) {
        YList<YList<T>> result = al();
        for (T t : this) for (T o : other) result.add(al(t, o));
        return result;
    }

    @Override
    public <O, R> YArrayList<R> eachToEach(Collection<O> other, BiFunction<T, O, R> combinator) {
        return YCollections.eachToEach(this, other, combinator);
    }

    @Override
    public <O, R> YArrayList<R> eachToEach(List<O> other, BiFunction<T, O, R> combinator) {
        YArrayList<R> result = new YArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            T a = this.get(i);
            for (int j = 0; j < other.size(); j++) {
                result.add(combinator.apply(a, other.get(j)));
            }
        }
        return result;
    }

    @Override
    public <R> YArrayList<R> mapUniquePares(BiFunction<T, T, R> bf) {
        YArrayList<R> result = al();
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) result.add(bf.apply(get(i), get(j)));
        return result;
    }

    @Override
    public YArrayList<YArrayList<T>> split(Predicate<T> isSplitter) {
        YArrayList<YArrayList<T>> result = al();
        YArrayList<T> cur = al();
        for (T l : this) {
            if (isSplitter.test(l)) {
                result.add(cur);
                cur = al();
            } else {
                cur.add(l);
            }
        }
        result.add(cur);
        return result;
    }

    //TODO test
    @Override
    public YArrayList<T> reverse() {
        YArrayList<T> result = al();
        for (int i = this.size() - 1; i >= 0; i--) result.add(get(i));
        return result;
    }

    /**
     * Removes last component and sets it in place of required. It allows to make remove without array copy.
     */
    public T removeFast(int i) {
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException(i + " (list size: "+ size() + ")");
        T old = get(i);
        if (i < size() - 1) set(i, remove(size() - 1)); else remove(i);
        return old;
    }
}
