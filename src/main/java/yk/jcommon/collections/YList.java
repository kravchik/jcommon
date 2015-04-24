package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/12/14
 * Time: 3:27 PM
 */
@SuppressWarnings("UnusedDeclaration")
public interface YList<T> extends YCollection<T>, List<T> {

    YList<T> filter(Predicate<? super T> predicate);
    <R extends T> YList<R> filterByClass(Class<R> clazz);
    <R> YList<R> map(Function<? super T, ? extends R> mapper);
    <R> YList<R> flatMap(Function<? super T, ? extends Collection<? extends R>> mapper);

    //TODO groupBy
    //http://kotlinlang.org/api/latest/jvm/stdlib/kotlin/group-by.html

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
}
