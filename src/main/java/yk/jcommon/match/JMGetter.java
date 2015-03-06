package yk.jcommon.match;

import yk.jcommon.collections.YList;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static yk.jcommon.collections.YArrayList.al;
//import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/20/13
 * Time: 8:00 PM
 *
 */
public class JMGetter<T> implements JM.Specific {
    public Class<T> c;
    public Function<T, Object> f;
    public Object pattern;

    protected JMGetter(Class<T> c, Function<T, Object> f, Object pattern) {
        this.f = f;
        this.c = c;
        this.pattern = pattern;
    }

    public static void main(String[] args) {
        //list with string, pattern will associate X with the string length
        YList pattern = al(new JMGetter<>(String.class, String::length, new JVar("X")));
        System.out.println(((JMGetter) pattern.get(0)).c);
        System.out.println(((JMGetter) pattern.get(0)).f.apply("hello"));
        //TODO unit test

        System.out.println(JM.match("hello", new JMGetter<>(String.class, String::length, new JVar("X"))));
    }

    @Override
    public String toString() {
        return "getter(" + pattern + ")";
    }

    @Override
    public Set<Map> match(Object data, Map current) {
        if (!(c.isAssignableFrom(data.getClass()))) return JM.emptyList;
        return JM.match(f.apply((T) data), pattern, current);
    }
}
