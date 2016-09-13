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
public class MatcherShortNames {
    public static final Object OTHER = new Object();

    public static MatchProperty p(String name, Object... rest) {
        return MatchProperty.p(name, rest);
    }

    public static MatchProperty p1(String name, boolean isMethod, Object... rest) {
        return MatchProperty.p1(name, isMethod, rest);
    }

    public static MatchVar var(String name) {
        return new MatchVar(name);
    }

    public static MatchVar var(String name, Object rest) {
        return new MatchVar(name, rest);
    }

    public static MatchProperty p(Class c, Object... rest) {
        return MatchProperty.p("getClass", al(c).with((List) al(rest)).toArray());
    }

    public static MatchProperty p1(Class c, Object... rest) {
        return MatchProperty.p1("getClass", true, al(c).with((List) al(rest)).toArray());
    }

    public static MatchByIndex i(Object rest) {
        return new MatchByIndex(rest);
    }

    public static MatchByIndex i() {
        return new MatchByIndex();
    }

    public static MatchByIndex i(Object index, Object rest) {
        return new MatchByIndex(index, rest);
    }

    public static MatchList ml(Object... oo) {
        return new MatchList(al(oo));
    }

    public static MatchList.Filler listFiller() {
        return new MatchList.Filler();
    }

    public static MatchList.Filler listFiller(String varName) {
        return new MatchList.Filler().setInside(var(varName));
    }

    public static MatchList.Filler listFiller(Object inside) {
        return new MatchList.Filler().setInside(inside);
    }

    public static MatchDeeper deeper(YList<Object> accessorPatterns) {
        return new MatchDeeper(accessorPatterns);
    }

    public static Object stairs(Object... oo) {
        Object last = null;
        for (Object o : al(oo).reverse()) {
            if (last == null);
            else if (o instanceof MatchProperty) {
                MatchProperty p = (MatchProperty) o;
                Tuple<String, MatchProperty.PropertyDesc> lastPare = p.pp.last();
                if (lastPare.b.value != null) BadException.die("last pare in Property must be key to null, but was " + lastPare);
                lastPare.b.value = last;
            } else if (o instanceof MatchByIndex) {
                MatchByIndex bi = (MatchByIndex) o;
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
