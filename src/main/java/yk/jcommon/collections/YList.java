package yk.jcommon.collections;

import yk.jcommon.utils.Util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YArrayList.*;
import static yk.jcommon.collections.YHashMap.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
@SuppressWarnings("UnusedDeclaration")
public interface YList<T> extends YCollection<T>, List<T> {

    YList<T> filter(Predicate<? super T> predicate);
    @Override
    default <R extends T> YList<R> filterByClass(Class<R> clazz) {
        return (YList<R>) filter(el -> clazz.isAssignableFrom(el.getClass()));
    }

    <R> YList<R> map(Function<? super T, ? extends R> mapper);
    <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);

    //TODO same for sets
    default <K> YMap<K, List<T>> groupBy(Function<T, K> grouper) {
        YMap<K, List<T>> result = hm();
        for (T t : this) {
            K group = grouper.apply(t);
            List<T> gg = result.get(group);
            if (gg == null) {
                gg = al();
                result.put(group, gg);
            }
            gg.add(t);
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
    @SuppressWarnings("unchecked")
    YList<T> with(T... t);
    YList<T> without(Collection<T> c);
    YList<T> without(T t);
    @SuppressWarnings("unchecked")
    YList<T> without(T... t);
    YList<T> take(int count);

    @SuppressWarnings("NullableProblems")
    YList<T> subList(int fromIndex, int toIndex);
    T last();
    YList<T> allMin(Comparator<? super T> comparator);
    YList<YList<T>> shuffle(YList<T> other);

    default void forUniquePares(BiConsumer<T, T> bc) {
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) bc.accept(get(i), get(j));
    }

    default <R> YList<R> mapUniquePares(BiFunction<T, T, R> bf) {
        YList<R> result = al();
        for (int i = 0; i < size()-1; i++) for (int j = i+1; j < size(); j++) result.add(bf.apply(get(i), get(j)));
        return result;
    }

    default public YList<YList<T>> split(Object eq) {
        return split(e -> Util.equalsWithNull(e, eq));
    }

    default public YList<YList<T>> split(Predicate<T> isSplitter) {
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

}
