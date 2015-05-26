package yk.jcommon.collections;

import org.junit.Test;

import static yk.jcommon.collections.YArrayList.al;
import static junit.framework.Assert.assertEquals;

public class TestCollections {

    public static void main(String[] args) {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        System.out.println(all.shuffle(all)
                .map(p -> p.toSet().toList().sorted().toString())
                .toSet().toList().sorted().toString("", "\n"));
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
        assertEquals(9, (int)al(2, 3, 4).fold(0, (a, b) -> a + b));
        assertEquals(24, (int)al(2, 3, 4).fold(1, (a, b) -> a * b));
        YArrayList<Integer> l = al();
        assertEquals(1, (int) l.fold(1, (a, b) -> a * b));
    }
}