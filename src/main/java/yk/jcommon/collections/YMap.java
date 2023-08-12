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
    Function PASS = o -> o;
    BiFunction PASS_FIRST = (o, o2) -> o;
    BiFunction PASS_SECOND = (o, o2) -> o2;

    //TODO notContainsKey()
    YMap<K, V> filter(BiPredicate<? super K, ? super V> predicate);
    <V2> YList<V2> mapToList(BiFunction<? super K, ? super V, V2> mapper);

    <K2, V2> YMap<K2, V2> map(Function<? super K, K2> keyMapper, Function<? super V, V2> valueMapper);
    <K2, V2> YMap<K2, V2> map(BiFunction<? super K, ? super V, K2> keyMapper, BiFunction<? super K, ? super V, V2> valueMapper);
    @Deprecated //use mapValues instead
    default <V2> YMap<K, V2> map(Function<? super V, V2> mapper) {return mapValues(mapper);}
    @Deprecated //use mapValues instead
    default <V2> YMap<K, V2> map(BiFunction<? super K, ? super V, V2> mapper) {return mapValues(mapper);}
    default <K2> YMap<K2, V> mapKeys(BiFunction<? super K, ? super V, K2> mapper) {return map(mapper, PASS_SECOND);}
    default <K2> YMap<K2, V> mapKeys(Function<? super K, K2> mapper) {return map(mapper, PASS);}
    default <V2> YMap<K, V2> mapValues(BiFunction<? super K, ? super V, V2> mapper) {return map(PASS_FIRST, mapper);}
    default <V2> YMap<K, V2> mapValues(Function<? super V, V2> mapper) {return map(PASS, mapper);}

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

    default void putAll(K k, V v, Object... other) {
        put(k, v);
        for (int i = 0; i < other.length; i += 2) put((K) other[i], (V) other[i + 1]);
    }

    default boolean containsAll(Map<K, V> whom) {
        for (Map.Entry<K, V> entry : whom.entrySet()) {
            if (!containsKey(entry.getKey()) || !get(entry.getKey()).equals(entry.getValue())) return false;
        }
        return true;
    }

    default boolean notEmpty() {
        return !isEmpty();
    }

    default String toString(String elementsInfix) {
        return toString(elementsInfix, ":");
    }

    default String toString(String elementsInfix, String kvInfix) {
        boolean was = false;
        StringBuilder sb = new StringBuilder("");
        for (K k : keySet()) {
            if (was) sb.append(elementsInfix);
            sb.append(k).append(kvInfix).append(get(k));
            was = true;
        }
        return sb.toString();
    }

    default String toString(String elementsInfix, BiFunction<K, V, String> toStringFunction) {
        boolean was = false;
        StringBuilder sb = new StringBuilder("");
        for (K k : keySet()) {
            if (was) sb.append(elementsInfix);
            sb.append(toStringFunction.apply(k, get(k)));
            was = true;
        }
        return sb.toString();
    }

    default YMap<K, V> assertSize(int s) {
        if (size() != s) throw new RuntimeException("Expected size " + s + " but was " + size());
        return this;
    }
}
