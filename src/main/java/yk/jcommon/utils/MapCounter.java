package yk.jcommon.utils;

import java.util.Map;

import static yk.jcommon.utils.Util.map;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/10/14
 * Time: 10:13 PM
 */
public class MapCounter<T> {
    public Map<T, Integer> mapa = map();

    public void add(T t) {
        mapa.put(t, get(t) + 1);
    }

    public int get(T t) {
        Object oldValue = mapa.get(t);
        return oldValue == null ? 0 : (Integer) oldValue;
    }
}
