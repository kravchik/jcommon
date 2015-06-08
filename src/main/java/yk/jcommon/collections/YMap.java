package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
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

    YMap<K, V> sorted(Comparator<Entry<K, V>> comparator);
    YMap<K, V> sortedBy(BiFunction<K, V, Comparable> evaluator);

    // пока 0.110 - не используется, туда и залить!
    YMap<K, V> take(int n);
    String toString(String kvInfix, String elementsInfix);
    //TODO toString(infix, (k, v) -> string)
}
