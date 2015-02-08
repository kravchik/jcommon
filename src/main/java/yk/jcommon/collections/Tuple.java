package yk.jcommon.collections;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 2/18/14
 * Time: 10:49 AM
 */
public class Tuple<A, B> {
    public A a;
    public B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}
