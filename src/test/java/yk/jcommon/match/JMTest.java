package yk.jcommon.match;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.match.JM.match;
import static yk.jcommon.utils.Util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/22/13
 * Time: 11:33 PM
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class JMTest {

    public static JVar v(String name) {
        return new JVar(name);
    }

    public static JVar v(String name, Object o) {
        return new JVar(name, o);
    }

    @Test
    public void test1() {
        assertEquals(set(map("x", "a")), match(list("a"), list(v("x"))));
        assertEquals(set(), match(list("a", "b"), list(v("x"), null)));

        assertEquals(set(map("x", "a", "y", "b")), match(list("a", "b"), list(v("x"), v("y"))));
        assertEquals(set(map("x", list("a"), "y", "b")), match(list(list("a"), "b"), list(v("x"), v("y"))));

        assertEquals(set(map("x", list("a"), "y", "b")), match(list(list("a"), "b"), list(v("x", list("a")), v("y"))));

        assertEquals(set(), match(list(list("a"), "b"), list(v("x", list(new Object[]{null})), v("y"))));

        assertEquals(set(map("x", "a")), match(set(list("a", "b"), "a", "b"), set(list(v("x"), "b"), v("x"), "b")));

        JVar X = v("x");
        assertEquals(set(), match(set(list("a", "b"), "c", "b"), set(list(X, "b"), X, "b")));

        assertEquals(set(), match(set(list(v("x"), "b")), set(list("c", v("y")))));//not working variable in data
    }

    @Test
    public void test() {
        //assertEquals(set(map("X", "hello", "Y", "world")), match(list("hello", "world"), list("X", "Y")));
        //assertEquals(set(map("Y", "hello", "X", "world")), match(list("hello", "X"), list("Y", "world")));
        //assertEquals(set(map("Y", list("l", "ll"))), match(list(list("l", "ll"), "w"), list("Y", "w")));
        //
        //assertEquals(set(map("X", "a"), map("X", "c")), match(set(list("a", "b"), list("c", "b")), set(list("X", "b"))));
        //
        ////TODO fix problem of meta-rule
        //System.out.println(match(                           //???
        //        set(list("print", "multi"), list("print", "wrong"), Util.<Object>list(list("calc", "X"), "then", list("print", "X"))),
        //        set(Util.<Object>list("A", "then", list("print", "Y")), list("print", "Y"))// B:
        //));
        //System.out.println(match(                           //???
        //        set(list("print", "multi"), list("print", "wrong"), Util.<Object>list(list("calc", "X"), "then", list("print", "X"))),
        //        set((List)list("A", "then", list("print", "multi")))// B:
        //));

    }

}
