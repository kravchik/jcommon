package yk.jcommon.match2;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;
import static yk.jcommon.match2.ShortNames.i;
import static yk.jcommon.match2.ShortNames.var;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 01/11/15
 * Time: 17:02
 */
public class Test1 {

    @Test
    public void test1() {
        assertEquals(hs(hm()), Matcher.match(al("a", "b"), i("a")));
        assertEquals(hs(hm("index", 0)), Matcher.match(al("a", "b"), i(var("index"), "a")));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), Matcher.match(al("a", "b"), i(var("$a"))));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), Matcher.match(al("a", "b"), new And(i(var("$a")))));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), Matcher.match(al("a", "b"), new And(i("a"), i(var("$a")))));
        assertEquals(hs(hm("$a", "a", "iFirst", 0, "iSecond", 0), hm("$a", "b", "iFirst", 0, "iSecond", 1)),
                Matcher.match(al("a", "b"), new And(i(var("iFirst"), "a"), i(var("iSecond"), var("$a")))));
    }

    //todo test deeper with 0 deepness

}
