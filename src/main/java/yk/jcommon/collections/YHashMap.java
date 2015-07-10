package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static yk.jcommon.collections.YArrayList.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 10/1/14
 * Time: 11:50 PM
 */
public class YHashMap<K, V> extends LinkedHashMap<K, V> implements YMap<K, V> {


    //public static YHashMap map(Object... oo) {
    //    YHashMap result = new YHashMap();
    //    for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
    //    return result;
    //}

    public static <K, V> YHashMap<K, V> hm(K k, V v, Object... oo) {
        YHashMap result = new YHashMap();
        if (result.containsKey(k)) BadException.die("repeating key: " + k);
        result.put(k, v);
        for (int i = 0; i < oo.length; i += 2) result.put(oo[i], oo[i + 1]);
        return result;
    }

    public static <K, V> YHashMap<K, V> toYMap(Map<K, V> source) {
        YHashMap result = new YHashMap();
        result.putAll(source);
        return result;
    }

    public static <K, V> YHashMap<K, V> hm() {
        return new YHashMap<>();
    }

    @Override
    public YMap<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        YMap<K, V> result = hm();
        for (Map.Entry<K, V> entry : this.entrySet())
            if (predicate.test(entry.getKey(), entry.getValue())) result.put(entry.getKey(), entry.getValue());
        return result;
    }

    @Override
    public <V2> YList<V2> mapToList(BiFunction<? super K, ? super V, V2> mapper) {
        YList<V2> result = al();
        for (Map.Entry<K, V> entry : this.entrySet()) {
            result.add(mapper.apply(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public <V2> YMap<K, V2> map(BiFunction<? super K, ? super V, V2> mapper) {
        YMap<K, V2> result = hm();
        for (Map.Entry<K, V> entry : this.entrySet()) {
            result.put(entry.getKey(), mapper.apply(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public Tuple<K, V> car() {
        Map.Entry<K, V> next = entrySet().iterator().next();
        return new Tuple<>(next.getKey(), next.getValue());
    }

    @Override
    public YMap<K, V> cdr() {
        return without(car().a);
    }

    @Override
    public Tuple<K, V> first() {
        return car();
    }

    @Override
    public Tuple<K, V> last() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> max(BiFunction<K, V, Float> evaluator) {
        float maxValue = Float.MIN_VALUE;
        K maxKey = null;
        for (K k : super.keySet()) {
            float curValue = evaluator.apply(k, get(k));
            if (curValue > maxValue) {
                maxKey = k;
                maxValue = curValue;
            }
        }
        return new Tuple<>(maxKey, get(maxKey));
    }

    @Override
    public V maxValue(Function<V, Float> evaluator) {
        float maxValue = Float.MIN_VALUE;
        V mv = null;
        for (V v : super.values()) {
            float curValue = evaluator.apply(v);
            if (curValue > maxValue) {
                mv = v;
                maxValue = curValue;
            }
        }
        return mv;
    }

    @Override
    public Tuple<K, V> min(BiFunction<K, V, Float> evaluator) {
        float minValue = Float.MAX_VALUE;
        K minKey = null;
        for (K k : super.keySet()) {
            float curValue = evaluator.apply(k, get(k));
            if (curValue < minValue) {
                minKey = k;
                minValue = curValue;
            }
        }
        return new Tuple<>(minKey, get(minKey));
    }

    @Override
    public V minValue(Function<V, Float> evaluator) {
        float minValue = Float.MAX_VALUE;
        V mv = null;
        for (V v : super.values()) {
            float curValue = evaluator.apply(v);
            if (curValue < minValue) {
                mv = v;
                minValue = curValue;
            }
        }
        return mv;
    }

    @Override
    public boolean isAll(BiPredicate<K, V> predicate) {
        for (Map.Entry<K, V> entry : super.entrySet()) if (!predicate.test(entry.getKey(), entry.getValue())) return false;
        return true;
    }

    @Override
    public boolean isAny(BiPredicate<K, V> predicate) {
        for (Map.Entry<K, V> entry : super.entrySet()) if (predicate.test(entry.getKey(), entry.getValue())) return true;
        return false;
    }

    @Override
    public V getOr(K key, V cur) {
        V result = get(key);
        return result == null ? cur : result;
    }

    @Override
    public YSet<K> keySet() {
        return new YHashSetWrapper<>(super.keySet());
    }

    @Override
    public YCollection<V> values() {
        return new YCollectionWrapper<>(super.values());
    }

    @Override
    public YMap<K, V> with(K k, V v) {
        YMap<K, V> result = hm();
        result.putAll(this);
        result.put(k, v);
        return result;
    }

    @Override
    public YMap<K, V> with(K k, V v, Object... other) {
        YMap<K, V> result = with(k, v);
        for (int i = 0; i < other.length; i += 2) result.put((K) other[i], (V) other[i + 1]);
        return result;
    }

    @Override
    public YMap<K, V> with(Map<K, V> kv) {
        YMap<K, V> result = hm();
        result.putAll(this);
        result.putAll(kv);
        return result;
    }

    @Override
    public YMap<K, V> without(K pKey) {
        YMap<K, V> result = new YHashMap<>();
        result.putAll(this);
        result.remove(pKey);
        return result;
    }

    @Override
    public YMap<K, V> without(Collection<K> keys) {
        YMap<K, V> result = toYMap(this);
        for (K key : keys) result.remove(key);
        return result;
    }

    @Override
    public YMap<K, V> sorted(Comparator<Map.Entry<K, V>> comparator) {
        YMap<K, V> result = hm();
        for (Map.Entry<K, V> entry : toYList(this.entrySet()).sorted(comparator)) result.put(entry.getKey(), entry.getValue());
        return result;
    }

    @Override
    public YMap<K, V> sortedBy(BiFunction<K, V, Comparable> evaluator) {
        List<Temp<K, V>> temp = al();
        for (Map.Entry<K, V> entry : this.entrySet()) temp.add(new Temp<>(entry, evaluator));
        Collections.sort(temp);
        YMap<K, V> result = hm();
        for (Temp<K, V> t : temp) result.put(t.entry.getKey(), t.entry.getValue());
        return result;
    }

    @Override
    public YMap<K, V> take(int n) {
        YMap<K, V> result = hm();
        int count = 0;
        for (K k : keySet()) {
            if (count++ >= n) break;
            result.put(k, get(k));
        }
        return result;
    }

    @Override
    public String toString(String elementsInfix, String kvInfix) {
        boolean was = false;
        StringBuilder sb = new StringBuilder("");
        for (K k : keySet()) {
            if (was) sb.append(elementsInfix);
            sb.append(k).append(kvInfix).append(get(k));
            was = true;
        }
        return sb.toString();
    }

    @Override
    public String toString(String elementsInfix, BiFunction<K, V, String> toStringFunction) {
        boolean was = false;
        StringBuilder sb = new StringBuilder("");
        for (K k : keySet()) {
            if (was) sb.append(elementsInfix);
            sb.append(toStringFunction.apply(k, get(k)));
            was = true;
        }
        return sb.toString();
    }

    private static class Temp<K, V> implements Comparable<Temp<K, V>> {
        Comparable evaluation;
        Map.Entry<K, V> entry;

        private Temp(Map.Entry<K, V> entry, BiFunction<K, V, Comparable> evaluator) {
            this.entry = entry;
            evaluation = evaluator.apply(entry.getKey(), entry.getValue());
        }

        @Override
        public int compareTo(Temp<K, V> o) {
            return evaluation.compareTo(o.evaluation);
        }
    }
}
