package yk.jcommon.match2;

import yk.jcommon.collections.Tuple;
import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.utils.BadException;
import yk.jcommon.utils.Reflector;
import yk.jcommon.utils.Util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 28/10/15
 * Time: 16:54
 */
public class Matcher {

    public YSet<YMap<String, Object>> match(Object data, Object pattern) {
        return match(data, pattern, hm());
    }

    public YSet<YMap<String, Object>> match(Object data, Object pattern, YMap<String, Object> cur) {
        //TODO auto-resolve at run-time
        if (data == null) {
            return pattern == null ? hs(cur) : hs();
            //TODO variable matches null
        }
        //TODO replace everything with CustomMatch
        //TODO array
        //TODO set
        if (pattern instanceof List) return matchList(data, (List) pattern, cur);
        if (pattern instanceof Map) return matchMap(data, (Map) pattern, cur);

        if (pattern instanceof MatchVarCalc) return ((MatchVarCalc)pattern).calc(cur);
        if (pattern instanceof MatchAnd) return matchAnd(data, ((MatchAnd) pattern).elements, cur);
        if (pattern instanceof MatchNot) return match(data, (MatchNot) pattern, cur);
        if (pattern instanceof MatchVar) return match(data, (MatchVar) pattern, cur);
        if (pattern instanceof MatchProperty) return match(data, (MatchProperty) pattern, cur);
        if (pattern instanceof MatchDeeper) return match(data, (MatchDeeper) pattern, cur);
        if (pattern instanceof MatchAny) return hs(cur);
        if (pattern instanceof MatchCustom) return ((MatchCustom) pattern).match(this, data, cur);

        if (Util.equalsWithNull(data, pattern)) return hs(cur);
        return hs();
    }

    private YSet<YMap<String, Object>> matchList(Object data, List pattern, YMap<String, Object> cur) {
        if (!(data instanceof List)) return hs();
        List dl = (List) data;
        if (pattern.size() != dl.size()) return hs();

        YSet<YMap<String, Object>> last = hs(cur);

        for (int i = 0; i < pattern.size(); i++) {
            Object d = dl.get(i);
            Object p = pattern.get(i);
            if (last.isEmpty()) break;
            YSet<YMap<String, Object>> newResult = hs();
            for (YMap<String, Object> map : last) newResult.addAll(match(d, p, map));
            last = newResult;
        }

        return last;
    }

    //TODO match keys
    //  it could generate a lot, so maybe by some KeyMapsCustomMatch
    private YSet<YMap<String, Object>> matchMap(Object dObj, Map pattern, YMap<String, Object> cur) {
        if (!(dObj instanceof Map)) return hs();
        Map data = (Map) dObj;
        if (!data.keySet().containsAll(pattern.keySet())) return hs();

        YSet<YMap<String, Object>> last = hs(cur);
        for (Object pk : pattern.keySet()) {
            Object pv = pattern.get(pk);
            Object dv = data.get(pk);

            YSet<YMap<String, Object>> newResult = hs();
            for (YMap<String, Object> map : last) newResult.addAll(match(dv, pv, map));
            last = newResult;
        }
        return last;
    }


    public YSet<YMap<String, Object>> matchAnd(Object data, YList pattern, YMap<String, Object> cur) {
        if (pattern.isEmpty()) return hs(cur);
        YSet<YMap<String, Object>> result = hs();
        Object car = pattern.car();
        YSet<YMap<String, Object>> mm = match(data, car, cur);
        for (YMap<String, Object> m : mm) result.addAll(matchAnd(data, pattern.cdr(), m));
        return result;
    }

    public YSet<YMap<String, Object>> match(Object data, MatchDeeper pattern, YMap<String, Object> cur) {
        YSet<YMap<String, Object>> result = hs();
        result.addAll(match(data, pattern.rest, cur));
        for (Object accessorPattern : pattern.accessorPatterns) {
            YSet<YMap<String, Object>> variants = match(data, accessorPattern);
            for (YMap<String, Object> variant : variants) {
                result.addAll(match(variant.get("access"), pattern.rest, cur));
                result.addAll(match(variant.get("access"), pattern, cur));
            }
        }
        return result;
    }

    public YList<Field> getAllFieldsInHierarchy(Class source) {
        YList<Field> result = al();
        while (!source.isAssignableFrom(Object.class)) {
            for (Field f : source.getDeclaredFields()) {
                result.add(f);
            }
            source = source.getSuperclass();
        }
        return result;
    }

    public YList<Method> getAllMethodsInHierarchy(Class source) {
        YList<Method> result = al();
        while (!source.isAssignableFrom(Object.class)) {
            for (Method f : source.getDeclaredMethods()) {
                result.add(f);
            }
            source = source.getSuperclass();
        }
        return result;
    }

    private static boolean tryDeeper(Object data) {
        if (data == null) return false;
        if (data.getClass().isPrimitive()) return false;
        if (data.getClass().getName().startsWith("java.lang.")) return false;
        if (data.getClass().isEnum()) return false;
        if (data instanceof String) return false;
        if (data instanceof Boolean) return false;
        return true;
    }

