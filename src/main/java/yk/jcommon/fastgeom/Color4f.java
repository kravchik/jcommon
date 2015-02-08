package yk.jcommon.fastgeom;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 5/1/14
 * Time: 6:40 PM
 */
public class Color4f {
    public final static Color4f WHITE = new Color4f(1, 1, 1);
    public final static Color4f RED = new Color4f(1, 0, 0);
    public final static Color4f GREEN = new Color4f(0, 1, 0);
    public final static Color4f BLUE = new Color4f(0, 0, 1);
    public final static Color4f GREY = new Color4f(0.5f, 0.5f, 0.5f);
    public final static Color4f BLACK = new Color4f(0, 0, 0);

    public final float r, g, b, a;

    public Color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color4f(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public static Color4f c(float r, float g, float b) {
        return new Color4f(r, g, b);
    }
}
