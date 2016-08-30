package yk.jcommon.match;

import yk.jcommon.match2.MatchNot;
import yk.jcommon.utils.Reflector;
import yk.jcommon.utils.Util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static yk.jcommon.collections.YHashSet.hs;
import static yk.jcommon.utils.Util.*;

/**
 * Kravchik Yuri
 * Date: 19.04.2013
 * Time: 13:52 AM
 *
 * Джава матчер. С переменными, лямбдами и пр. В "идеальный язык" за это же число - подробности.
 * var(pattern) - переменная должна содержать паттерн, простейший случай - var("hello")
 * var(collection()) - это любая коллекция - не, это через класс-матчер
 * var(set()) - пустой список (ну, это под вопросом)
 * var(collection("hello", "world", set("wtf")))
 *
 *
 * set(var("a", set(var("b"), "world")), var("a", set("hello", var("c"))))
 * Т.е. нужно матчить глубину переменных, а не просто equals, т.к. там ещё может быть работа (незаматченные переменные) - ерунда какая-то
 *
 * Можно "гарантировать" однократность вызова геттера - помещать результат в мапу. Тогда прикольно получится, если
 * геттер - это просто рандом. Он тогда назначает число каждому эл-ту, и это можно использовать...
 *
 * Как вариант:
 * Class в паттерне - это требование Class и в данных. А чтобы именно на класс проверить - нужно создавать экземпляр для примера.
 * Так можно углубиться - и матчить объекты по полям, сравнивая с экземпляром. Но тут куча сложностей. Кажется
 * лучше - сравнивать по классу, плюс отдельной мапой - по параметрам. И быстрее, и лучше выразить можно что нужно.
 *
 */
//TODO match variables in data too?
public class JM {
    //TODO use YCollections

    public static final Set emptySet = Collections.emptySet();//TODO rename in emptySet

    public static Set<Map<String, Object>> match(Object data, Object pattern) {
        return (Set)match(data, pattern, map());
//        Set<Map<Object, Object>> match = (Set)match(data, pattern, map());
//        Set<Map<String, Object>> result = set();
//        for (Map<Object, Object> map : match) {
//            Map<String, Object> rr = map();
//            result.add(rr);
//            for (Map.Entry<Object, Object> entry : map.entrySet()) rr.put(((JVar)entry.getKey()).name, entry.getValue());
//        }
//        return result;
    }

    public static Set<Map> match(Object data, Object pattern, Map current) {
        if (data == pattern) return set(current);
//        if (current.containsKey(pattern)) return match(data, current.get(pattern), current);//TODO match by String?
        if (pattern instanceof MatchNot) return match(data, ((MatchNot) pattern).rest, current).isEmpty() ? hs(current) : emptySet;
        if (isVar(pattern)) {
            if (current.containsKey(((JVar)pattern).name)) return match(data, current.get(((JVar)pattern).name), current);
            Map<Object, Object> copy = copy(current, ((JVar)pattern).name, data);
            return ((JVar)pattern).child == null ? (Set<Map>) (Set)set(copy) : match(data, ((JVar)pattern).child, copy);
        }
        if (data == null || pattern == null) return emptySet;
        if (pattern instanceof Specific) return ((Specific)pattern).match(data, current);
        if (pattern instanceof Specific2) return ((Specific2)pattern).match(current);
        if (pattern instanceof List) return matchList(data, (List) pattern, current);
        if (pattern instanceof Set) return matchSet(data, (Set) pattern, current);
        //TODO IMappable
        if (pattern instanceof Map && !(data instanceof Map)) return matchObject(data, (Map)pattern, current);
        if (pattern instanceof Map) return matchMap(data, (Map) pattern, current);
        //TODO consider moving up
        if (data.equals(pattern)) return set(current);
        return emptySet;
    }

    public static boolean isVar(Object o) {
        return o != null && o instanceof JVar;
    }

