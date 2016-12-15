package yk.jcommon.utils;

import org.apache.commons.lang3.text.translate.*;
import yk.jcommon.fastgeom.Vec2f;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Math.pow;

/**
 * Kravchik Yuri
 * Date: 11/24/11
 * Time: 12:43 AM
 */
public class Util {
    public static final float PI = (float) Math.PI;
    public static DecimalFormat F = new DecimalFormat("#.##");

    public static <T> Set<Map<T, T>> scramble(Set<T> aa, Set<T> bb) {
        return scramble(aa, bb, new HashMap<T, T>());
    }

    public static <T> Set<Map<T, T>> scramble(Set<T> aa, Set<T> bb, Map<T, T> prev) {
        Set<Map<T, T>> result = new HashSet<Map<T, T>>();
        if (aa.isEmpty() || bb.isEmpty()) {
            result.add(prev);
        } else
        for (T b : bb) {
            T aHead = aa.iterator().next();
            if (prev.containsKey(aHead) && !prev.get(aHead).equals(b)) continue;
            Set<T> aTail = subSet(aa, aHead);
            if (!prev.containsKey(aHead)) result.addAll(scramble(aTail, bb, ext(prev, aHead, b)));
            result.addAll(scramble(aTail, bb, new HashMap<T, T>(prev)));//b without substitution, or if b is already in prev
        }
        return result;
    }

    public static <K, V> Map<K, V> ext(Map<K, V> m, K k, V v) {
        Map<K, V> result = new HashMap<K, V>(m);
        result.put(k, v);
        return result;
    }

