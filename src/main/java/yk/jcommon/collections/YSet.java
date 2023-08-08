package yk.jcommon.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public interface YSet<T> extends YCollection<T>, Set<T> {
    @Override
    YSet<T> filter(Predicate<? super T> predicate);
    @Override
    <R> YSet<R> map(Function<? super T, ? extends R> mapper);
    @Override
    <R> YSet<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);
    @Override
    YSet<T> cdr();

    @Override
    default YSet<T> toSet() {
        return this;
    }

    @Override
    YSet<T> withAll(Collection<T> c);
    @Override
    YSet<T> with(T t);
    @Override
    @SuppressWarnings("unchecked")
    YSet<T> with(T... tt);
    @Override
    YSet<T> withoutAll(Collection<T> tt);
    @Override
    YSet<T> without(T t);
    @Override
    @SuppressWarnings("unchecked")
    default YSet<T> without(T... tt) {
        return withoutAll(hs(tt));
    }

    default YSet<T> intersection(YSet<T> other) {
        YSet<T> result = hs();
        for (T t : this) if (other.contains(t))result.add(t);
        return result;
    }

    @Override
    default YSet<T> take(int count) {
        YSet result = hs();
        Iterator<T> it = iterator();
        for (int i = 0; i < count && it.hasNext(); i++) result.add(it.next());
        return result;
    }

    @Override
    default YSet<T> assertSize(int s) {
        if (size() != s) {
            throw new RuntimeException("Expected size " + s + " but was " + size());
        }
        return this;
    }
}
