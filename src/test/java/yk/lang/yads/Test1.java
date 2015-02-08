package yk.lang.yads;

import org.junit.Test;
import yk.jcommon.collections.Tuple;
import yk.jcommon.collections.YList;
import yk.jcommon.fastgeom.Vec2f;

import static junit.framework.Assert.assertEquals;
import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 08/02/15
 * Time: 16:40
 */
public class Test1 {

    @Test
    public void parsePrimitives() {
        assertEquals(al("hello", "hello", "hello", "", ""), YADSParser.parseList("hello 'hello' \"hello\" '' \"\""));
        assertEquals(al(10, 10l, 10L, 10f, 10d, 10D, 10.1d, 10.1f, 10.1f), YADSParser.parseList("10 10l 10L 10f 10d 10D 10.1d 10.1f 10.1"));
        assertEquals(al(true, false), YADSParser.parseList("true false"));
    }

    @Test
    public void reflector() {
        assertEquals(al("hello", "world"), YADSSerializer.deserialize("hello world"));
        assertEquals(al(al("hello", "world"), al("hello2", "world2")), YADSSerializer.deserialize("(hello world) (hello2 world2)"));
        assertEquals(hm("hello", "world"), YADSSerializer.deserialize("hello:world"));

        assertEquals(new YADClass(null, al(new Tuple("a", "b"), "c")), YADSSerializer.deserialize("a:b c"));
        assertEquals(al(new YADClass(null, al(new Tuple("a", "b"), "c"))), YADSSerializer.deserialize("(a:b c)"));
        assertEquals(al(new YADClass("name", al(new Tuple("a", "b"), "c"))), YADSSerializer.deserialize("name(a:b c)"));
    }

    @Test
    public void reflectorImports() {
        assertEquals(al(new YADClass("XY", al(1, 2))), YADSSerializer.deserialize("XY(1 2)"));
        assertEquals(al(new Vec2f(1, 2)), YADSSerializer.deserialize("yk.jcommon.fastgeom.Vec2f(x:1 y:2)"));
        assertEquals(al(new Vec2f(1, 2)), YADSSerializer.deserialize("import:yk.jcommon.fastgeom \n Vec2f(x:1 y:2)"));
    }

    @Test
    public void test() {
        System.out.println(YADSParser.parseList("hello world"));
        System.out.println(YADSParser.parseClass("XY(10 20)"));
        System.out.println(YADSParser.parseClass("HBox(pos : 10, 20 VBox(size: 50, 50))"));
        System.out.println(HBox.class.getName());
        System.out.println(YADSSerializer.parseClass(null, YADSParser.parseClass("HBox(pos : 10, 20)")).toString());

        System.out.println(YADSSerializer.parseList(YADSParser.parseList("import: yk.lang.yads HBox(pos : 10, 20)")).toString());

        //TODO convert with respect to method call arguments types!
        //TODO map or YAD if class not defined and unknown

    }


}
