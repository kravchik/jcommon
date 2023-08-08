package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;


/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
//TODO remove defaults which return modifiable list
@SuppressWarnings("ForLoopReplaceableByForEach")
public interface YList<T> extends YCollection<T>, List<T> {
    //TODO nTimes

    @Override//TODO remove (currently needed for cs translator)
    T get(int index);

    default T getOr(int index, T or) {
        return index >= size() || index < 0 ? or : get(index);
    }

    @Override
    YList<T> filter(Predicate<? super T> predicate);

    @Override
    <R> YList<R> map(Function<? super T, ? extends R> mapper);

    @Override
    default <R> YList<R> mapWithIndex(BiFunction<Integer, ? super T, ? extends R> mapper) {
        return (YList<R>) YCollection.super.mapWithIndex(mapper);
    }

    @Override
    <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);

    /**
     * The same as 'forEach', but returns 'this' so can continue using the instasnce.
     */
    @Override
    default YList<T> forEachFun(Consumer<T> consumer) {
        for (T t : this) consumer.accept(t);
        return this;
    }

    default <T2> YList<T> forZip(YList<T2> b, BiConsumer<T, T2> f) {
        if (size() != b.size()) throw new RuntimeException("Expected the same size");
        for (int i = 0; i < this.size(); i++) f.accept(this.get(i), b.get(i));
        return this;
    }

    @Override
    default YList<T> forWithIndex(BiConsumer<Integer, ? super T> consumer) {
        for (int i = 0; i < size(); i++) consumer.accept(i, get(i));
        return this;
    }

    @Override
    default T cadr() {
        return get(1);
    }

    @Override
    default YList<T> toList() {
        return this;
    }


    @Override
    YList<T> cdr();
    @Override
    YList<T> withAll(Collection<T> c);
    @Override
    YList<T> with(T t);
    YList<T> withAt(int index, T t);
    @Override
    @SuppressWarnings("unchecked")
    YList<T> with(T... t);
    @Override
    YList<T> withoutAll(Collection<T> c);
    @Override
    YList<T> without(T t);
    @Override
    @SuppressWarnings("unchecked")
    YList<T> without(T... t);
    @Override
    YList<T> take(int count);

    @Override
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

    <O, R> YList<R> eachToEach(Collection<O> other, BiFunction<T, O, R> combinator);

    <O, R> YList<R> eachToEach(List<O> other, BiFunction<T, O, R> combinator);

    default void forUniquePares(BiConsumer<T, T> bc) {
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) bc.accept(get(i), get(j));
    }

    <R> YList<R> mapUniquePares(BiFunction<T, T, R> bf);

    default YList<? extends YList<T>> split(Object eq) {
        return split(e -> e == null ? eq == null : e.equals(eq));
    }

    YList<? extends YList<T>> split(Predicate<T> isSplitter);

    YList<T> reverse();

    default void forEachNeighbours(BiConsumer<T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            consumer.accept(t1, t2);
        }
    }

    default void forEachNeighbours(Consumer3<T, T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            T t3 = this.get((i + 2) % this.size());
            consumer.accept(t1, t2, t3);
        }
    }

    default void forEachNeighbours(Consumer4<T, T, T, T> consumer) {
        for (int i = 0; i < this.size(); i++) {
            T t1 = this.get((i + 0) % this.size());
            T t2 = this.get((i + 1) % this.size());
            T t3 = this.get((i + 2) % this.size());
            T t4 = this.get((i + 3) % this.size());
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

    @Override
    default T maxByFloat(Function_float_T<T> evaluator) {
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

    @Override
    default T minByFloat(Function_float_T<T> evaluator) {
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

    default <T2, T3> YList<T3> zipWith(YList<T2> b, BiFunction<T, T2, T3> f) {
        return YCollections.zip(this, b, f);
    }

    @Override
    default YList<T> assertSize(int s) {
        if (size() != s) throw new RuntimeException("Expected size " + s + " but was " + size());
        return this;
    }

    default YList<T> forThis(YListConsumer<T> c) {
        c.accept(this);
        return this;
    }

    default <T2> T2 mapThis(YListFunction<T, T2> f) {
        return f.apply(this);
    }

    interface YListConsumer<T> {
        void accept(YList<T> al);
    }

    interface YListFunction<T, T2> {
        T2 apply(YList<T> al);
    }
}
