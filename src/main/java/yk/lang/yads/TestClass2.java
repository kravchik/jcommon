package yk.lang.yads;

import yk.jcommon.collections.YList;
import yk.jcommon.utils.BadException;

import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

public class TestClass2 implements YadsAware {

    public float a, b;

    public TestClass2(float a) {
        this.a = a;
        this.b = a;
    }

    public TestClass2(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public TestClass2(List body) {
        if (body.size() == 1) {
            a = ((Number) body.get(0)).floatValue();
            b = a;
        } else if (body.size() == 2) {
            a = ((Number) body.get(0)).floatValue();
            b = ((Number) body.get(1)).floatValue();
        } else {
            BadException.die("can't parse " + body);
        }
    }

    @Override
    public List yadsSerialize(boolean typeIsKnown) {
        if (typeIsKnown) {
            YList result = al();
            result.add(a == b ? al(a) : al(a, b));
            return result;
        } else {
            return al(getClass().getSimpleName(), yadsSerialize(true).get(0));
        }
    }

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
    public YList serializeInner() {
        return a == b ? al(a) : al(a, b);
    }
}
