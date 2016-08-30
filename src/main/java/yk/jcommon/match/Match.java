package yk.jcommon.match;

import yk.jcommon.match2.MatchNot;
import yk.jcommon.utils.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.utils.Util.*;

/**
 * Kravchik Yuri
 * Date: 10.07.2012
 * Time: 12:32 AM
 */
public class Match {
    private static Map buffer = map();
    private static final List emptyList = list();
    private static Object getB(Object o) {
        Object old = buffer.get(o);
        if (old == null) {
            buffer.put(o, o);
            old = o;
        }
        return old;
    }

    public static Object get(Object o) {
        //if (o instanceof List) {
        //    List l = (List)o;
        //    int size = l.size();
        //    for (int i = 0; i < size; i++) l.set(i, getFromList(l.getFromList(i)));
        //    return getB(l);
        //} else if (o instanceof Set) {
        //    Set n = set();
        //    for (Object oo : (Set)o) n.add(getFromList(oo));
        //    return getB(n);
        //} else {
        //    return getB(o);
        //}
        return o;

    }

    //TODO define type of matched variable (for example L:List)

    public static Map matchOne(Object data, Object pattern) {
        List<Map> result = match(data, pattern);
        if (result.size() > 1) throw new RuntimeException("ambiguity when matching " + data + " by pattern " + pattern);
        if (result.size() == 0) throw new RuntimeException("not matched when matching " + data + " by pattern " + pattern);
        return result.get(0);
    }

    public static List<Map> match(Object data, Object pattern) {
        return match(data, pattern, map());
    }

    public static List<Map> match(Object data, Object pattern, Map current) {
        if (data == null) return emptyList;//todo fix this fast fix
        if (data.equals(pattern)) return list(current);
        if (isVar(pattern)) {
            Object old = current.get(pattern);
            if (old == null) old = data;
            if (!old.equals(data)) return emptyList;
            Map<Object, Object> copy = copy(current, pattern, data);
            return (List<Map>) (List) list(copy);
        } else if (pattern instanceof MatchNot) {
            return match(data, ((MatchNot) pattern).rest, current).isEmpty() ? al(current) : emptyList;
        } else if (pattern instanceof Ptr) {
            Ptr ptr = (Ptr) pattern;
            return match(current.get(ptr.var), ptr.path, current);
//        } else if (data instanceof Specific) {
//            return ((Specific)data).match(pattern, current);
//        } else if (pattern instanceof Specific) {
//            return ((Specific)pattern).match(data, current);
        } else if (pattern instanceof Specific) {
            return ((Specific)pattern).match(current);
        } else if (!pattern.getClass().equals(data.getClass())) return emptyList;
        if (pattern instanceof List) {
            return matchList(data, (List) pattern, current);
        } else if (pattern instanceof Set) {
            return matchSet(data, (Set) pattern, current);
        } else if (pattern instanceof Map) {
            return matchMap(data, (Map) pattern, current);
        } else {
            return (data == null ? pattern == null : data.equals(pattern)) ? list(current) : emptyList;
        }
    }

    public static Object resolve(Object pattern, Map mapping) {
        if (pattern instanceof List) {
            List result = list();
            for (Object o : ((List) pattern)) {
                result.add(resolve(o, mapping));
            }
            return result;
        }
        if (pattern instanceof Set) {
            Set result = set();
            for (Object o : ((Set) pattern)) {
                result.add(resolve(o, mapping));
            }
            return result;
        }
        if (pattern instanceof Map) {
            Map result = map();
            for (Map.Entry<Object, Object> e : (Set<Map.Entry>)((Map) pattern).entrySet()) {
                result.put(resolve(e.getKey(), mapping), resolve(e.getValue(), mapping));
            }
            return result;
        }
        if (isVar(pattern)) {
            Object result = mapping.get(pattern);
            if (result == null) throw new Error("no mapping for " + pattern);//todo what about nulls?
            return result;
        }
        return pattern;
    }

    public static Object add(Object data, Object pattern, Map mapping) {
        Object p = resolve(pattern, mapping);
        if (data instanceof Specific) {
            return ((Specific)data).resolve(pattern, mapping);
        } else if (data instanceof List) {
            return copy((List)data, resolve(pattern, mapping));
        } else if (data instanceof Set) {
            return copy((Set) data, resolve(p, mapping));
        } else if (data instanceof Map) {
            Map result = copy((Map) data);
            for (Object o : ((Map) pattern).keySet()) {
                result.put(resolve(o, mapping), add(((Map)data).get(o), ((Map)pattern).get(o), mapping));
            }
            return result;
        } else if (pattern instanceof Ptr) {
            Ptr ptr = (Ptr) pattern;
            return add(mapping.get(ptr.var), ptr.path, mapping);
        }
        return pattern;
        //throw new Error("" + pattern);
    }

