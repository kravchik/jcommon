package yk.jcommon.collections;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;
import static yk.jcommon.collections.YArrayList.al;

public class TestCollections {

    public static void main1(String[] args) {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        System.out.println(all.shuffle(all)
                .map(p -> p.toSet().toList().sorted().toString())
                .toSet().toList().sorted().toStringPrefixInfix("", "\n"));
    }

    @Test
    public void testShuffle() {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        assertEquals("[[alt, ctrl], [alt, shift], [alt, super], [alt], [ctrl, shift], [ctrl, super], [ctrl], [shift, super], [shift], [super]]"
                , all.shuffle(all).map(p -> p.toSet().toList().sorted().toString()).toSet().toList().sorted().toString());
    }

    @Test
    public void testFold() {
        //System.out.println(al(2, 3, 4).fold(1, (a, b) -> a * b)); ahaha - internal compilator error
        assertEquals(9, (int)al(2, 3, 4).reduce(0, (a, b) -> a + b));
        assertEquals(24, (int)al(2, 3, 4).reduce(1, (a, b) -> a * b));
        YArrayList<Integer> l = al();
        assertEquals(1, (int) l.reduce(1, (a, b) -> a * b));
    }

    public static void testSort(YCollection<Integer> c) {
        assertEquals(al(1), c.with(1).sorted(v -> -v).toList());
        assertEquals(al(4, 3, 2, 1), c.with(1, 2, 3, 4).sorted(v -> -v).toList());
        assertEquals(al(), c.with().sorted(v -> -v).toList());

        assertEquals(al(1), c.with(1).sorted((o1, o2) -> -o1.compareTo(o2)).toList());
        assertEquals(al(4, 3, 2, 1), c.with(1, 2, 3, 4).sorted((o1, o2) -> -o1.compareTo(o2)).toList());
        assertEquals(al(), c.with().sorted((o1, o2) -> -o1.compareTo(o2)).toList());

        assertEquals(al(1), c.with(1).sorted().toList());
        assertEquals(al(1, 2, 3, 4), c.with(4, 3, 2, 1).sorted().toList());
        assertEquals(al(), c.with().sorted().toList());
    }

    public static void testMax(YCollection<Integer> c) {
        try {
            c.max();
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).max());
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).max());
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).max());
        assertEquals((Integer) 4, c.with(4).max());

        try {
            c.max((o1, o2) -> o1.compareTo(o2));
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).max((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 1, c.with(1).max((o1, o2) -> -o1.compareTo(o2)));

        try {
            c.max(i -> -i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).max(i -> -i));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).max(i -> -i));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).max(i -> -i));
        assertEquals((Integer) 1, c.with(1, 3, 4, 2).max(i -> -i));
        assertEquals((Integer) 1, c.with(1).max(i -> -i));

    }

    private static void testMin(YCollection<Integer> c) {
        try {
            c.min();
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).min());
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).min());
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).min());
        assertEquals((Integer) 1, c.with(1).min());

        try {
            c.min((o1, o2) -> o1.compareTo(o2));
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).min((o1, o2) -> -o1.compareTo(o2)));
        assertEquals((Integer) 4, c.with(4).min((o1, o2) -> -o1.compareTo(o2)));

        try {
            c.min(i -> i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).min(i -> -i));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).min(i -> -i));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).min(i -> -i));
        assertEquals((Integer) 4, c.with(4).min(i -> -i));
    }

    @Test
    public void testList() {
        testSort(YArrayList.al());
        testMax(YArrayList.al());
        testMin(YArrayList.al());
    }

    @Test
    public void testSet() {
        testSort(YHashSet.hs());
        testMax(YHashSet.hs());
        testMin(YHashSet.hs());
    }

}
