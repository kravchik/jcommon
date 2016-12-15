package yk.jcommon.match2;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.match2.MatcherShortNames.listFiller;
import static yk.jcommon.match2.MatcherShortNames.var;

/**
 * Created by Yuri Kravchik on 18/11/16.
 */
public class MatchListTest {

    @Test
    public void testList() {
        assertEquals("[{before=a}]", new Matcher().match(al("a", "b", "c"), new MatchList(al(var("before"), "b", "c"))).toString());
        assertEquals("[{before=a, after=c}]", new Matcher().match(al("a", "b", "c"), new MatchList(al(var("before"), "b", var("after")))).toString());

        assertEquals("[{before=[a], between=[]}]", new Matcher().match(al("a", "b", "c"), new MatchList(al(listFiller("before"), "b", listFiller("between"), "c"))).toString());
        assertEquals("[{before=[a], between=[], after=[]}]", new Matcher().match(al("a", "b", "c"), new MatchList(al(listFiller("before"), "b", listFiller("between"), "c", listFiller("after")))).toString());
    }


}