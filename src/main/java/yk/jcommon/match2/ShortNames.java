package yk.jcommon.match2;

import yk.jcommon.collections.Tuple;
import yk.jcommon.collections.YList;
import yk.jcommon.utils.BadException;
import yk.jcommon.utils.Reflector;

import java.util.List;

import static yk.jcommon.collections.YArrayList.al;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 01/11/15
 * Time: 17:04
 */
public class ShortNames {
    public static Property p(String name, Object... rest) {
        return Property.p(name, rest);
    }

    public static Property p1(String name, boolean isMethod, Object... rest) {
        return Property.p1(name, isMethod, rest);
    }

    public static Var var(String name) {
        return new Var(name);
    }

    public static Var var(String name, Object rest) {
        return new Var(name, rest);
    }

    public static Property p(Class c, Object... rest) {
        return Property.p("getClass", al(c).with((List) al(rest)).toArray());
    }

    public static Property p1(Class c, Object... rest) {
        return Property.p1("getClass", true, al(c).with((List) al(rest)).toArray());
    }

    public static ByIndex i(Object rest) {
        return new ByIndex(rest);
    }

    public static ByIndex i() {
        return new ByIndex();
    }

    public static ByIndex i(Object index, Object rest) {
        return new ByIndex(index, rest);
    }

    public static Deeper deeper(YList<Object> accessorPatterns) {
        return new Deeper(accessorPatterns);
    }

    public static Object stairs(Object... oo) {
        Object last = null;
        for (Object o : al(oo).reverse()) {
            if (last == null);
            else if (o instanceof Property) {
                Property p = (Property) o;
                Tuple<String, Property.PropertyDesc> lastPare = p.pp.last();
                if (lastPare.b.value != null) BadException.die("last pare in Property must be key to null, but was " + lastPare);
                lastPare.b.value = last;
            } else if (o instanceof ByIndex) {
                ByIndex bi = (ByIndex) o;
                if (bi.rest != null) bi.index = bi.rest;
                bi.rest = last;
            } else {
                if (Reflector.getField(o.getClass(), "rest") == null) BadException.die("expected object with field 'rest', but was " + o);
                if (Reflector.get(o, "rest") != null) BadException.die("expected null at 'rest' but was " + Reflector.get(o, "rest"));
                Reflector.set(o, "rest", last);
            }
            last = o;
        }
        return last;
    }

}