    public static List<List<Integer>> scrambleIn(int number, int size, List<Integer> current) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if (size == 0)
            result.add(current);
        else for (int i = 0; i < number; i++) if (!current.contains(i)) {
            List<Integer> li = new ArrayList<Integer>(current);
            li.add(i);
            result.addAll(scrambleIn(number, size - 1, li));
        }
        return result;
    }

    public static <T> Set<T> subSet(Set<T> nn, T n) {
        Set<T> result = new HashSet<T>(nn);
        result.remove(n);
        return result;
    }

    public static void testScrambleIn() {
        System.out.println(scrambleIn(1, 3, new ArrayList<Integer>()));
    }

    public static void testScramble() {
        Set<Integer> aa = new HashSet<Integer>();
        Set<Integer> bb = new HashSet<Integer>();

        aa.add(1);
        aa.add(2);

        bb.add(4);
        bb.add(5);

        HashMap<Integer, Integer> upper = new HashMap<Integer, Integer>();
//        upper.put(1, 4);

        System.out.println(scramble(aa, bb, upper));
    }

    public static float format(float v, int base) {
        float b = (float) pow(10, base);
        return ((int) (v*b))/b;
    }

    public static <T> Set<Map<T, T>> assocFlat(List<T> from, List<T> to, Map<T, T> mapping) {
        Set<Map<T, T>> result = new HashSet<Map<T, T>>();
        result.add(mapping);
        if (from.isEmpty() || to.isEmpty()) return result;
        result.addAll(assocFlat(cdr(from), to, mapping));
        result.addAll(assocFlat(from, cdr(to), mapping));

        if (!mapping.containsKey(car(from))) {
            for (T t : to) if (!mapping.containsValue(t)) {
                Map<T, T> m = new HashMap<T, T>(mapping);
                m.put(car(from), t);
                result.addAll(assocFlat(cdr(from), to, m));
            }
        }
        return result;
    }

    public static <T> T car(List<T> list) {
        return list.get(0);
    }

    public static <T> List<T> cdr(List<T> list) {
        if (list.size() == 1) return list();
        return list.subList(1, list.size());
    }

    public static <T> Collection<T> sub(Collection<T> s, T item) {
        Set<T> result = new HashSet<T>();
        for (T t : s) if (t != item) result.add(t);
        return result;
    }

    public static <T> List<T> sub(List<T> s, T item) {
        List<T> result = new ArrayList<T>(s);
        result.remove(item);
        return result;
    }

    public static <T> HashSet<T> sub(Set<T> s, T item) {
        HashSet<T> result = new HashSet<T>();
        for (T t : s) if (t != item) result.add(t);
        return result;
    }

    public static <K, V> HashMap<K, V> sub(Map<K, V> s, K key) {
        HashMap<K, V> result = new HashMap<K, V>(s);
        result.remove(key);
        return result;
    }

    public static <T> T car(Collection<T> list) {
        return list.iterator().next();
        //Iterator<T> it = list.iterator();
        //return it.hasNext() ? it.next() : null;
    }

    public static <T> Set<T> copy(Set<T> set) {
        return new HashSet<T>(set);
    }

    public static <T> Set<T> copy(Set<T> set, T... args) {
        HashSet<T> result = new HashSet<T>(set);
        set.addAll(Arrays.asList(args));
        return result;
    }

    public static <T> List<T> copy(List<T> array, T... args) {
        ArrayList<T> result = new ArrayList<T>(array);
        array.addAll(Arrays.asList(args));
        return result;
    }

    public static <K, V> Map<K, V> copy(Map<K, V> map) {
        return new HashMap<K, V>(map);
    }

    public static <K, V> Map<K, V> copy(Map<K, V> m, K k, V v, Object... other) {
        //System.out.println("copying: " + m + " k: " + k + " v: " + v);
        Map<K, V> result = copy(m);
        result.put(k, v);
        for (int i = 0; i < other.length; i += 2) {
            result.put((K)other[i], (V)other[i + 1]);
        }
        return result;
    }

    //public static <K, V> Map<K, V> copy(Map<K, V> m, Object... other) {
    //    Map<K, V> result = copy(m);
    //    for (int i = 0; i < other.length; i += 2) {
    //        result.put((K)other[i], (V)other[i + 1]);
    //    }
    //    return result;
    //}

    public static <T> Collection<T> cdr(Collection<T> list) {
        return sub(list, car(list));
    }

    public static String append(Collection ss, String appender) {
        return append("", ss, appender);
    }

    public static String append(String prefix, Collection ss, String appender) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (Object o : ss) {
            if (first) first = false;
            else sb.append(appender);
            sb.append(prefix).append(o == null ? "null" : o.toString());
        }
        return sb.toString();
    }

    public static <T> Set<T> set(T... tt) {
        return new HashSet<T>(Arrays.asList(tt));
    }

    public static <T> ArrayList<T> list(T... tt) {
        return new ArrayList<T>(Arrays.asList(tt));
    }

    public static List list() {
        return new ArrayList();
    }

