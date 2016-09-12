package yk.jcommon.match2;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.collections.YHashSet.hs;
import static yk.jcommon.match2.MatcherShortNames.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 01/11/15
 * Time: 17:02
 */
public class TestMatcher {
    //todo test deeper with 0 deepness

    @Test
    public void test1() {
        assertEquals(hs(hm()), new Matcher().match(al("a", "b"), i("a")));
        assertEquals(hs(hm("index", 0)), new Matcher().match(al("a", "b"), i(var("index"), "a")));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), new Matcher().match(al("a", "b"), i(var("$a"))));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), new Matcher().match(al("a", "b"), new MatchAnd(i(var("$a")))));
        assertEquals(hs(hm("$a", "b"), hm("$a", "a")), new Matcher().match(al("a", "b"), new MatchAnd(i("a"), i(var("$a")))));
        assertEquals(hs(hm("$a", "a", "iFirst", 0, "iSecond", 0), hm("$a", "b", "iFirst", 0, "iSecond", 1)),
                new Matcher().match(al("a", "b"), new MatchAnd(i(var("iFirst"), "a"), i(var("iSecond"), var("$a")))));
    }

    @Test
    public void testList3() {
        assertEquals(hs(), new Matcher().match(al(), new MatchList3(new MatchAny(), "b", new MatchAny())));
        assertEquals(hs(), new Matcher().match(al(), new MatchList3(new MatchAny(), new MatchAny(), new MatchAny())));//TODO or this matches?
        assertEquals(hs(hm()), new Matcher().match(al("a"), new MatchList3(new MatchAny(), "a", new MatchAny())));
        assertEquals(hs(hm("$a", al())), new Matcher().match(al("a"), new MatchList3(var("$a"), "a", new MatchAny())));
        assertEquals(hs(hm("$a", al())), new Matcher().match(al("a"), new MatchList3(new MatchAny(), "a", var("$a"))));
        assertEquals(hs(hm("$a", al())), new Matcher().match(al("a"), new MatchList3(var("$a"), "a", var("$a"))));
        assertEquals(hs(hm()), new Matcher().match(al("a", "b"), new MatchList3(new MatchAny(), "b", new MatchAny())));
        assertEquals(hs(hm("$a", al("a"))), new Matcher().match(al("a", "b"), new MatchList3(var("$a"), "b", new MatchAny())));
        assertEquals(hs(), new Matcher().match(al("a", "b", "c"), new MatchList3(var("$a"), "a", var("$a"))));
        assertEquals(hs(hm("$a", al(), "$b", al("b", "c"))), new Matcher().match(al("a", "b", "c"), new MatchList3(var("$a"), "a", var("$b"))));

        assertEquals(hs(hm("$a", al(), "$b", al("a", "c")), hm("$a", al("a"), "$b", al("c"))), new Matcher().match(al("a", "a", "c"), new MatchList3(var("$a"), "a", var("$b"))));

        assertEquals(hs(hm("$a", al())), new Matcher().match(al("a"), new MatchList3(var("$a"), "a", new MatchAny(), 0)));
        assertEquals(hs(), new Matcher().match(al("a"), new MatchList3(var("$a"), "a", new MatchAny(), 1)));

        assertEquals(hs(), new Matcher().match(al("a", "b"), new MatchList3(var("$a"), "b", new MatchAny(), 0)));
        assertEquals(hs(hm("$a", al("a"))), new Matcher().match(al("a", "b"), new MatchList3(var("$a"), "b", new MatchAny(), 1)));

        assertEquals(hs(hm("$a", al("a"), "$i", 1)), new Matcher().match(al("a", "b"), new MatchList3(var("$a"), "b", new MatchAny(), var("$i") )));
        assertEquals(hs(hm("$a", al(), "$i", 0), hm("$a", al("a"), "$i", 1)), new Matcher().match(al("a", "b"), new MatchList3(var("$a"), new MatchAny(), new MatchAny(), var("$i") )));
    }

    @Test
    public void testList() {
        assertEquals("[{}]", new Matcher().match(al(), al()) + "");
        assertEquals("[{}]", new Matcher().match(al("a"), al("a")) + "");
        assertEquals("[]", new Matcher().match(al("a"), al()) + "");
        assertEquals("[]", new Matcher().match(al(), al("a")) + "");
        assertEquals("[]", new Matcher().match("", al("a")) + "");

        assertEquals("[{$b=b}]", new Matcher().match(al("a", "b"), al("a", var("$b"))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b"), al(var("$b"), var("$b"))) + "");
        assertEquals("[{$a=a, $b=b}]", new Matcher().match(al("a", "b"), al(var("$a"), var("$b"))) + "");

    }

    @Test
    public void testMap() {
        assertEquals("[{}]", new Matcher().match(hm(), hm()) + "");
        assertEquals("[{}]", new Matcher().match(hm("ka", "va"), hm("ka", "va")) + "");
        assertEquals("[{}]", new Matcher().match(hm("ka", "va"), hm()) + "");
        assertEquals("[]", new Matcher().match(hm(), hm("ka", "va")) + "");
        assertEquals("[]", new Matcher().match("", hm("ka", "va")) + "");

        assertEquals("[{$va=va}]", new Matcher().match(hm("ka", "va", "kb", "vb"), hm("ka", var("$va"))) + "");
        assertEquals("[]", new Matcher().match(hm("ka", "va", "kb", "vb"), hm("ka", var("$va"), "kb", var("$va"))) + "");
        assertEquals("[{$va=va, $vb=vb}]", new Matcher().match(hm("ka", "va", "kb", "vb"), hm("ka", var("$va"), "kb", var("$vb"))) + "");

    }

    @Test
    public void testMapFullMatcher() {
        assertEquals("[{$other={}}]", new Matcher().match(hm(), new MatchMap(var("$other"), hm())) + "");
        assertEquals("[{$other={}}]", new Matcher().match(hm("ka", "va"), new MatchMap(var("$other"), hm("ka", "va"))) + "");
        assertEquals("[]", new Matcher().match(hm(), new MatchMap(var("$other"), hm("ka", "va"))) + "");
        assertEquals("[]", new Matcher().match("", new MatchMap(var("$other"), hm("ka", "va"))) + "");

        assertEquals("[{$va=va, $other={kb=vb}}]", new Matcher().match(hm("ka", "va", "kb", "vb"), new MatchMap(var("$other"), hm("ka", var("$va")))) + "");
        assertEquals("[{$va=va, $vb=vb, $other={}}]", new Matcher().match(hm("ka", "va", "kb", "vb"), new MatchMap(var("$other"), hm("ka", var("$va"), "kb", var("$vb")))) + "");

        assertEquals("[{$other={}, $va=va, $vb=vb}]", new Matcher().match(al(hm(), hm("ka", "va", "kb", "vb")), al(var("$other"), new MatchMap(var("$other"), hm("ka", var("$va"), "kb", var("$vb"))))) + "");
        assertEquals("[{$va=va, $vb=vb, $other={}}]", new Matcher().match(al(hm("ka", "va", "kb", "vb"), hm()), al(new MatchMap(var("$other"), hm("ka", var("$va"), "kb", var("$vb"))), var("$other"))) + "");

        assertEquals("[]", new Matcher().match(al(hm("ka", "va", "kb", "vb"), hm()), al(new MatchMap(var("$other"), hm("ka", var("$va"))), var("$other"))) + "");
        assertEquals("[{$va=va, $other={kb=vb}}]", new Matcher().match(al(hm("ka", "va", "kb", "vb"), hm("kb", "vb")), al(new MatchMap(var("$other"), hm("ka", var("$va"))), var("$other"))) + "");

        assertEquals("[{$vb=va, $other={kb=va}}]", new Matcher().match(
                al(hm("ka", "va", "kb", "va"), hm("kb", "va")),
                al(new MatchMap(var("$other"), hm("ka", var("$vb"))), hm("kb", var("$vb")))
        ) + "");

        assertEquals("[]", new Matcher().match(
                al(hm("ka", "va", "kb", "va"), hm("kb", "vb")),
                al(new MatchMap(var("$other"), hm("ka", var("$vb"))), hm("kb", var("$vb")))
        ) + "");
    }

    @Test
    public void testMatchList() {
        assertEquals("[{}]", new Matcher().match(al(), ml()) + "");
        assertEquals("[{}]", new Matcher().match(al("a"), ml("a")) + "");
        assertEquals("[]", new Matcher().match(al("a"), ml()) + "");
        assertEquals("[]", new Matcher().match(al(), ml("a")) + "");
        assertEquals("[]", new Matcher().match("", ml("a")) + "");

        assertEquals("[{$b=b}]", new Matcher().match(al("a", "b"), ml("a", var("$b"))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b"), ml(var("$b"), var("$b"))) + "");
        assertEquals("[{$a=a, $b=b}]", new Matcher().match(al("a", "b"), ml(var("$a"), var("$b"))) + "");

    }

    @Test
    public void testMatchListFillers() {

        assertEquals("[{}]", new Matcher().match(al(), ml(listFiller())) + "");
        assertEquals("[{}]", new Matcher().match(al("a"), ml(listFiller())) + "");
        assertEquals("[{}]", new Matcher().match(al("a", "b"), ml(listFiller())) + "");

        assertEquals("[{}]", new Matcher().match(al("a", "b"), ml(listFiller().setMaxLength(2))) + "");
        assertEquals("[{}]", new Matcher().match(al(), ml(listFiller().setMaxLength(0))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b"), ml(listFiller().setMaxLength(0))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b"), ml(listFiller().setMaxLength(1))) + "");

        assertEquals("[{}]", new Matcher().match(al("a", "b"), ml(listFiller().setMinLength(0))) + "");
        assertEquals("[{}]", new Matcher().match(al("a", "b"), ml(listFiller().setMinLength(2))) + "");
        assertEquals("[{}]", new Matcher().match(al(), ml(listFiller().setMinLength(0))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b"), ml(listFiller().setMinLength(3))) + "");

        assertEquals("[]", new Matcher().match(al(), ml(listFiller().setMinLength(1).setMaxLength(2))) + "");
        assertEquals("[{}]", new Matcher().match(al("a"), ml(listFiller().setMinLength(1).setMaxLength(2))) + "");
        assertEquals("[{}]", new Matcher().match(al("a", "b"), ml(listFiller().setMinLength(1).setMaxLength(2))) + "");
        assertEquals("[]", new Matcher().match(al("a", "b", "c"), ml(listFiller().setMinLength(1).setMaxLength(2))) + "");


        assertEquals("[{$v=a}, {$v=b}, {$v=c}]", new Matcher().match(al("a", "b", "c"), ml(listFiller(), var("$v"), listFiller())) + "");
        assertEquals("[{$before=[], $v=a, $after=[b, c]}, {$before=[a], $v=b, $after=[c]}, {$before=[a, b], $v=c, $after=[]}]",
                new Matcher().match(al("a", "b", "c"), ml(listFiller("$before"), var("$v"), listFiller("$after"))) + "");
        assertEquals("[{$before1=[], $before2=[], $v=a, $after=[b, c]}, {$before1=[], $before2=[a], $v=b, $after=[c]}, {$before1=[], $before2=[a, b], $v=c, $after=[]}, {$before1=[a], $before2=[], $v=b, $after=[c]}, {$before1=[a], $before2=[b], $v=c, $after=[]}, {$before1=[a, b], $before2=[], $v=c, $after=[]}]",
                new Matcher().match(al("a", "b", "c"), ml(listFiller("$before1"), listFiller("$before2"), var("$v"), listFiller("$after"))) + "");
        assertEquals("[{$before=[], $v=a, $after1=[], $after2=[b, c]}, {$before=[], $v=a, $after1=[b], $after2=[c]}, {$before=[], $v=a, $after1=[b, c], $after2=[]}, {$before=[a], $v=b, $after1=[], $after2=[c]}, {$before=[a], $v=b, $after1=[c], $after2=[]}, {$before=[a, b], $v=c, $after1=[], $after2=[]}]",
                new Matcher().match(al("a", "b", "c"), ml(listFiller("$before"), var("$v"), listFiller("$after1"), listFiller("$after2"))) + "");
        assertEquals("[{$v1=a, $mid=[b], $v2=c}]",
                new Matcher().match(al("a", "b", "c"), ml(var("$v1"),listFiller("$mid"), var("$v2"))) + "");
        assertEquals("[{$v1=a, $mid=[], $v2=b, $after=[c]}, {$v1=a, $mid=[b], $v2=c, $after=[]}]",
                new Matcher().match(al("a", "b", "c"), ml(var("$v1"),listFiller("$mid"), var("$v2"), listFiller("$after"))) + "");

        assertEquals("[{$v1=a, $mid=[], $v2=b, $after=[c]}]",
                new Matcher().match(al(al("a", "b", "c"), al("c")), al(ml(var("$v1"),listFiller("$mid"), var("$v2"), listFiller("$after")), var("$after"))) + "");
        assertEquals("[{$after=[c], $v1=a, $mid=[], $v2=b}]",
                new Matcher().match(al(al("c"), al("a", "b", "c")), al(var("$after"), ml(var("$v1"),listFiller("$mid"), var("$v2"), listFiller("$after")))) + "");

        assertEquals("[{$common=[]}, {$common=[b]}, {$common=[b, c]}, {$common=[c]}]",
                new Matcher().match(al(al("a", "b", "c"), al("b", "c", "d")), al(ml(listFiller(), listFiller("$common"), listFiller()), ml(listFiller(), listFiller("$common"), listFiller()))) + "");

    }

    @Test
    public void testMatchOr() {
        assertEquals("[{1=a, 2=b}]", new Matcher().match(al("a", "b", "b"), new MatchOr(al(var("x"), var("y"), var("x")), al(var("1"), var("2"), var("2")))) + "");
        assertEquals("[]",           new Matcher().match(al("a", "a", "b"), new MatchOr(al(var("x"), var("y"), var("x")), al(var("1"), var("2"), var("2")))) + "");
        assertEquals("[{x=a, y=b}]", new Matcher().match(al("a", "b", "a"), new MatchOr(al(var("x"), var("y"), var("x")), al(var("1"), var("2"), var("2")))) + "");
    }

    @Test
    public void testVarCalc() {
//        assertEquals(
//                hs(hm("a", 3)),
//                Matcher.match(al(3, 4), new And(i(var("a")), i((VarCalc) cur -> ((Integer)cur.get("a")) + 1)))
//                );
    }

}
