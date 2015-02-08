package yk.jcommon.match;

import org.junit.Test;
import yk.jcommon.utils.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.utils.Util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 5/17/13
 * Time: 12:13 AM
 */

//no matter what is pattern and what is data
//both can have variables and match each other
//and variables could be in variables


//пока отложил, т.к. найдено более простое решение для мета-правил:
// сначала создаём инстанс его, затем уже этот инстанс подбирает что нужно


//TODO fix flat-mem issue

@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class Match2 {
    private static final Set emptyList = set();

    public static Set<Map> match(Object data, Object pattern) {
        return match(data, pattern, map());
    }


    public static Set<Map> match(Object data, Object pattern, Map current) {
        if (data == pattern) return set(current);

        if (current.containsKey(data)) return match(current.get(data), pattern, current);
        if (current.containsKey(pattern)) return match(data, current.get(pattern), current);

        if (isVar(data)) {
            Map<Object, Object> copy = copy(current, data, pattern);
            return (Set<Map>)(Set)set(copy);
        }
        if (isVar(pattern)) {
            Map<Object, Object> copy = copy(current, pattern, data);
            return (Set<Map>)(Set)set(copy);
        }

        if (data == null || pattern == null) return emptyList;

        if (pattern instanceof List) {
            return matchList(data, (List) pattern, current);
        } else if (pattern instanceof Set) {
            return matchSet(data, (Set) pattern, current);
        } else {
            return data.equals(pattern) ? set(current) : emptyList;
        }
    }

    private static Set<Map> matchSet(Object od, Set pattern, Map current) {
        if (!(od instanceof Set)) return emptyList;
        if (pattern.isEmpty()) return set(current);
        Set<Map> result = set();
        Set data = (Set) od;
        Object p = car(pattern);
        for (Object d : data) {
            for (Map mm : match(d, p, current)) {
                result.addAll(matchSet(sub(data, d), sub(pattern, p), mm));
            }
        }
        return result;
    }

    private static Set<Map> matchList(Object d, List pattern, Map current) {
        if (!(d instanceof List)) return emptyList;
        List data = (List) d;
        if (data.size() != pattern.size()) return emptyList;
        Set<Map> result = set(current);

        for (int i = 0; i < data.size(); i++) {
            Set<Map> old = result;
            result = set();
            for (Map map : old) result.addAll(match(data.get(i), pattern.get(i), map));
            if (result.isEmpty()) break;
        }
        return result;
    }

    public static boolean isVar(Object o) {
        return o != null && o instanceof String && Character.isUpperCase(((String) o).charAt(0));
    }

    @Test
    public void test() {
        assertEquals(set(map("X", "hello", "Y", "world")), match(list("hello", "world"), list("X", "Y")));
        assertEquals(set(map("Y", "hello", "X", "world")), match(list("hello", "X"), list("Y", "world")));
        assertEquals(set(map("Y", list("l", "ll"))), match(list(list("l", "ll"), "w"), list("Y", "w")));

        assertEquals(set(map("X", "a"), map("X", "c")), match(set(list("a", "b"), list("c", "b")), set(list("X", "b"))));

        //TODO fix problem of meta-rule
        System.out.println(match(                           //???
                set(list("print", "multi"), list("print", "wrong"), Util.<Object>list(list("calc", "X"), "then", list("print", "X"))),
                set(Util.<Object>list("A", "then", list("print", "Y")), list("print", "Y"))// B:
        ));
        System.out.println(match(                           //???
                set(list("print", "multi"), list("print", "wrong"), Util.<Object>list(list("calc", "X"), "then", list("print", "X"))),
                set((List)list("A", "then", list("print", "multi")))// B:
        ));

    }
}