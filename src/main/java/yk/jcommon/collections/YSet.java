package yk.jcommon.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YHashSet.*;

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

    @Override
    default YSet<T> toSet() {
        return this;
    }

    YSet<T> with(Collection<T> c);
    YSet<T> with(T t);
    @SuppressWarnings("unchecked")
    YSet<T> with(T... tt);
    YSet<T> without(Collection<T> tt);
    YSet<T> without(T t);
    @SuppressWarnings("unchecked")
    default YSet<T> without(T... tt) {
        return without(hs(tt));
    }

    @Override
    default YSet<T> take(int count) {
        YSet result = hs();
        Iterator<T> it = iterator();
        for (int i = 0; i < count && it.hasNext(); i++) result.add(it.next());
        return result;
    }

}
