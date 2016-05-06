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

    public static YSet<YMap<String, Object>> match(Object data, Object pattern) {
        return match(data, pattern, hm());
    }

    public static YSet<YMap<String, Object>> match(Object data, Object pattern, YMap<String, Object> cur) {
        //TODO auto-resolve at run-time
        if (data == null) {
            return pattern == null ? hs(cur) : hs();
            //TODO variable matches null
        }
        //TODO list
        //TODO map
        //TODO set
        if (pattern instanceof VarCalc) return ((VarCalc)pattern).calc(cur);
        if (pattern instanceof And) return matchAnd(data, ((And) pattern).elements, cur);
        if (pattern instanceof Not) return match(data, (Not) pattern, cur);
        if (pattern instanceof ByIndex) return match(data, (ByIndex) pattern, cur);
        if (pattern instanceof Var) return match(data, (Var) pattern, cur);
        if (pattern instanceof Property) return match(data, (Property) pattern, cur);
        if (pattern instanceof Deeper) return match(data, (Deeper) pattern, cur);
        if (pattern instanceof Any) return hs(cur);

        if (Util.equalsWithNull(data, pattern)) return hs(cur);
        return hs();
    }

    public static YSet<YMap<String, Object>> matchAnd(Object data, YList pattern, YMap<String, Object> cur) {
        if (pattern.isEmpty()) return hs(cur);
        YSet<YMap<String, Object>> result = hs();
        Object car = pattern.car();
        YSet<YMap<String, Object>> mm = match(data, car, cur);
        for (YMap<String, Object> m : mm) result.addAll(matchAnd(data, pattern.cdr(), m));
        return result;
    }

    public static YSet<YMap<String, Object>> match(Object data, Deeper pattern, YMap<String, Object> cur) {
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

    public static YList<Field> getAllFieldsInHierarchy(Class source) {
        YList<Field> result = al();
        while (!source.isAssignableFrom(Object.class)) {
            for (Field f : source.getDeclaredFields()) {
                result.add(f);
            }
            source = source.getSuperclass();
        }
        return result;
    }

    public static YList<Method> getAllMethodsInHierarchy(Class source) {
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

    public static YSet<YMap<String, Object>> match(Object data, Not pattern, YMap<String, Object> cur) {
        if (match(data, pattern.rest, cur).notEmpty()) return hs();
        return hs(cur);
    }

    public static YSet<YMap<String, Object>> match(Object data, ByIndex pattern, YMap<String, Object> cur) {
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
            if (!(index instanceof Var) && !(index instanceof Any)) BadException.shouldNeverReachHere("" + index);

            for (int i = 0; i < Array.getLength(data); i++) {
                for (YMap<String, Object> m : match(Array.get(data, i), pattern.rest, index instanceof Var ? cur.with(((Var) index).name, i) : cur)) {
//                    if (pattern.index == null) result.add(m);
//                    else result.addAll(match(i, index, m));
                    result.add(m);
                }
            }
            return result;
        }
        return hs();
    }

    private static Object resolve(Object p, YMap<String, Object> cur) {
        if (p instanceof Var && cur.containsKey(((Var)p).name)) return cur.get(((Var)p).name);
        return p;
    }

    public static YSet<YMap<String, Object>> match(Object data, Property pattern, YMap<String, Object> cur) {
        return matchProps(data, pattern.pp, cur);
    }

    public static YSet<YMap<String, Object>> matchProps(Object data, YMap<String, Property.PropertyDesc> pp, YMap<String, Object> cur) {
        if (pp.isEmpty()) return hs(cur);
        Property.PropertyDesc car = pp.car().b;
        YMap<String, Property.PropertyDesc> cdr = pp.cdr();
        YSet<YMap<String, Object>> result = hs();

        Tuple<Boolean, Object> tuple = car.isMethod == null ? getValue(data, car.name) : car.isMethod ? getMethodValue(data, car.name) : getFieldValue(data, car.name);
        if (!tuple.a) return hs();
        for (YMap<String, Object> m : match(tuple.b, car.value, cur)) result.addAll(matchProps(data, cdr, m));
        return result;
    }

    public static Tuple<Boolean, Object> getValue(Object o, String name) {
        Field field = getField(o, name);
        if (field != null) return new Tuple<>(true, Reflector.get(o, field));
        try {
            Method method = getMethod(o, name);
            if (method != null) return new Tuple<>(true, method.invoke(o));
        } catch (Exception ignore) {
        }
        return new Tuple<>(false, null);
    }

    public static Tuple<Boolean, Object> getMethodValue(Object o, String name) {
        try {
            Method method = getMethod(o, name);
            if (method != null) return new Tuple<>(true, method.invoke(o));
        } catch (Exception ignore) {
        }
        return new Tuple<>(false, null);
    }

    public static Tuple<Boolean, Object> getFieldValue(Object o, String name) {
        Field field = getField(o, name);
        if (field != null) return new Tuple<>(true, Reflector.get(o, field));
        return new Tuple<>(false, null);
    }

    public static YSet<YMap<String, Object>> match(Object data, Var pattern, YMap<String, Object> cur) {
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
    public static YMap<String, Method> METHODS = hm();
    private static Method getMethod(Object o, String name) {
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
