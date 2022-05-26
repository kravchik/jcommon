package yk.jcommon.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YArrayList.times;

/**
 * Created by Yuri Kravchik on 17.11.2019
 */
public class TestYArrayList {

    @Test
    public void testTimes() {
        assertEquals(al(), times(-1, i -> "_" + i));//maybe it is better to throw an exception
        assertEquals(al(), times(0, i -> "_" + i));
        assertEquals(al("_0", "_1", "_2"), times(3, i -> "_" + i));
    }

    @Test
    public void testRemoveFast() {
        assertEquals(al(), rf(al("a"), 0));
        assertEquals(al("b"), rf(al("a", "b"), 0));
        assertEquals(al("c", "b"), rf(al("a", "b", "c"), 0));
        assertEquals(al("a", "c"), rf(al("a", "b", "c"), 1));
        assertEquals(al("a", "b"), rf(al("a", "b", "c"), 2));
    }

    private static <T> YArrayList<T> rf(YArrayList<T> src, int i) {
        T old = src.get(i);
        T t = src.removeFast(i);
        assertEquals(old, t);
        return src;
    }
}
