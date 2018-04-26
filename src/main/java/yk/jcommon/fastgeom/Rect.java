package yk.jcommon.fastgeom;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 16/10/15
 * Time: 10:12
 */
public class Rect {
    public int l, r, t, b;

    public Rect(int l, int b, int r, int t) {
        this.l = l;
        this.r = r;
        this.t = t;
        this.b = b;
    }

    public boolean isInside(Vec2i v2i) {
        return isInside(v2i.x, v2i.y);
    }

    public boolean isInside(int x, int y) {
        return x >= l && x < r && y >= b && y < t;
    }

    public int getWidth() {
        return r - l;
    }

    public int getHeight() {
        return b - t;
    }
}
