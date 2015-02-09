package yk.lang.yads;

import yk.jcommon.collections.Tuple;
import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.utils.BadException;
import yk.jcommon.utils.Reflector;
import yk.jcommon.utils.Tab;
import yk.jcommon.utils.Util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 05/02/15
 * Time: 23:22
 */
public class YADSSerializer {
    public static YList<String> namespaces = al("", "test", "yk.lang.yads");
    private static Tab tab = new Tab("  ");

    public static String serialize(Object o) {
        YSet<String> namespaces = hs();
        String result = serialize(namespaces, o);
        return (namespaces.isEmpty() ? "" : "import= " + Util.join(namespaces, ", ") + "\n") + result;
    }

    private static String serialize(YSet<String> namespaces, Object o) {
        if (o == null) return "null";
        if (o instanceof Number) return o + "";
        if (o instanceof String) return "'" + Util.ESCAPE_YADS_SINGLE_QUOTES.translate((String) o) + "'";//TODO don't escape ' for " and vice versa?
        if (o instanceof Boolean) return o + "";
        if (o instanceof List) return serializeList(namespaces, (List) o);
        if (o instanceof Map) return serializeMap(namespaces, (Map) o);
        if (o.getClass().isEnum()) return "" + o;
        if (o.getClass().isArray()) {
            if (o.getClass().getComponentType().isArray()) {
                String result = "";
                result += "{\n";
                tab.inc();
                int length = Array.getLength(o);
                for (int i = 0; i < length; i++) result += tab + serialize(namespaces, Array.get(o, i));
                tab.dec();
                result += tab + "}\n";
                return result;

            } else {
                String result = "";
                result += "{";
                int length = Array.getLength(o);
                tab.inc();//just in case there will be complex structures
                for (int i = 0; i < length; i++) result += (i > 0 ? " " : "") + serialize(namespaces, Array.get(o, i));
                tab.dec();
                result += "}\n";
                return result;
            }
        }
        return serializeClass(namespaces, o);
    }

    private static String serializeMap(YSet<String> namespaces, Map o) {//TODO add specific Map type if not just HashMap or YHashMap
        String result = "";
        result += tab + "{\n";
        tab.inc();
        for (Object key : o.keySet()) result += tab + serialize(namespaces, key) + "= " + serialize(namespaces, o.get(key)) + "\n";
        tab.dec();
        result += tab + "}\n";
        return result;
    }

    private static String serializeList(YSet<String> namespaces, List o) {//TODO add specific List type if not just ArrayList or YArrayList
        String result = "";
        result += tab + "{\n";
        tab.inc();
        for (Object el : o) result += tab + serialize(namespaces, el) + "\n";
        tab.dec();
        result += tab + "}\n";
        return result;
    }

    private static String serializeClass(YSet<String> namespaces, Object o) {
        String result = "";
        String name = o.getClass().getName();
        if (name.contains(".")) {
            int lastPointIndex = name.lastIndexOf(".");
            namespaces.add(name.substring(0, lastPointIndex));
            name = name.substring(lastPointIndex + 1);
        }
        result += tab + name + " {\n";
        tab.inc();

        for (Field field : Reflector.getAllNonStaticFieldsReversed(o.getClass()).values()) {
            Object value = Reflector.get(o, field);
            if (value == null) continue;
            //TODO other default
            result += tab + field.getName() + "= " + serialize(namespaces, value) + "\n";
        }


        tab.dec();
        result += "\n" + tab + "}\n";
        return result;
    }

    public static Object deserialize(String s) {
        return deserializeList(YADSParser.parseList(s));
    }

    public static Object deserializeList(YList l) {
        return deserializeClass(null, new YADClass(null, l));
    }

    public static Object deserializeClass(Object yad) {
        return deserializeClass(null, yad);
    }

    public static Object deserializeClass(Class clazz, Object yad) {
        if (yad == null) return null;
        if (clazz != null && clazz.isArray()) return parseArray(clazz, (YADClass) yad);
        if (clazz != null && clazz.isEnum()) return Enum.valueOf(clazz, (String)yad);
        if (clazz == Integer.class || clazz == int.class) return ((Number) yad).intValue();
        if (clazz == Float.class || clazz == float.class) return ((Number) yad).floatValue();
        if (clazz == Long.class || clazz == long.class) return ((Number) yad).longValue();
        if (clazz == Double.class || clazz == double.class) return ((Number) yad).doubleValue();
        if (yad instanceof YADClass) return deserializeClassImpl(clazz, (YADClass) yad);
        else return yad;
    }

    private static Object parseArray(Class clazz, YADClass yad) {
        Object result = Array.newInstance(clazz.getComponentType(), yad.body.size());
        for (int i = 0; i < yad.body.size(); i++) Array.set(result, i, deserializeClass(clazz.getComponentType(), yad.body.get(i)));
        return result;
    }

    private static Object deserializeClassImpl(Class clazz, YADClass yad) {
        if (yad.name != null) {
            clazz = null;
            for (String p : namespaces) {
                try {
                    clazz = Class.forName((p.length() > 0 ? p + "." : "") + yad.name);
                    break;
                } catch (ClassNotFoundException ignore) {
                }
            }
        }
        //TODO assert found class by class name, extends field?
        Object instance = clazz == null ? null : Reflector.newInstanceArgless(clazz);
        YList array = al();
        YList<Tuple> tuples = al();
        for (Object element : yad.body) {
            if (element instanceof Tuple) {
                Tuple<String, Object> t = (Tuple<String, Object>) element;
                Object value = deserializeClass(clazz == null ? null : Reflector.getField(clazz, t.a).getType(), t.b);
                if (t.a.equals("import")) {
                    if (value instanceof String) namespaces.add((String) value);
                    else if (value instanceof YList) namespaces.addAll((YList<String>) value);
                    else BadException.die("unknown data " + value + " for import");
                } else {
                    tuples.add(new Tuple(t.a, value));
                }
            } else {
                array.add(deserializeClass(null, element));
            }
        }
        if (clazz != null && clazz.isEnum()) {
            if (!tuples.isEmpty()) BadException.die("enum can't contain tuples");
            if (array.size() != 1) BadException.die("enum must be stated by one element");
            return Enum.valueOf(clazz, (String)array.get(0));
        }
        if (instance != null) {
            for (Tuple t : tuples) Reflector.set(instance, (String) t.a, t.b);
            if (!array.isEmpty()) Reflector.invokeMethod(instance, "init", array);
            return instance;
        }
        if (yad.name != null || (!array.isEmpty() && !tuples.isEmpty())) {
            return new YADClass(yad.name, tuples.join(array));
        }
        if (array.isEmpty()) {
            YMap result = hm();
            for (Tuple t : tuples) result.put(t.a, t.b);
            return result;
        }
        return array;
    }
}
