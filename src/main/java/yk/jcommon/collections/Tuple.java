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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (a != null) {
            if (!a.equals(tuple.a)) return false;
        } else {
            if (tuple.a != null) return false;
        }
        if (b != null) {
            if (!b.equals(tuple.b)) return false;
        } else {
            if (tuple.b != null) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        if (a != null) result = a.hashCode();
        else result = 0;
        if (b != null) result = 31 * result + b.hashCode();
        else result = 31 * result + 0;
        return result;
    }
}