    public YSet<YMap<String, Object>> match(Object data, MatchNot pattern, YMap<String, Object> cur) {
        if (match(data, pattern.rest, cur).notEmpty()) return hs();
        return hs(cur);
    }

    public YSet<YMap<String, Object>> match(Object data, MatchByIndex pattern, YMap<String, Object> cur) {
        if (data instanceof List) {
            List l = (List) data;
            Object index = resolve(pattern.index, cur);

            if (pattern.index instanceof Number) return match(l.get(((Number) index).intValue()), pattern.rest, cur);
            YSet<YMap<String, Object>> result = hs();
            for (int i = 0; i < l.size(); i++) {
                for (YMap<String, Object> m : match(l.get(i), pattern.rest, cur)) {
                    if (pattern.index == null) result.add(m);
                    else result.addAll(match(i, index, m));
                }
            }
            return result;
        }
        if (data.getClass().isArray()) {
            Object index = resolve(pattern.index, cur);
            if (index instanceof Number) return match(Array.get(data, ((Number) index).intValue()), pattern.rest, cur);
            YSet<YMap<String, Object>> result = hs();
            if (!(index instanceof MatchVar) && !(index instanceof MatchAny)) BadException.shouldNeverReachHere("" + index);

            for (int i = 0; i < Array.getLength(data); i++) {
                for (YMap<String, Object> m : match(Array.get(data, i), pattern.rest, index instanceof MatchVar ? cur.with(((MatchVar) index).name, i) : cur)) {
//                    if (pattern.index == null) result.add(m);
//                    else result.addAll(match(i, index, m));
                    result.add(m);
                }
            }
            return result;
        }
        return hs();
    }

    public Object resolve(Object p, YMap<String, Object> cur) {
        if (p instanceof MatchVar && cur.containsKey(((MatchVar)p).name)) return cur.get(((MatchVar)p).name);
        return p;
    }

    public YSet<YMap<String, Object>> match(Object data, MatchProperty pattern, YMap<String, Object> cur) {
        return matchProps(data, pattern.pp, cur);
    }

    public YSet<YMap<String, Object>> matchProps(Object data, YMap<String, MatchProperty.PropertyDesc> pp, YMap<String, Object> cur) {
        if (pp.isEmpty()) return hs(cur);
        MatchProperty.PropertyDesc car = pp.car().b;
        YMap<String, MatchProperty.PropertyDesc> cdr = pp.cdr();
        YSet<YMap<String, Object>> result = hs();

        Tuple<Boolean, Object> tuple = car.isMethod == null ? getValue(data, car.name) : car.isMethod ? getMethodValue(data, car.name) : getFieldValue(data, car.name);
        if (!tuple.a) return hs();
        for (YMap<String, Object> m : match(tuple.b, car.value, cur)) result.addAll(matchProps(data, cdr, m));
        return result;
    }

    public Tuple<Boolean, Object> getValue(Object o, String name) {
        Field field = getField(o, name);
        if (field != null) return new Tuple<>(true, Reflector.get(o, field));
        try {
            Method method = getMethod(o, name);
            if (method != null) return new Tuple<>(true, method.invoke(o));
        } catch (Exception ignore) {
        }
        return new Tuple<>(false, null);
    }

    public Tuple<Boolean, Object> getMethodValue(Object o, String name) {
        try {
            Method method = getMethod(o, name);
            if (method != null) return new Tuple<>(true, method.invoke(o));
        } catch (Exception ignore) {
        }
        return new Tuple<>(false, null);
    }

    public Tuple<Boolean, Object> getFieldValue(Object o, String name) {
        Field field = getField(o, name);
        if (field != null) return new Tuple<>(true, Reflector.get(o, field));
        return new Tuple<>(false, null);
    }

    public YSet<YMap<String, Object>> match(Object data, MatchVar pattern, YMap<String, Object> cur) {
        if (cur.containsKey(pattern.name)) {
            if (cur.get(pattern.name).equals(data)) return hs(cur); else return hs();
//            return match(cur.get(pattern
        }
        YMap<String, Object> resMap = cur.with(pattern.name, data);
        if (pattern.rest != null) return match(data, pattern.rest, resMap);
        else return hs(resMap);
    }




    //OPTIMIZATIONS
    public static YMap<String, Field> FIELDS = hm();
    private static Field getField(Object o, String name) {
        String key = o.getClass().toString() + ":" + name;
        if (FIELDS.containsKey(key)) return FIELDS.get(key);
        Field result = Reflector.getField(o.getClass(), name);
        FIELDS.put(key, result);
        return result;
    }
    public YMap<String, Method> METHODS = hm();
    public Method getMethod(Object o, String name) {
        String key = o.getClass().toString() + ":" + name;
        if (METHODS.containsKey(key)) return METHODS.get(key);
        Method result = null;
        try {
            result = o.getClass().getMethod(name);
        } catch (NoSuchMethodException ignore) {}
        if (result != null) result.setAccessible(true);
        METHODS.put(key, result);
        return result;
    }

}