    public static Object resolve(Object pattern, Map mapping) {
        if (pattern instanceof List) {
            Set result = set();
            for (Object o : ((List) pattern)) result.add(resolve(o, mapping));
            return result;
        }
        if (pattern instanceof Set) {
            Set result = set();
            for (Object o : ((Set) pattern)) result.add(resolve(o, mapping));
            return result;
        }
        if (pattern instanceof Map) {
            Map result = map();
            for (Map.Entry<Object, Object> e : (Set<Map.Entry>)((Map) pattern).entrySet()) result.put(resolve(e.getKey(), mapping), resolve(e.getValue(), mapping));
            return result;
        }
        if (pattern instanceof JVar) {
            Object result = mapping.get(((JVar) pattern).name);
            if (result == null) throw new Error("no mapping for " + pattern);//todo what about nulls?
            return result;
        }
        return pattern;
    }

    public static Set<Map> matchSet(Object od, Set pattern, Map current) {
        if (!(od instanceof Set)) return emptySet;
        if (pattern.isEmpty()) return set(current);
        Set<Map> result = set();
        Set data = (Set) od;
        Object p = car(pattern);
        for (Object d : data) for (Map mm : match(d, p, current)) result.addAll(match(sub(data, d), sub(pattern, p), mm));//not matchSet to handle Specific2
        return result;
    }

    private static Set<Map> matchMap(Object od, Map pattern, Map current) {
        if (!(od instanceof Map)) return emptySet;
        if (pattern.isEmpty()) return set(current);
        Set<Map> result = set();
        Map data = (Map) od;
        Object ppkey = car(pattern.keySet());
        Object pkeyR = resolve(ppkey, current);
        //TODO consider not sub-ing 'od'
        for (Map mmm : match(data.get(pkeyR), pattern.get(ppkey), current)) result.addAll(match(sub(data, pkeyR), sub(pattern, ppkey), mmm));//not matchSet to handle Specific2
        return result;
    }

    private static Set<Map> matchObject(Object od, Map pattern, Map current) {
        if (pattern.isEmpty()) return set(current);
        Set<Map> result = set();
        Object ppkey = car(pattern.keySet());
        Object pkeyR = resolve(ppkey, current);
        for (Map mmm : match(Reflector.get(od, (String) pkeyR), pattern.get(ppkey), current)) result.addAll(match(od, sub(pattern, ppkey), mmm));//not matchSet to handle Specific2
        return result;
    }

    private static Set<Map> matchList(Object d, List pattern, Map current) {
        if (!(d instanceof List)) return emptySet;
        List data = (List) d;
        if (data.size() != pattern.size()) return emptySet;
        Set<Map> result = set(current);
        //TODO handle Specific2
        for (int i = 0; i < data.size(); i++) {
            Set<Map> curRes = set();
            for (Map map : result) curRes.addAll(match(data.get(i), pattern.get(i), map));
            result = curRes;
        }
        return result;
    }

    public static String tab(int tabs) {
        String result = "";
        for (int i = 0; i < tabs; i++) {
            result += "    ";
        }
        return result;
    }

    public static String toString(Object o) {
        return toString(o, 0);
    }

    public static String toString(Object o, int tabs) {
        if (o instanceof List) {
            Set<String> ss = set();
            for (Object s : (List)o) ss.add(toString(s, tabs+1));

            return "\n" + tab(tabs) + "[" + Util.append(ss, " ") + "]";
        } else if (o instanceof Set) {
            Set<String> ss = set();
            for (Object s : (Set)o) ss.add(toString(s, tabs+1));
            return "{\n" + Util.append(tab(tabs), ss, "\n") + "}";
        } else if (o instanceof Map) {
            Set<String> kv = set();
            for (Object entry : ((Map) o).entrySet()) kv.add(((Map.Entry) entry).getKey() + ":" + ((Map.Entry) entry).getValue());
            return "{\n" + Util.append(tab(tabs), kv, "\n") + "}";
        } else if (o == null) {
            return "null";
        } else {
            return o.toString();
        }
    }

    public static interface Specific<T> {
        Set<Map> match(T data, Map current);//TODO automatically check T (somehow)
        //TODO implement class matcher
    }
    public static interface Specific2<T> {
        Set<Map> match(Map current);//TODO automatically check T (somehow)
        //TODO implement class matcher
    }
}

