package yk.jcommon.collections;

import yk.jcommon.utils.BadException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 10/1/14
 * Time: 11:50 PM
 */
public class YHashMap<K, V> extends HashMap<K, V> implements YMap<K, V> {


    //public static YHashMap map(Object... oo) {
    //    YHashMap result = new YHashMap();
    //    for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
    //    return result;
    //}

    public static <K, V> YHashMap<K, V> hm(K k, V v, Object... oo) {//TODO assert repeating keys
        YHashMap result = new YHashMap();
        result.put(k, v);
        for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
        return result;
    }

    public static <K, V> YHashMap<K, V> toYMap(Map<K, V> source) {
        YHashMap result = new YHashMap();
        result.putAll(source);
        return result;
    }

    public static <K, V> YHashMap<K, V> hm() {
        return new YHashMap<K, V>();
    }

    @Override
    public Map<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        return null;
    }

    @Override
    public <K2, V2> YMap<K2, V2> map(BiFunction<? super K, ? super V, Tuple<? extends K2, ? extends V2>> mapper) {
        YMap<K2, V2> result = hm();
        for (Entry<K, V> e : entrySet()) {
            Tuple<? extends K2, ? extends V2> t = mapper.apply(e.getKey(), e.getValue());
            //TODO assert repeating keys?
            result.put(t.a, t.b);
        }
        return result;
    }

    @Override
    public <K2, V2> YMap<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends List<Tuple<? extends K2, ? extends V2>>> mapper) {
        return null;
    }

    @Override
    public Tuple<K, V> car() {
        Entry<K, V> next = entrySet().iterator().next();
        return new Tuple<>(next.getKey(), next.getValue());
    }

    @Override
    public Map<K, V> cdr() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> first() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> last() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> max() {
        throw BadException.notImplemented();
    }

    @Override
    public Tuple<K, V> min() {
        throw BadException.notImplemented();
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
    public YArrayList<V> values() {
        //TODO wrapper
        return YArrayList.toYList(super.values());
    }

    @Override
    public YMap<K, V> append(K k, V v) {
        YMap<K, V> result = new YHashMap<>();
        result.putAll(this);
        result.put(k, v);
        return result;
    }

    @Override
    public YMap<K, V> append(Map<K, V> kv) {
        YMap<K, V> result = new YHashMap<>();
        result.putAll(this);
        result.putAll(kv);
        return result;
    }

    @Override
    public YMap<K, V> append(K k, V v, Object... other) {
        YMap<K, V> result = append(k, v);
        for (int i = 0; i < other.length; i += 2) result.put((K)other[i], (V)other[i+1]);
        return result;
    }

    @Override
    public YMap<K, V> sub(K pKey) {
        YMap<K, V> result = new YHashMap<>();
        result.putAll(this);
        result.remove(pKey);
        return result;
    }
}
