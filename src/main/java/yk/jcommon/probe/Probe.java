package yk.jcommon.probe;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.match2.Matcher;
import yk.jcommon.search.SSearch;
import yk.jcommon.utils.Reflector;
import yk.jcommon.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 01/11/15
 * Time: 17:16
 */
public class Probe extends SSearch<State> {
    public Object pattern;

    public YSet<String> skip = hs();
    public YSet<String> skipMethods = hs();
    public YSet<String> skipFields = hs();
    public Consumer<String> logger;

    public static YList<String> find(Object data, Object pattern) {
        Probe probe = new Probe(data, pattern);
        Node<State> solution = probe.nextSolution(10000);
        return solution == null ? null : solution.state.stackTrace;
    }

    public static YList<String> find(Object data, Object pattern, YSet<String> skip) {
        Probe probe = new Probe(data, pattern);
        probe.skip = skip;
        Node<State> solution = probe.nextSolution(10000);
        return solution == null ? null : solution.state.stackTrace;
    }

    public Probe(Object data, Object pattern) {
        super(new State(al(), data));
        this.pattern = pattern;
    }

    @Override
    public List<State> generate(Node<State> node) {
        return children(node, pattern);
    }

    @Override
    public boolean isSolution(Node<State> node) {
        YSet<YMap<String, Object>> match = new Matcher().match(node.state.data, pattern);
        if (match.notEmpty()) {
            node.state.match = match;
            return true;
        }
        return false;
    }

    private static String getClassName(Object o) {
        return o == null ? "null" : o.getClass().toString();
    }

    public YList<State> children(SSearch.Node<State> node, Object pattern) {
        State state = node.state;
        YList<State> result = al();
        //TODO try reuse accessors-way
        Object data = state.data;
        YList<String> stackTrace = state.stackTrace;


        if (!tryDeeper(data)) return result;

        if (data instanceof List) {
            for (int i = 0; i < ((List) data).size(); i++) if (inspectListElement((List)data, i, state)) {
                Object value = ((List) data).get(i);
                YList<String> newStacktrace = stackTrace.with("[" + i + "]" + "{" + getClassName(value) + "}");
                result.add(new State(newStacktrace, value));
            }
            return result;
        }

        if (data instanceof Map) {
            Map m = (Map) data;
            for (Object key : m.keySet()) if (inspectMapValue(m, key, state)) {
                Object value = m.get(key);
                YList<String> newStacktrace = stackTrace.with("byKey(" + key + "{" + getClassName(key) + "}){" + getClassName(value) + "}");
                result.add(new State(newStacktrace, value));
            }
            return result;
        }

        for (Field field : Reflector.getAllFieldsInHierarchy(data.getClass())) {
            if (inspectField(field, state)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(data);
                    YList<String> newStacktrace = stackTrace.with("." + field.getName() + "{" + getClassName(value) + "}");
                    result.add(new State(newStacktrace, value));
                } catch (IllegalAccessException ignore) {
                    if (logger != null) logger.accept("exception " + Util.stacktraceToString(ignore) + " at " + stackTrace);
                }
            }
        }

        for (Method method : Reflector.getAllMethodsInHierarchy(data.getClass())) {
            method.setAccessible(true);
            if (inspectMethod(method, state)) {
                method.setAccessible(true);
                try {
                    Object value = method.invoke(data);
                    YList<String> newStacktrace = stackTrace.with("." + method.getName() + "()" + "{" + getClassName(value) + "}");
                    result.add(new State(newStacktrace, value));
                } catch (Exception ignore) {
                    if (logger != null) logger.accept("exception " + Util.stacktraceToString(ignore) + " at " + stackTrace);
                }
            }
        }
        return result;
    }

    private boolean inspectMapValue(Map m, Object key, State state) {
        return true;
    }

    private boolean inspectListElement(List data, int i, State state) {
        return true;
    }

    public boolean inspectField(Field f, State state) {
        if (skip.contains(f.getName())) return false;
        if (skipFields.contains(f.getName())) return false;
        if (!Modifier.isPublic(f.getModifiers())) return false;
        //noinspection RedundantIfStatement
        if (f.getDeclaringClass() == Object.class) return false;
        return true;
    }

    public boolean inspectMethod(Method m, State state) {
        if (!Modifier.isPublic(m.getModifiers())) return false;
        if (skip.contains(m.getName())) return false;
        if (skipMethods.contains(m.getName())) return false;
        if (!m.getName().startsWith("get")) return false;
        if (m.getParameters().length > 0) return false;
        //noinspection RedundantIfStatement
        if (m.getDeclaringClass() == Object.class) return false;
        return true;
    }

    public boolean tryDeeper(Object data) {
        if (data == null) return false;
        if (data.getClass().isPrimitive()) return false;
        if (data.getClass().getName().startsWith("java.lang.")) return false;
        if (data.getClass().isEnum()) return false;
        return true;
    }


}
