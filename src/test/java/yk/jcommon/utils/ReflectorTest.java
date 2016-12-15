package yk.jcommon.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static yk.jcommon.fastgeom.Vec3f.v3;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 04/11/16
 * Time: 10:21
 */
public class ReflectorTest {

    @Test
    public void testOverloading() {
//        Vec3f v1 = new Vec3f(5, 5, 5);
//        assertEquals("x: 6.0 y: 6.0 z: 6.0", Reflector.invokeMethodOverloaded(v1, "add", Vec3f.class, 1f).toString());
//        v1.add(1f, 2, 3);
//        assertEquals("x: 6.0 y: 7.0 z: 8.0", Reflector.invokeMethodOverloaded(v1, "add", Vec3f.class, 1f, 2, 3).toString());
//        try {
//            Reflector.invokeMethodOverloaded(v1, "add", Vec3f.class, 1f, 2, 3d);
//            fail();
//        } catch (Exception ignore){}
//
//        System.out.println(Reflector.invokeMethodOverloaded(v1, "add", Vec3f.class, 1f, 2, 3d));
    }

    @Test
    public void testSet() {
        assertEquals(v3(1, 2, 1).toString(), Reflector.set(v3(1, 1, 1), "y", 2).toString());
        assertEquals(v3(1, 2, 1).toString(), Reflector.set(v3(1, 1, 1), "y", (byte)2).toString());
        assertEquals(v3(1, 2, 1).toString(), Reflector.set(v3(1, 1, 1), "y", (long)2).toString());

        assertEquals(new Foo(2f).toString(), Reflector.set(new Foo(1f), "f", 2).toString());
    }

    private static class Foo {
        public Float f;

        public Foo(Float f) {
            this.f = f;
        }

        @Override
        public String toString() {
            return "Foo{" +
                   "f=" + f +
                   '}';
        }
    }
}