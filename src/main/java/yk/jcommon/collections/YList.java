package yk.jcommon.collections;

import yk.jcommon.utils.MyMath;
import yk.jcommon.utils.Util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
@SuppressWarnings("UnusedDeclaration")
public interface YList<T> extends YCollection<T>, List<T> {
    //TODO nTimes

    @Override//TODO remove (currently needed for cs translator)
    T get(int index);

    YList<T> filter(Predicate<? super T> predicate);

    @SuppressWarnings("unchecked")
    @Override
    default <R extends T> YList<R> filterByClass(Class<R> clazz) {
        return (YList<R>) filter(el -> clazz.isAssignableFrom(el.getClass()));
    }

    <R> YList<R> map(Function<? super T, ? extends R> mapper);
    <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);

    default T cadr() {
        return get(1);
    }

    default <K> YList<YList<T>> splitByDif(BiFunction<T, T, Boolean> splitter) {
        T last = null;
        YList<T> cur = null;
        YList<YList<T>> result = al();
        for (T t : this) {
            if (cur == null) {
                cur = al(t);
                result.add(cur);
            } else if (splitter.apply(last, t)) {
                cur = al(t);
                result.add(cur);
            } else {
                cur.add(t);
            }
            last = t;
        }
        return result;
    }

    @Override
    default YList<T> toList() {
        return this;
    }


    YList<T> cdr();
    YList<T> with(Collection<T> c);
    YList<T> with(T t);
    default YList<T> withAt(int index, T t) {
        YList<T> result = al();
        result.addAll(this);
        result.set(index, t);
        return result;
    }
    @SuppressWarnings("unchecked")
    YList<T> with(T... t);
    YList<T> without(Collection<T> c);
    YList<T> without(T t);
    @SuppressWarnings("unchecked")
    YList<T> without(T... t);
    YList<T> take(int count);

    YList<T> subList(int fromIndex, int toIndex);
    T last();

    default T lastOr(T t) {
        if (isEmpty()) return t;
        return last();
    }

    default T lastOrCalc(Supplier<T> supplier) {
        if (isEmpty()) return supplier.get();
        return last();
    }

    YList<T> allMin(Comparator<? super T> comparator);

    YList<YList<T>> eachToEach(YList<T> other);

    default <O, R> YList<R> eachToEach(Collection<O> other, BiFunction<T, O, R> combinator) {
        return YCollections.eachToEach(this, other, combinator);
    }

    default <O, R> YList<R> eachToEach(List<O> other, BiFunction<T, O, R> combinator) {
        return YCollections.eachToEach(this, other, combinator);
    }

    default void forUniquePares(BiConsumer<T, T> bc) {
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) bc.accept(get(i), get(j));
    }

    default <R> YList<R> mapUniquePares(BiFunction<T, T, R> bf) {
        YList<R> result = al();
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) result.add(bf.apply(get(i), get(j)));
        return result;
    }

    default YList<YList<T>> split(Object eq) {
        return split(e -> Util.equalsWithNull(e, eq));
    }

    default YList<YList<T>> split(Predicate<T> isSplitter) {
        YList<YList<T>> result = al();
        YList<T> cur = al();
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

    default YList<T> reverse() {
        YList<T> result = al();
        for (T t : this) result.add(0, t);
        return result;
    }

    default void forEachNeighbours(BiConsumer<T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get(MyMath.module(i + 0, this.size()));
            T t2 = this.get(MyMath.module(i + 1, this.size()));
            consumer.accept(t1, t2);
        }
    }

    default void forEachNeighbours(Consumer3<T, T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get(MyMath.module(i + 0, this.size()));
            T t2 = this.get(MyMath.module(i + 1, this.size()));
            T t3 = this.get(MyMath.module(i + 2, this.size()));
            consumer.accept(t1, t2, t3);
        }
    }

    default void forEachNeighbours(Consumer4<T, T, T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get(MyMath.module(i + 0, this.size()));
            T t2 = this.get(MyMath.module(i + 1, this.size()));
            T t3 = this.get(MyMath.module(i + 2, this.size()));
            T t4 = this.get(MyMath.module(i + 3, this.size()));
            consumer.accept(t1, t2, t3, t4);
        }
    }

    @Override
    default T last(Predicate<? super T> predicate) {
        T result = null;
        for (int i = 0, thisSize = this.size(); i < thisSize; i++) {
            T t = this.get(i);
            if (predicate.test(t)) result = t;
        }
        return result;
    }

    @Override
    default <CMP extends Comparable<CMP>> T max(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        CMP maxComparable = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (maxComparable == null || maxComparable.compareTo(nextComparable) < 0) {
                max = t;
                maxComparable = nextComparable;
            }
        }
        return max;
    }

    default T maxByFloat(FloatFunction<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get max on empty collection");
        T max = null;
        float maxComparable = 0;
        boolean found = false;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable > maxComparable) {
                max = t;
                maxComparable = nextComparable;
            }
            found = true;
        }
        return max;
    }

    @Override
    default <CMP extends Comparable<CMP>> T min(Function<T, CMP> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        CMP minComparable = null;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            CMP nextComparable = evaluator.apply(t);
            if (nextComparable == null) throw new RuntimeException("evaluator shouldn't return null values");
            if (minComparable == null || minComparable.compareTo(nextComparable) >= 0) {
                min = t;
                minComparable = nextComparable;
            }
        }
        return min;
    }

    default T minByFloat(FloatFunction<T> evaluator) {
        if (isEmpty()) throw new RuntimeException("can't get min on empty collection");
        T min = null;
        float minComparable = 0;
        boolean found = false;
        for (int i = 0; i < this.size(); i++) {
            T t = this.get(i);
            float nextComparable = evaluator.apply(t);
            if (!found || nextComparable < minComparable) {
                min = t;
                minComparable = nextComparable;
            }
            found = true;
        }
        return min;
    }
}
