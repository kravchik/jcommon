package yk.jcommon.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 8:53 PM
 */
public class YHashSet<T> extends LinkedHashSet<T> implements YSet<T> {

    @Override
    public YCollection<T> emptyInstance() {
        return hs();
    }

    public static <T> YHashSet<T> toYSet(Collection<T> source) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(source);
        return result;
    }

    @SafeVarargs
    public static <T> YHashSet<T> hs(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    public static <T> YHashSet<T> hs() {//to avoid creation of empty array if calling hs(T... tt)
        return new YHashSet<>();
    }

    @Override
    public YSet<T> filter(Predicate<? super T> predicate) {
        return YCollections.filterSet(this, predicate);
    }

    @Override
    public <R> YSet<R> map(Function<? super T, ? extends R> mapper) {
        return YCollections.mapSet(this, mapper);
    }

    @Override
    public <R> YSet<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper) {
        return YCollections.flatMapSet(this, mapper);
    }

    @Override
    public YSet<T> cdr() {
        YSet<T> result = new YHashSet<>();
        Iterator<T> iterator = this.iterator();
        iterator.next();
        for (; iterator.hasNext(); ) result.add(iterator.next());
        return result;
    }

    @Override
    public YSet<T> without(T t) {
        return YCollections.subSet(this, t);
    }

    @Override
    public YSet<T> withoutAll(Collection<T> tt) {
        return YCollections.subSet(this, tt);
    }

    @Override
    public YSet<T> withAll(Collection<T> c) {
        return YCollections.appendSet(this, c);
    }

    @Override
    public YSet<T> with(T t) {
        return YCollections.appendSet(this, t);
    }

    @SafeVarargs
    @Override
    public final YSet<T> with(T... tt) {
        YHashSet<T> result = new YHashSet<>();
        result.addAll(this);
        for (int i = 0; i < tt.length; i++) result.add(tt[i]);
        return result;
    }

    public <TT extends YHashSet<T>> YSet<T> forThis(Consumer<TT> c) {
        c.accept((TT)this);
        return this;
    }
}
