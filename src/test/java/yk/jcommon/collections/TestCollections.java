package yk.jcommon.collections;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;

public class TestCollections {

    public static void main(String[] args) {
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
    public void testEachToEach() {
        YList<String> all = al("shift", "ctrl", "alt", "super");
        assertEquals("[[alt, ctrl], [alt, shift], [alt, super], [alt], [ctrl, shift], [ctrl, super], [ctrl], [shift, super], [shift], [super]]"
                , all.eachToEach(all).map(p -> p.toSet().toList().sorted().toString()).toSet().toList().sorted().toString());
    }

    @Test
    public void testFold() {
        //System.out.println(al(2, 3, 4).fold(1, (a, b) -> a * b)); ahaha - internal compilator error
        assertEquals(9, (int)al(2, 3, 4).reduce(0, (a, b) -> a + b));
        assertEquals(24, (int)al(2, 3, 4).reduce(1, (a, b) -> a * b));
        YArrayList<Integer> l = al();
        assertEquals(1, (int) l.reduce(1, (a, b) -> a * b));
    }
}
