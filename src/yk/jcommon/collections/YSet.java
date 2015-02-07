package yk.jcommon.collections;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public interface YSet<T> extends Set<T> {
    YSet<T> filter(Predicate<? super T> predicate);
    boolean any(Predicate<? super T> predicate);
    boolean all(Predicate<? super T> predicate);
    <R> YSet<R> map(Function<? super T, ? extends R> mapper);
    <R> YSet<R> flatMap(Function<? super T, ? extends List<? extends R>> mapper);

    YList<T> sorted();
    YList<T> sorted(Comparator<? super T> comparator);
    T car();
    YSet<T> cdr();
    T first();
    T last();

    T max();
    T min();

    YArrayList<T> toList();

    YSet<T> sub(T t);
    YSet<T> sub(Set<T> tt);

    YSet<T> append(T t);



}
