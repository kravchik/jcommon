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

}
