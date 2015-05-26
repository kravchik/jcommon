package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public interface YSet<T> extends YCollection<T>, Set<T> {
    YSet<T> filter(Predicate<? super T> predicate);
    default <R extends T> YSet<R> filterByClass(Class<R> clazz) {
        return (YSet<R>) filter(el -> clazz.isAssignableFrom(el.getClass()));
    }
    <R> YSet<R> map(Function<? super T, ? extends R> mapper);
    <R> YSet<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);
    YSet<T> cdr();
    YSet<T> with(Collection<T> c);
    YSet<T> with(T t);
    @SuppressWarnings("unchecked")
    YSet<T> with(T... tt);
    YSet<T> without(Collection<T> tt);
    YSet<T> without(T t);
    @SuppressWarnings("unchecked")
    YSet<T> without(T... tt);

    //TODO set (because its linked)
    YList<T> sorted();
    YList<T> sorted(Comparator<? super T> comparator);
}
