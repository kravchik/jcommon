package yk.jcommon.collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created by Yuri Kravchik on 20.12.2018
 */
public class YHashMapTest {

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