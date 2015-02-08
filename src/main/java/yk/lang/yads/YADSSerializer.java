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

    public static String toMida(Object o) {
        YSet<String> namespaces = hs();
        String result = toMidaClass(namespaces, o);
        return "import: " + Util.join(namespaces, ", ") + "\n" + result;
    }

    private static String toMidaObject(YSet<String> namespaces, Object o) {
        if (o instanceof Number) return o + "";
        if (o instanceof String) return "'" + o + "'";
        if (o instanceof Boolean) return o + "";
        if (o.getClass().isArray()) {
            if (o.getClass().getComponentType().isArray()) {
                String result = "";
                result += "(\n";
                tab.inc();
                int length = Array.getLength(o);
                for (int i = 0; i < length; i++) result += tab + toMidaObject(namespaces, Array.get(o, i));
                tab.dec();
                result += tab + ")\n";
                return result;

            } else {
                String result = "";
                result += "(";
                int length = Array.getLength(o);
                tab.inc();//just in case there will be complex structures
                for (int i = 0; i < length; i++) result += (i > 0 ? " " : "") + toMidaObject(namespaces, Array.get(o, i));
                tab.dec();
                result += ")\n";
                return result;
            }
        }
        return toMidaClass(namespaces, o);
    }

    private static String toMidaClass(YSet<String> namespaces, Object o) {
        String result = "";
        String name = o.getClass().getName();
        if (name.contains(".")) {
            int lastPointIndex = name.lastIndexOf(".");
            namespaces.add(name.substring(0, lastPointIndex));
            name = name.substring(lastPointIndex + 1);
        }
        result += tab + name + " (\n";
        tab.inc();

        for (Field field : Reflector.getAllNonStaticFieldsReversed(o.getClass()).values()) {
            Object value = Reflector.get(o, field);
            if (value == null) continue;
            //TODO other default
            result += tab + field.getName() + ": " + toMidaObject(namespaces, value);
            result += "\n";
        }


        tab.dec();
        result += "\n" + tab + ")";
        return result;
    }

    public static Object deserialize(String s) {
        return parseList(YADSParser.parseList(s));
    }

    public static Object parseList(YList l) {
        return parseClass(null, new YADClass(null, l));
    }

    public static Object parseClass(Object yad) {
        return parseClass(null, yad);
    }

    public static Object parseClass(Class clazz, Object yad) {
        //System.out.println("called 2 with class " + clazz + " yad:  " + yad);
        if (clazz != null && clazz.isArray()) return parseArray(clazz, (YADClass) yad);
        if (clazz == Integer.class || clazz == int.class) return ((Number) yad).intValue();
        if (clazz == Float.class || clazz == float.class) return ((Number) yad).floatValue();
        if (clazz == Long.class || clazz == long.class) return ((Number) yad).longValue();
        if (clazz == Double.class || clazz == double.class) return ((Number) yad).doubleValue();
        if (yad instanceof YADClass) return parseClassImpl(clazz, (YADClass) yad);
        else return yad;
    }

    private static Object parseArray(Class clazz, YADClass yad) {
        Object result = Array.newInstance(clazz.getComponentType(), yad.body.size());
        for (int i = 0; i < yad.body.size(); i++) Array.set(result, i, parseClass(clazz.getComponentType(), yad.body.get(i)));
        return result;
    }

    private static Object parseClassImpl(Class clazz, YADClass yad) {
        //System.out.println("called with class " + clazz + " yad:  " + yad);
        if (yad.name != null) {
            clazz = null;
            for (String p : namespaces) {
                try {
                    clazz = Class.forName((p.length() > 0 ? p + "." : "") + yad.name);
                    break;
                } catch (ClassNotFoundException ignore) {
                }
            }
            //if (clazz == null) throw new RuntimeException("class not found " + yad.name + " with imports: " + namespaces);
        }
        //TODO assert found class by class name, extends field?
        Object instance = clazz == null ? null : Reflector.newInstanceArgless(clazz);
        YList array = al();
        YList<Tuple> tuples = al();
        for (Object element : yad.body) {
            if (element instanceof Tuple) {
                Tuple<String, Object> t = (Tuple<String, Object>) element;
                Object value = parseClass(clazz == null ? null : Reflector.getField(clazz, t.a).getType(), t.b);
                if (t.a.equals("import")) {
                    if (value instanceof String) namespaces.add((String) value);
                    else if (value instanceof YList) namespaces.addAll((YList<String>) value);
                    else BadException.die("unknown data " + value + " for import");
                } else {
                    tuples.add(new Tuple(t.a, value));
                }
            } else {
                array.add(parseClass(null, element));
            }
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
