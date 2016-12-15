package yk.jcommon.match2.generator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.match2.MatcherShortNames.var;

/**
 * Created by Yuri Kravchik on 19/11/16.
 */
public class GenMatcherTest {

    @Test
    public void test1() {
        assertEquals("{}", new GenMatcher("a", "a").next().toString());
        assertEquals(null, new GenMatcher("a", "b").next());
        assertEquals("{}", new GenMatcher(al("a", "b"), new GenMatchListElement("a")).next().toString());
        assertEquals(null, new GenMatcher(al("a", "b"), new GenMatchListElement("c")).next());

        GenMatcher gen = new GenMatcher(al("a", "b"), new GenMatchListElement(var("x")));
        assertEquals("{x=a}", gen.next().toString());
        assertEquals("{x=a}", gen.next().toString());
        assertEquals("{x=a}", gen.next().toString());
        assertEquals("{x=a}", gen.next().toString());
    }

}