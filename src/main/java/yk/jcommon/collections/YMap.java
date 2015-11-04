package yk.jcommon.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 8/13/14
 * Time: 10:34 PM
 */
public interface YMap<K, V> extends Map<K, V> {
    YMap<K, V> filter(BiPredicate<? super K, ? super V> predicate);
    <V2> YList<V2> mapToList(BiFunction<? super K, ? super V, V2> mapper);
    <V2> YMap<K, V2> map(BiFunction<? super K, ? super V, V2> mapper);

    Tuple<K, V> car();
    YMap<K, V> cdr();
    Tuple<K, V> first();
    Tuple<K, V> last();

    //TODO first by predicate

    Tuple<K, V> max(BiFunction<K, V, Float> evaluator);
    V maxValue(Function<V, Float> evaluator);
    Tuple<K, V> min(BiFunction<K, V, Float> evaluator);
    V minValue(Function<V, Float> evaluator);

    boolean isAll(BiPredicate<K, V> predicate);
    boolean isAny(BiPredicate<K, V> predicate);

    V getOr(K key, V cur);

    @Override
    YSet<K> keySet();

    @Override
    YCollection<V> values();

    YMap<K, V> with(K k, V v);
    YMap<K, V> with(K k, V v, Object... other);
    YMap<K, V> with(Map<K, V> kv);

    YMap<K, V> without(K pKey);
    YMap<K, V> without(Collection<K> keys);

    YMap<K, V> sorted(Comparator<Entry<K, V>> comparator);
    YMap<K, V> sortedBy(BiFunction<K, V, Comparable> evaluator);

    YMap<K, V> take(int n);
    String toString(String elementsInfix, String kvInfix);
    String toString(String elementsInfix, BiFunction<K, V, String> toStringFunction);

    default boolean containsAll(Map<K, V> whom) {
        for (Map.Entry<K, V> entry : whom.entrySet()) {
            if (!containsKey(entry.getKey()) || !get(entry.getKey()).equals(entry.getValue())) return false;
        }
        return true;
    }

    default boolean notEmpty() {
        return !isEmpty();
    }
}
