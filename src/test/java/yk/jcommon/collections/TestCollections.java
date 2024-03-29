package yk.jcommon.collections;

import org.junit.Test;

import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;

public class TestCollections {

    public static void main1(String[] args) {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        System.out.println(all.eachToEach(all)     //take pares of each to each
                .map(p -> p                        //rework each pare
                        .toSet()                   //  convert to set to remove "alt alt" and similar
                        .sorted()                  //  sort (yes, it is a LinkedHashSet inside)
                        .toString(", ")            //  make a string
                )
                .toSet()                           //convert to set to remove duplicates ("alt shift", "shift alt")
                .sorted()                          //sort
                .toString("\n"));                  //make a result string
    }

    @Test
    public void testGroupBy() {
        assertEquals(hm(), YArrayList.<String>al().groupBy(s -> s.length()));
        assertEquals(hm(0, al("")), al("").groupBy(s -> s.length()));
        assertEquals(hm(2, al("aa", "bb"), 1, al("b")), al("aa", "bb", "b").groupBy(s -> s.length()));
    }

    @Test
    public void testEachToEach() {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        assertEquals("[[alt, ctrl], [alt, shift], [alt, super], [alt], [ctrl, shift], [ctrl, super], [ctrl], [shift, super], [shift], [super]]"
                , all.eachToEach(all).map(p -> p.toSet().toList().sorted().toString()).toSet().toList().sorted().toString());

        assertEquals(al(al("a", "c"), al("a", "d"), al("b", "c"), al("b", "d")), al("a", "b").eachToEach(al("c", "d")));

        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach(al("c", "d"), (a, b) -> a + b));
        assertEquals(al(), al("a", "b").eachToEach(al(), (a, b) -> a + b));
        assertEquals(al(), al().eachToEach(al("c", "d"), (a, b) -> a + b));
        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach((Collection<String>)al("c", "d"), (a, b) -> a + b));
        assertEquals(al("ac", "ad", "bc", "bd"), al("a", "b").eachToEach(al("c", "d"), (a, b) -> a + b));
    }

    @Test
    public void testReduce() {
        //System.out.println(al(2, 3, 4).fold(1, (a, b) -> a * b)); ahaha - internal compilator error
        assertEquals(9, (int)al(2, 3, 4).reduce(0, (a, b) -> a + b));
        assertEquals(24, (int)al(2, 3, 4).reduce(1, (a, b) -> a * b));
        YArrayList<Integer> l = al();
        assertEquals(1, (int) l.reduce(1, (a, b) -> a * b));
    }

    @Test
    public void testIfEmpty() {
        assertEquals("isEmpty", al().ifEmpty("isEmpty", (l) -> (String)l.first()));
        assertEquals("a", al("a", "b").ifEmpty("isEmpty", (l) -> l.first()));
    }

    private static void testSort(YCollection<Integer> c) {
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

    private static void testMax(YCollection<Integer> c) {
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

        try {
            c.maxByFloat(i -> -i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 1, c.with(1, 2, 3, 4).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(2, 1, 3, 4).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(2, 3, 4, 1).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(1, 3, 4, 2).maxByFloat(i -> -i));
        assertEquals((Integer) 1, c.with(1).maxByFloat(i -> -i));

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

        try {
            c.minByFloat(i -> i);
            fail();
        } catch (Exception ignore) {}
        assertEquals((Integer) 4, c.with(1, 2, 3, 4).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(1, 2, 4, 3).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(4, 1, 2, 3).minByFloat(i -> -i));
        assertEquals((Integer) 4, c.with(4).minByFloat(i -> -i));
    }

    private static void testCommon(YCollection<String> c) {
        assertEquals(c, c.filter(x -> true));
        assertEquals(c, c.with("2").filter(x -> !x.equals("2")));
        assertEquals(c.emptyInstance().with("1"), c.with("1").filter(x -> x.equals("1")));

        assertEquals("a", c.with("a", "b").firstOr(null));
        assertEquals("a", c.with("a", "b").first());
        assertEquals("b", c.with("b").firstOr("c"));
        assertEquals("b", c.with("b").first());
        assertEquals("c", c.with().firstOr("c"));
        assertEquals(null, c.with().firstOr(null));
        assertEquals("d", c.with().firstOrCalc(() -> "d"));
        assertEquals("a", c.with("a").firstOrCalc(() -> "d"));
        try {
            c.with().first();
            fail();
        } catch (Exception ignore) {}

        assertTrue(c.with("a", "b").isAny(s -> s.equals("a")));
        assertFalse(c.with("a", "b").isAny(s -> s.equals("c")));
        assertFalse(c.isAny(s -> s.equals("c")));

        assertTrue(c.with("a", "b").isAll(s -> s.length() == 1));
        assertFalse(c.with("a", "b", "cc").isAll(s -> s.length() == 1));
        assertTrue(c.isAll(s -> s.length() == 1));

    }

    private static void testForZip(YCollection<String> c) {
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.with("a1", "a2");
            YCollection<String> bb = c.with("b1", "b2");
            assertSame(aa, aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b))));
            assertEquals(al("a1", "b1", "a2", "b2"), sideEffects);
        }
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.emptyInstance();
            YCollection<String> bb = c.emptyInstance();
            assertSame(aa, aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b))));
            assertEquals(al(), sideEffects);
        }
        {
            YList<String> sideEffects = al();
            YCollection<String> aa = c.with("a1");
            YCollection<String> bb = c.emptyInstance();
            try {
                aa.forZip(bb, (a, b) -> sideEffects.addAll(al(a, b)));
                fail();
            } catch (RuntimeException ignore){}
        }
    }

    @Test
    public void testList() {
        testSort(al());
        testMax(al());
        testMin(al());
        testCommon(al());
        testForZip(al());

        assertEquals("b", al("a", "b").lastOr("c"));
        assertEquals("b", al("a", "b").last());
        assertEquals("b", al("b").lastOr("c"));
        assertEquals("b", al("b").last());
        assertEquals("c", al().lastOr("c"));
        assertEquals(null, al().lastOr(null));

        assertEquals("b", al("a", "b").lastOrCalc(() -> "c"));
        assertEquals("c", al().lastOrCalc(() -> "c"));
        try {
            al().last();
            fail();
        } catch (Exception ignore) {}
    }

    @Test
    public void testSet() {
        testSort(hs());
        testMax(hs());
        testMin(hs());
        testCommon(hs());
        testForZip(hs());
    }

}
