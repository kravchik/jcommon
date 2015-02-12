package yk.lang.yads;

import org.junit.Test;
import yk.jcommon.collections.Tuple;
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
        assertEquals(al("\"hello\"", "'hello'"), YADSParser.parseList(" '\\\"hello\\\"'  '\\'hello\\''"));
        assertEquals(al("hello\nworld", "hello\tworld"), YADSParser.parseList(" 'hello\\nworld'  'hello\\tworld'"));

        assertEquals(al("hello\nworld", "hello\tworld"), YADSParser.parseList(" 'hello\nworld'  'hello\tworld'"));

        assertEquals(al("\"'hello world'\"", "\"'hello world'\""), YADSParser.parseList(" \"\\\"'hello world'\\\"\"  '\"\\'hello world\\'\"'"));
        assertEquals(al("\\'"), YADSParser.parseList(" \"\\\\'\" "));
        assertEquals(al("\\\""), YADSParser.parseList(" '\\\\\"' "));

        assertEquals(al(10, 10l, 10L, 10f, 10d, 10D, 10.1d, 10.1f, 10.1f), YADSParser.parseList("10 10l 10L 10f 10d 10D 10.1d 10.1f 10.1"));
        assertEquals(al(true, false), YADSParser.parseList("true false"));
    }

    @Test
    public void deserializer() {
        assertEquals(al("hello", "world"), YADSSerializer.deserialize("hello world"));
        assertEquals(al(al("hello", "world"), al("hello2", "world2")), YADSSerializer.deserialize("{hello world} {hello2 world2}"));
        assertEquals(hm("hello", "world"), YADSSerializer.deserialize("hello=world"));

        assertEquals(new YADClass(null, al(new Tuple("a", "b"), "c")), YADSSerializer.deserialize("a=b c"));
        assertEquals(al(new YADClass(null, al(new Tuple("a", "b"), "c"))), YADSSerializer.deserialize("{a=b c}"));
        assertEquals(al(new YADClass("name", al(new Tuple("a", "b"), "c"))), YADSSerializer.deserialize("name{a=b c}"));

        assertEquals(al(new TestEnumClass(TestEnum.ENUM1)), YADSSerializer.deserialize("TestEnumClass{enumField=ENUM1}"));
        assertEquals(al(TestEnum.ENUM1), YADSSerializer.deserialize("TestEnum{ENUM1}"));

        assertEquals(al(new TestEnumClass(null)), YADSSerializer.deserialize("TestEnumClass{enumField=null}"));
    }

    @Test
    public void deserializerImports() {
        assertEquals(al(new YADClass("XY", al(1, 2))), YADSSerializer.deserialize("XY{1 2}"));
        assertEquals(al(new Vec2f(1, 2)), YADSSerializer.deserialize("yk.jcommon.fastgeom.Vec2f{x=1 y=2}"));
        assertEquals(al(new Vec2f(1, 2)), YADSSerializer.deserialize("import=yk.jcommon.fastgeom \n Vec2f{x=1 y=2}"));
    }

    @Test
    public void serializer() {
        assertEquals("import= yk.jcommon.fastgeom\nVec2f {\n  x= 1.0\n  y= 2.0\n\n}\n", YADSSerializer.serialize(new Vec2f(1, 2)));
        assertEquals("{\n  'hello'\n  'world'\n}\n", YADSSerializer.serialize(al("hello", "world")));
        assertEquals("{\n  'k'= 'v'\n}\n", YADSSerializer.serialize(hm("k", "v")));

        assertEquals("import= yk.lang.yads\nTestEnumClass {\n  enumField= ENUM1\n\n}\n", YADSSerializer.serialize(new TestEnumClass(TestEnum.ENUM1)));
        assertEquals("import= yk.lang.yads\nTestEnumClass {\n\n}\n", YADSSerializer.serialize(new TestEnumClass(null)));

        assertEquals("{\n  'hello'\n  null\n}\n", YADSSerializer.serialize(al("hello", null)));

        assertEquals("{\n  'h\"e\\'l\\nl\\to'\n}\n", YADSSerializer.serialize(al("h\"e'l\nl\to")));
    }

    @Test
    public void testClass() {
        assertEquals(new TestClass(al(1, 2), hm("key1", "value1", "key2", "value2"), 3), YADSSerializer.deserialize(TestClass.class, "someList=1, 2 someMap={key1=value1 'key2'=value2} someInt=3"));
    }

    @Test
    public void test() {
        System.out.println("'\u005cn'");

        System.out.println(YADSParser.parseList("hello world"));
        System.out.println(YADSParser.parseClass("XY{10 20}"));
        System.out.println(YADSParser.parseClass("HBox{pos = 10, 20 VBox{size= 50, 50}}"));
        System.out.println(HBox.class.getName());
        //System.out.println(YADSSerializer.deserializeClass(null, YADSParser.parseClass("HBox{pos = 10, 20}")).toString());

        //System.out.println(YADSSerializer.deserializeList(YADSParser.parseList("import= yk.lang.yads HBox{pos = 10, 20}")).toString());

        //TODO convert with respect to method call arguments types!
        //TODO map or YAD if class not defined and unknown

    }


}
