package yk.jcommon.collections;

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

    //<K2, V2> YMap<K2, V2> map(BiFunction<? super K, ? super V, Tuple<? extends K2, ? extends V2>> mapper);
    //<K2, V2> YMap<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends List<Tuple<? extends K2, ? extends V2>>> mapper);

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
    YArrayList<V> values();

    //TODO with
    //TODO without
    YMap<K, V> append(K k, V v);
    YMap<K, V> append(Map<K, V> kv);
    YMap<K, V> append(K k, V v, Object... other);

    YMap<K, V> sub(K pKey);

    //TODO sorted() - sorted keys
    //TODO sortedValues() - sorted values
}
