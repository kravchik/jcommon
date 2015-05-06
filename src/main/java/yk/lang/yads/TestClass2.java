package yk.lang.yads;

import yk.jcommon.collections.YList;

import java.util.List;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YArrayList.toYList;

public class TestClass2 implements YadsAwareConstructor {

    public float a, b;

    public YList<String> ss;

    public TestClass2(float a) {
        this.a = a;
        this.b = a;
    }

    public TestClass2(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public TestClass2(List<String> ss) {
        this.ss = toYList(ss);
    }

    //public TestClass2(List body) {
    //    if (body.size() == 1) {
    //        a = ((Number) body.get(0)).floatValue();
    //        b = a;
    //    } else if (body.size() == 2) {
    //        a = ((Number) body.get(0)).floatValue();
    //        b = ((Number) body.get(1)).floatValue();
    //    } else {
    //        BadException.die("can't parse " + body);
    //    }
    //}

    //@Override
    //public String yadsSerialize() {
    ////    return al(getClass().getSimpleName(), yadsSerializeWhenTypeIsKnown())
        //return null;
    //}

    //@Override
    //public String yadsSerializeWhenTypeIsKnown() {
        //return al(a) or al(a, b)
        //if (a == b) return "{" + a + "}";
        //return "{" + a + " " + b + "}";
    //}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass2 that = (TestClass2) o;

        if (Float.compare(that.a, a) != 0) return false;
        if (Float.compare(that.b, b) != 0) return false;
        if (ss != null ? !ss.equals(that.ss) : that.ss != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (a != +0.0f ? Float.floatToIntBits(a) : 0);
        result = 31 * result + (b != +0.0f ? Float.floatToIntBits(b) : 0);
        result = 31 * result + (ss != null ? ss.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestClass2{" +
                "a=" + a +
                ", b=" + b +
                ", ss=" + ss +
                '}';
    }

    @Override
    public List genConstructorArguments() {
        return a == b ? al(a) : al(a, b);
    }
}
