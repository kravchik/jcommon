package yk.jcommon.collections;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 10:34 PM
 */
public interface YMap<K, V> extends Map<K, V> {
    Map<K, V> filter(BiPredicate<? super K, ? super V> predicate);
    <V2> YList<V2> mapToList(BiFunction<? super K, ? super V, V2> mapper);
    <V2> YMap<K, V2> map(BiFunction<? super K, ? super V, V2> mapper);

    Tuple<K, V> car();
    Map<K, V> cdr();
    Tuple<K, V> first();
    Tuple<K, V> last();

    Tuple<K, V> max();
    Tuple<K, V> min();

    V getOr(K key, V cur);

    @Override
    YSet<K> keySet();

    @Override
    YList<V> values();

    YMap<K, V> with(K k, V v);
    YMap<K, V> with(K k, V v, Object... other);
    YMap<K, V> with(Map<K, V> kv);

    YMap<K, V> without(K pKey);
    YMap<K, V> without(Collection<K> keys);

    //TODO Function<T, Comparable<T>>

    //TODO sorted() - sorted keys
    //TODO sortedValues() - sorted values
}