    public static Object replace(Object data, Map mapa) {
        if (data instanceof List) {
            List result = list();
            for (Object o : (List)data) result.add(replace(o, mapa));
            return get(result);
        } else if (data instanceof Set) {
            Set result = set();
            for (Object o : (Set)data) result.add(replace(o, mapa));
            return get(result);
        }
        if (mapa.containsKey(data)) return mapa.get(data);
        return get(data);
    }

    private static List<Map> matchSet(Object od, Set pattern, Map current) {
        if (!(od instanceof Set)) return emptyList;
        if (pattern.isEmpty()) return list(current);
        List<Map> result = list();
        Set data = (Set) od;
        Object p = car(pattern);
        for (Object d : data) {
            for (Map mm : match(d, p, current)) {
                addAll(result, matchSet(sub(data, d), sub(pattern, p), mm));
            }
        }
        return result;
    }

    private static List<Map> matchMap(Object od, Map pattern, Map current) {
        if (!(od instanceof Map)) return emptyList;
        if (pattern.isEmpty()) return list(current);
        List<Map> result = list();
        Map data = (Map) od;
        if (data.size() < pattern.size()) return emptyList;//OPTIMIZATION
        Object ppkey = car(pattern.keySet());
        //Object dval = data.getFromList(pkey);//without keys matching
        //if (dval == null) return emptyList;
        //for (Map nmap : match(dval, pattern.getFromList(pkey), current)) {
        //    addAll(result, matchMap(data, sub(pattern, pkey), nmap));
        //}

        if (isVar(ppkey))//TODO get binding if ppkey already bind
        for (Object dKey : data.keySet()) {  //match keys here
            for (Map mm : match(dKey, ppkey, current)) {
                for (Map mmm : match(data.get(dKey), pattern.get(ppkey), mm)) {
                    addAll(result, matchMap(sub(data, dKey), sub(pattern, ppkey), mmm));
                }
            }
        } else {

            Object pkeyR = resolve(ppkey, current);
            for (Map mmm : match(data.get(pkeyR), pattern.get(ppkey), current)) {
                addAll(result, matchMap(sub(data, pkeyR), sub(pattern, ppkey), mmm));
            }
        }
        return result;
    }

    private static List<Map> matchList(Object d, List pattern, Map current) {
        if (!(d instanceof List)) return emptyList;
        List data = (List) d;
        if (pattern.get(0).equals("правила")) return emptyList;
        if (data.size() != pattern.size()) return emptyList;
        Map<List, Object> copy = copy(current, pattern, d);
        List<Map> result = (List<Map>)(List)list(copy);
        for (int i = 0; i < data.size(); i++) {
            List<Map> curRes = list();
            for (Map map : result) addAll(curRes, match(data.get(i), pattern.get(i), map));
            if (curRes.isEmpty()) return emptyList;
            result = curRes;
        }
        return result;
    }

    public static void addAll(List<Map> l, List<Map> m) {
        if (m != null) l.addAll(m);
    }

    public static boolean isVar(Object o) {
        return o instanceof String && Character.isUpperCase(((String) o).charAt(0));
    }

    public static class Ptr {
        public String var;
        public Object path;

        public static Ptr ptr(String var, Object path) {
            return new Ptr(var, path);
        }

        public Ptr(String var, Object path) {
            this.var = var;
            this.path = path;
        }
    }

    public static interface Specific {
        List<Map> match(Map current);
        Object resolve(Object p, Map current);
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
            List<String> ss = list();
            for (Object s : (List)o) ss.add(toString(s, tabs+1));

            return "\n" + tab(tabs) + "[" + Util.append(ss, " ") + "]";
        } else if (o instanceof Set) {
            List<String> ss = list();
            for (Object s : (Set)o) ss.add(toString(s, tabs+1));
            return "{\n" + Util.append(tab(tabs), ss, "\n") + "}";
        } else if (o instanceof Map) {
            List<String> kv = list();
            for (Object entry : ((Map) o).entrySet()) kv.add(((Map.Entry) entry).getKey() + ":" + ((Map.Entry) entry).getValue());
            return "{\n" + Util.append(tab(tabs), kv, "\n") + "}";
        } else if (o == null) {
            return "null";
        } else {
            return o.toString();
        }
    }


}

