package yk.jcommon.fastgeom;

/**
 * Kravchik Yuri
 * Date: 16.07.2012
 * Time: 9:26 PM
 */
public class Complex {
    public final float a;
    public final float b;

    public Complex(float a, float b) {
        this.a = a;
        this.b = b;
    }

    public Complex mul(Complex other) {
        return new Complex(a*other.a-b*other.b, a*other.b+b*other.a);
    }

    public static Complex fromAngle(float angle) {
        return new Complex((float)Math.cos(angle), (float) Math.sin(angle));
    }

    public Complex mul(float oa, float ob) {
        return new Complex(a*oa-b*ob, a*ob+b*oa);
    }

    public Vec2f rot(Vec2f v) {
        Complex result = mul(new Complex(v.x, v.y));
        return new Vec2f(result.a, result.b);
    }

    public Complex normalized() {
        float l = (float) (1f/Math.sqrt(a*a+b*b));
        return new Complex(a*l, b*l);
    }

    public float len() {
        return (float)Math.sqrt(a*a + b*b);
    }

    public Complex divFast(Complex other) {
        return new Complex(a*other.a+b*other.b, -a*other.b+b*other.a);
    }

    public Complex conj() {
        return new Complex(a, -b);
    }


    public Complex add(Complex other) { return new Complex(a+other.a, b+other.b); }
    public Complex sub(Complex other) { return new Complex(a-other.a, b-other.b); }
    public Complex add(float oa, float ob) { return new Complex(a+oa, b+ob); }
    public Complex sub(float oa, float ob) { return new Complex(a-oa, b-ob); }

    @Override
    public String toString() {
        return "(" + a + " i" + b + ")";
    }
}
