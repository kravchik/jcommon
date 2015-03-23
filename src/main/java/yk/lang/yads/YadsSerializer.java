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
import java.lang.reflect.Constructor;
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
public class YadsSerializer {
    //private static YList<String> namespaces = al("", "test", "yk.lang.yads");
    private static Tab tab = new Tab("  ");

    public static String serialize(Object o) {
        YSet<String> namespaces = hs();
        String result = serialize(namespaces, false, o);
        return (namespaces.isEmpty() ? "" : "import= " + Util.join(namespaces, ", ") + "\n") + result;
    }

    public static String serializeInner(Object o) {
        YSet<String> namespaces = hs();
        String result = serializeInner(namespaces, o);
        return (namespaces.isEmpty() ? "" : "import= " + Util.join(namespaces, ", ") + "\n") + result;
    }

    private static boolean between(char x ,char min, char max) {
        return x >= min && x <= max;
    }

    private static String serialize(YSet<String> namespaces, boolean typeIsKnown, Object o) {
        if (o == null) return "null";
        if (o instanceof Long) return o + "l";
        if (o instanceof Double) return o + "d";
        if (o instanceof Number) return o + "";
        if (o instanceof String) {
            String s = (String) o;
            boolean withoutQuotes = true;
            if (s.length() > 0) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (!(between(c, '0', '9')
                    || between(c, 'A', 'Z')
                    || between(c, 'a', 'z')
                    || between(c, '_', '_'))) withoutQuotes = false;
                }
                if (between(s.charAt(0), '0', '9')) withoutQuotes = false;
            } else {
                withoutQuotes = false;
            }
            if (withoutQuotes) return s;
            return "'" + Util.ESCAPE_YADS_SINGLE_QUOTES.translate((String) o) + "'";//TODO don't escape ' for " and vice versa?
        }
        if (o instanceof Boolean) return o + "";
        if (o instanceof List) return serializeList(namespaces, (List) o);
        if (o instanceof Map) return serializeMap(namespaces, (Map) o);
        if (o instanceof YadsAware) {
            String result = "";
            List serialized = ((YadsAware) o).yadsSerialize(typeIsKnown);
            if (serialized == null) return serializeClass(namespaces, o);
            //result += tab + "{\n";
            result += "\n";
            tab.inc();
            for (Object el : serialized) result += tab + possiblyCompact(serialize(namespaces, false, el)) + "\n";
            tab.dec();
            //result += tab + "}\n";
            return result;
        }
        if (o.getClass().isEnum()) return "" + o;
        if (o.getClass().isArray()) {
            if (o.getClass().getComponentType().isArray()) {
                String result = "";
                result += "{\n";
                tab.inc();
                int length = Array.getLength(o);
                for (int i = 0; i < length; i++) result += tab + possiblyCompact(serialize(namespaces, false, Array.get(o, i)));
                tab.dec();
                result += tab + "}\n";
                return result;

            } else {
                String result = "";
                result += "{";
                int length = Array.getLength(o);
                tab.inc();//just in case there will be complex structures
                for (int i = 0; i < length; i++) result += (i > 0 ? " " : "") + possiblyCompact(serialize(namespaces, false, Array.get(o, i)));
                tab.dec();
                result += "}\n";
                return result;
            }
        }
        return serializeClass(namespaces, o);
    }

    private static String serializeMap(YSet<String> namespaces, Map o) {//TODO add specific Map type if not just HashMap or YHashMap
        String result = "";
        result += "{\n";
        tab.inc();
        for (Object key : o.keySet()) result += tab + serialize(namespaces, false, key) + "= " + possiblyCompact(serialize(namespaces, false, o.get(key))) + "\n";
        tab.dec();
        result += tab + "}\n";
        return result;
    }

    private static String serializeList(YSet<String> namespaces, List o) {//TODO add specific List type if not just ArrayList or YArrayList
        String result = "";
        result += "{\n";
        tab.inc();
        for (Object el : o) result += tab + possiblyCompact(serialize(namespaces, false, el)) + "\n";
        tab.dec();
        result += tab + "}\n";
        return result;
    }

    private static String possiblyCompact(String s) {
        if (s.length() < 100) s = compact(s);
        return s;
    }

    private static String serializeClass(YSet<String> namespaces, Object o) {
        String result = "";
        String name = o.getClass().getName();
        if (name.contains(".")) {
            namespaces.add(getPackageName(name));
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        result += name + " {\n";
        tab.inc();
        result += serializeInner(namespaces, o);
        tab.dec();
        result += tab + "}\n";
        return result;
    }

    private static String getPackageName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    private static String serializeInner(YSet<String> namespaces, Object o) {
        String result = "";
        for (Field field : Reflector.getAllNonStaticFieldsReversed(o.getClass()).values()) {
            Object value = Reflector.get(o, field);
            if (value == null) continue;//TODO other defaults
            result += tab + field.getName() + "= " + possiblyCompact(serialize(namespaces, field.getType() == value.getClass(), value)) + "\n";
        }
        return result;
    }

    public static Object deserialize(String s) {
        Namespaces namespaces = new Namespaces();
        namespaces.enterScope();
        namespaces.addPackage("");
        return deserialize(namespaces, s);
    }

    public static Object deserialize(Namespaces namespaces, String s) {
        return deserializeList(namespaces, YADSParser.parseList(s));
    }

    public static <T> T deserializeClass(Class<? extends T> clazz, String s) {
        Namespaces namespaces = new Namespaces();
        namespaces.enterScope();
        namespaces.addPackage("");
        namespaces.addPackage(getPackageName(clazz.getName()));
        return (T) deserializeClass(namespaces, clazz, s);
    }

    public static <T> T deserializeClass(Namespaces namespaces, Class<? extends T> clazz, String s) {
        return (T) deserializeClass(namespaces, clazz, new YadsClass(null, YADSParser.parseList(s)));
    }

    private static Object deserializeList(Namespaces namespaces, YList l) {
        return deserializeClass(namespaces, null, new YadsClass(null, l));
    }

    private static Object deserializeClass(Namespaces namespaces, Object yad) {
        return deserializeClass(namespaces, null, yad);
    }

    private static Object deserializeClass(Namespaces namespaces, Class clazz, Object yad) {
        if (yad == null) return null;
        if (clazz != null && clazz.isArray()) return parseArray(namespaces, clazz, (YadsClass) yad);
        if (clazz != null && clazz.isEnum()) return Enum.valueOf(clazz, (String) yad);
        if (clazz == Integer.class || clazz == int.class) return ((Number) yad).intValue();
        if (clazz == Float.class || clazz == float.class) return ((Number) yad).floatValue();
        if (clazz == Long.class || clazz == long.class) return ((Number) yad).longValue();
        if (clazz == Double.class || clazz == double.class) return ((Number) yad).doubleValue();
        if (clazz == Boolean.class || clazz == boolean.class) {
                //noinspection UnnecessaryUnboxing
                return ((Boolean) yad).booleanValue();
        }
        if (yad instanceof YadsClass) return returnWithAssert(clazz, deserializeClassImpl(namespaces, clazz, (YadsClass) yad));
        else if (clazz != null && !(Map.class.isAssignableFrom(clazz))) {
            return returnWithAssert(clazz, deserializeClassImpl(namespaces, clazz, new YadsClass(null, al(yad))));
        }
        else return returnWithAssert(clazz, yad);
    }

    private static Object returnWithAssert(Class clazz, Object instance) {
        if (instance == null) return null;
        if (clazz == boolean.class && instance.getClass() == Boolean.class) return instance;
        if (clazz != null) {
            if (!clazz.isAssignableFrom(instance.getClass())) {
                BadException.die("found instance " + instance + " of class " + instance.getClass() + " but expected object of " + clazz);
            }
        }
        return instance;
    }

    private static Object parseArray(Namespaces namespaces, Class clazz, YadsClass yad) {
        Object result = Array.newInstance(clazz.getComponentType(), yad.body.size());
        for (int i = 0; i < yad.body.size(); i++) Array.set(result, i, deserializeClass(namespaces, clazz.getComponentType(), yad.body.get(i)));
        return result;
    }

    private static Object deserializeClassImpl(Namespaces namespaces, Class clazz, YadsClass yad) {
        if (yad.name != null) clazz = namespaces.findClass(yad.name);
        //TODO assert found class by class name, extends field?
        YList array = al();
        YList<Tuple> tuples = al();
        namespaces.enterScope();
        if (clazz != null) {

        }
        for (Object element : yad.body) {
            if (element instanceof Tuple) {
                Tuple<String, Object> t = (Tuple<String, Object>) element;
                if ("import".equals(t.a)) {
                    Object value = deserializeClass(namespaces, null, t.b);
                    if (value instanceof String) namespaces.addPackage((String) value);
                    else if (value instanceof YList) for (Object o : (YList) value) namespaces.addPackage((String) o);
                    else BadException.die("unknown data " + value + " for import");
                } else {
                    Object value;
                    if (clazz == null || Map.class.isAssignableFrom(clazz)) {
                        value = deserializeClass(namespaces, null, t.b);
                    } else {
                        Field field = Reflector.getField(clazz, t.a);
                        if (field == null) throw BadException.die("can't find field " + t.a + " for " + clazz);
                        value = deserializeClass(namespaces, field.getType(), t.b);
                    }
                    tuples.add(new Tuple(t.a, value));
                }
            } else {
                array.add(deserializeClass(namespaces, null, element));
            }
        }
        if (clazz != null && clazz.isEnum()) {
            if (!tuples.isEmpty()) BadException.die("enum can't contain tuples");
            if (array.size() != 1) BadException.die("enum must be stated by one element");
            namespaces.exitScope();
            return Enum.valueOf(clazz, (String)array.get(0));
        }
        Object instance;

        if (clazz == null) {
            if (yad.name != null) clazz = YadsClass.class;
            else if (!array.isEmpty() && !tuples.isEmpty()) clazz = YadsClass.class;
            else if (!tuples.isEmpty()) clazz = Map.class;
            else if (!array.isEmpty()) clazz = List.class;
            else clazz = YadsClass.class;//default is yadsclass?
        }

        if (List.class.isAssignableFrom(clazz)) {
            if (!tuples.isEmpty()) BadException.die("list class '" + clazz + "' cannot be instantiated with tuples");//TODO line number
            if (clazz == List.class) instance = al();
            else if (clazz == YList.class) instance = al();
            else instance = Reflector.newInstance(clazz);
            ((List) instance).addAll(array);
        } else if (Map.class.isAssignableFrom(clazz)) {
            if (!array.isEmpty()) BadException.die("map class '" + clazz + "' cannot be instantiated with list");//TODO line number
            if (clazz == Map.class) instance = hm();
            else if (clazz == YMap.class) instance = hm();
            else instance = Reflector.newInstance(clazz);
            for (Tuple t : tuples) ((Map)instance).put(t.a, t.b);
        } else if (clazz == YadsClass.class) {
            instance = new YadsClass(yad.name, tuples.with(array));
        } else {
            if (!array.isEmpty()) {
                Constructor constructor = Reflector.getApropriateConstructor(clazz, array.toArray());
                if (constructor != null) instance = Reflector.newInstance(clazz, array.toArray());
                else instance = Reflector.newInstance(clazz, array);
            }
            else instance = Reflector.newInstanceArgless(clazz);
            for (Tuple t : tuples) Reflector.set(instance, (String) t.a, t.b);
        }
        namespaces.exitScope();
        return instance;
    }

    public static String compact(String s) {
        String oldS = "";
        while (s.length() != oldS.length()) {
            oldS = s;
            s = s.replace("\n", " ");
            s = s.replace("  ", " ");
            s = s.replaceAll("\\} (.)", "}$1");
            s = s.replaceAll("\\{ (.)", "{$1");
            s = s.replaceAll("(.) \\}", "$1}");
            s = s.replaceAll("(.) \\{", "$1{");
        }
        return s.trim();
    }
}
