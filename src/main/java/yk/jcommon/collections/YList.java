package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
public interface YList<T> extends List<T> {

    YList<T> filter(Predicate<? super T> predicate);
    int count(Predicate<? super T> predicate);
    <R extends T> YList<R> filterByClass(Class<R> clazz);

    boolean any(Predicate<? super T> predicate);
    boolean all(Predicate<? super T> predicate);

    <R> YList<R> map(Function<? super T, ? extends R> mapper);
    <R> YList<R> flatMap(Function<? super T, ? extends List<? extends R>> mapper);

    <R> R foldLeft(R first, BiFunction<R, T, R> folder);

    YList<T> sorted();
    YList<T> sorted(Comparator<? super T> comparator);
    YList<T> sorted(Function<T, Float> evaluator);
    T car();
    YList<T> cdr();
    T first();
    T first(Predicate<? super T> predicate);
    T last();

    T max();
    T max(Comparator<? super T> comparator);
    T min();
    T min(Comparator<? super T> comparator);
    YList<T> allMin(Comparator<? super T> comparator);

    YSet<T> toSet();

    YList<T> join(Collection<T> c);//TODO rename append?

    YList<T> sub(T t);

    String join(String separator);//TODO toString?
    String join(String prefix, String appender);//TODO toString?

    @Override
    YList<T> subList(int fromIndex, int toIndex);
    YList<T> take(int count);

    YList<YList<T>> shuffle(YList<T> other);

    //TODO join(T) or append(T)

}