//    public static <T> Set<T> set(Set<T> s) {
//        return new HashSet<T>(s);
//    }

    //public static <K, V> Map<K, V> map(K k, V v, Object... oo) {
    //    Map<K, V> result = new HashMap<K, V>();
    //    result.put(k, v);
    //    for (int i = 0; i < oo.length; i+=2) result.put((K)oo[i], (V)oo[i+1]);
    //    return result;
    //}
    //
    public static Map map(Object... oo) {
        Map result = new HashMap();
        for (int i = 0; i < oo.length; i+=2) result.put(oo[i], oo[i+1]);
        return result;
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    public static <T> Set<T> addIf(Set<T> old, Set<T> toAdd) {
        if (toAdd == null) return old;
        if (old == null) return toAdd;
        old.addAll(toAdd);
        return old;
    }

    public static float sqr(float a) {
        return a * a;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new Error(e);
        }
    }

    public static <T> HashSet<T> cross(Set<T> a, Set<T> b) {
        HashSet<T> result = new HashSet<T>();
        for (T t : b) if (a.contains(t)) result.add(t);
        return result;
    }

    public static <T> ArrayList<T> add(Collection<T> a, Collection<T> b) {
        ArrayList<T> result = (ArrayList<T>) list();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    public static <T> HashSet<T> add(Set<T> a, Set<T> b) {
        HashSet<T> result = (HashSet<T>) set();
        result.addAll(a);
        result.addAll(b);
        return result;
    }

    public static String join(Collection c, String separator) {
        boolean was = false;
        String result = "";
        for (Object o : c) {
            if (was) result += separator;
            result += o;
            was = true;
        }
        return result;
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize) {
        try {
            byte[] buf = new byte[bufferSize];
            int bytesRead = input.read(buf);
            while (bytesRead != -1) {
                output.write(buf, 0, bytesRead);
                bytesRead = input.read(buf);
            }
            output.flush();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //TODO move to MyMath
    public static Vec2f cross(Vec2f A, Vec2f B) {
        if (A == null || B == null) return null;
        float a = A.x;
        float c = A.y;
        float b = B.x;
        float d = B.y;
        if (a - b == 0) return null;
        float ra = (d - c) / (a - b);
        float rb = a * ra + c;
        return new Vec2f(ra, rb);
    }

    //TODO move to MyMath
    /*
     * Convert line stated as point and normal to y=ax+b form
     */
    public static Vec2f pn2ab(Vec2f p, Vec2f n) {
        if (n.y == 0) return null;
        return new Vec2f(-n.x / n.y, n.x/n.y*p.x + p.y);
    }

    private static void testCrossing() {
        System.out.println(cross(pn2ab(new Vec2f(-1, 0), new Vec2f(-1, 1).normalized()), pn2ab(new Vec2f(1, 0), new Vec2f(1, 1).normalized())));
        System.out.println(cross(pn2ab(new Vec2f(0, 1), new Vec2f(0, 1).normalized()), pn2ab(new Vec2f(1, 0), new Vec2f(1, 1).normalized())));

    }

    private static final String s = " auto generated text";

    public static String insertLines(String body, String prefix, List<String> lines) {
        String splitter = "//" + prefix + s;
        String[] split = body.split(splitter);
        String result = split[0];
        result = result + splitter + "\n";
        for (String line : lines) result = result + line + "\n";
        result = result + "\n" + splitter;
        result = result + split[2];
        return result;
    }

    public static <T> T last(List<T> c) {
        return c.get(c.size() - 1);
    }

    public static final CharSequenceTranslator UNESCAPE_YADS_SINGLE_QUOTES =
     new AggregateTranslator(
                             new OctalUnescaper(),     // .between('\1', '\377'),
                             new UnicodeUnescaper(),
                             new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()),
                             new LookupTranslator(
                                                  new String[][] {
                                                                  {"\\\\", "\\"},
                                                                  {"\\'", "'"},
                                                                  {"\\", ""}
                                                  })
     );

    public static final CharSequenceTranslator UNESCAPE_YADS_DOUBLE_QUOTES =
     new AggregateTranslator(
                             new OctalUnescaper(),     // .between('\1', '\377'),
                             new UnicodeUnescaper(),
                             new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()),
                             new LookupTranslator(
                                                  new String[][] {
                                                                  {"\\\\", "\\"},
                                                                  {"\\\"", "\""},
                                                                  {"\\", ""}
                                                  })
     );

    public static final AggregateTranslator ESCAPE_YADS_SINGLE_QUOTES = new AggregateTranslator(new LookupTranslator(new String[][]{{"'", "\\'"}, {"\\", "\\\\"}}), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()));
    public static final AggregateTranslator ESCAPE_YADS_DOUBLE_QUOTES = new AggregateTranslator(new LookupTranslator(new String[][]{{"\"", "\\\""}, {"\\", "\\\\"}}), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()));

    public static boolean equalsWithNull(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    public static String addLength(String s, int targetLength) {
        return addLength(s, targetLength, " ");
    }

    public static String addLength(String s, int targetLength, String with) {
        StringBuilder sb = new StringBuilder(s);
        while(sb.length() < targetLength) sb.append(with);
        return sb.toString();
    }

    public static String stacktraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
