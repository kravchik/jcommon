package yk.jcommon.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created by Yuri Kravchik on 20.12.2018
 */
public class TestYHashMap {

    @Test
    public void testFilter() {
        assertEquals(hm(), hm().filter((k, v) -> false));
        assertEquals(hm(), hm(1, 2).filter((k, v) -> false));
        assertEquals(hm(1, 2), hm(1, 2).filter((k, v) -> true));
        assertEquals(hm(1, 2), hm(1, 2, 3, 4).filter((k, v) -> k == 1));
        assertEquals(hm(1, 2), hm(1, 2, 3, 4).filter((k, v) -> v == 2));
    }

    @Test
    public void testMapToList() {
        assertEquals(al(), hm().mapToList((k, v) -> k));
        assertEquals(al(1), hm(1, 2).mapToList((k, v) -> k));
        assertEquals(al(2), hm(1, 2).mapToList((k, v) -> v));
        assertEquals(al(1, 3), hm(1, 2, 3, 4).mapToList((k, v) -> k));
    }

    @Test
    public void testMap() {
        assertEquals(hm(), hm().map((k, v) -> k+"", (k, v) -> v+""));
        assertEquals(hm(), hm().map(k -> k+"", v -> v));
        assertEquals(hm("1", "2"), hm(1, 2).map((k, v) -> k+"", (k, v) -> v+""));
        assertEquals(hm("1", "2"), hm(1, 2).map(k -> k+"", v -> v+""));
    }

    @Test
    public void testMapValues() {
        assertEquals(hm(), hm().mapValues((k, v) -> k+""));
        assertEquals(hm(), hm().mapValues(v -> v+""));

        assertEquals(hm(1, "2"), hm(1, 2).mapValues((k, v) -> v+""));
        assertEquals(hm(1, "2"), hm(1, 2).mapValues(v -> v+""));
    }

    @Test
    public void testMapKeys() {
        assertEquals(hm(), hm().mapKeys((k, v) -> k+""));
        assertEquals(hm(), hm().mapKeys(v -> v+""));

        assertEquals(hm("2", 2), hm(1, 2).mapKeys((k, v) -> v+""));
        assertEquals(hm("1", 2), hm(1, 2).mapKeys(k -> k+""));
    }

    @Test
    public void testWith() {
        assertEquals(hm(1, 2, 3, 4), hm(1, 2).with(3, 4));
        assertEquals(hm(1, 2, 3, 4, 5, 6), hm(1, 2).with(3, 4, 5, 6));
        assertEquals(hm(1, 2, 3, 4), hm(1, 2).with(hm(3, 4)));

        assertEquals(hm(0, 1, 2, 4, 4, 5), hm(0, 1, 2, 3, 4, 5).with(2, 4));
        assertEquals(hm(0, 1, 2, 4, 4, 6), hm(0, 1, 2, 3, 4, 5).with(2, 4, 4, 6));
        assertEquals(hm(0, 1, 2, 4, 4, 5), hm(0, 1, 2, 3, 4, 5).with(hm(2, 4)));
    }

}