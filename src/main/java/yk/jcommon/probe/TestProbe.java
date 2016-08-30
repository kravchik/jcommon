package yk.jcommon.probe;

import org.junit.Test;
import yk.jcommon.collections.YArrayList;
import yk.jcommon.fastgeom.Vec3f;
import yk.jcommon.search.SSearch;

import static junit.framework.TestCase.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;
import static yk.jcommon.match2.MatcherShortNames.p;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 02/11/15
 * Time: 11:04
 */
public class TestProbe {

    @Test
    public void test1() {
        {
            assertEquals("byKey(v{class java.lang.String}){class yk.jcommon.collections.YArrayList}\n" +
                            "[0]{class yk.jcommon.fastgeom.Vec3f}\n" +
                            ".y{class java.lang.Float}",
                    Probe.find(hm("v", al(new Vec3f(3, 4, 5))), 4f).toString("\n"));
        }

        {
            Probe probe = new Probe(hm("k", al("v")), p(YArrayList.class));
            SSearch.Node<State> node = probe.nextSolution(100);
            System.out.println("!" + node.state.stackTrace.toString("\n"));
        }

    }

}
