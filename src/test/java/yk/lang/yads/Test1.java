package yk.lang.yads;

import org.junit.Test;
import yk.jcommon.collections.YList;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 08/02/15
 * Time: 16:40
 */
public class Test1 {

    @Test
    public void primitives() {
        assertEquals(al("hello", "hello", "hello", "", ""), YADSParser.parseList("hello 'hello' \"hello\" '' \"\""));
        assertEquals(al(10, 10l, 10L, 10f, 10d, 10D, 10.1f, 10.1f), YADSParser.parseList("10 10l 10L 10f 10d 10D 10.1 10.1f"));
    }

}
