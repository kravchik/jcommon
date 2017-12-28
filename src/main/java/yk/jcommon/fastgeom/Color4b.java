/**
 * File Color4b.java
 * @author Yuri Kravchik
 * Created 27.01.2009
 */
package yk.jcommon.fastgeom;

/**
 * Color4b
 *
 * @author Yuri Kravchik Created 27.01.2009
 */
public class Color4b {
    public final int r, g, b, a;

    public Color4b(final int r, final int g, final int b, final int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public byte getA() {
        return (byte) a;
    }

    public byte getB() {
        return (byte) b;
    }

    public byte getG() {
        return (byte) g;
    }

    public byte getR() {
        return (byte) r;
    }
}
