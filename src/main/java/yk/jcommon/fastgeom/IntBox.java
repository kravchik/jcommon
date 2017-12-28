package yk.jcommon.fastgeom;

import static yk.jcommon.utils.MyMath.max;
import static yk.jcommon.utils.MyMath.min;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:11:01
 */
public class IntBox {//TODO rename IntSquare
    //left bottom
    public int l;
    public int b;
    //right top
    public int r;
    public int t;

    public int w;
    public int h;

    //true if given box intersects with this box
    public boolean isCrossed(IntBox box1) {
        return isCrossedi(box1.l, box1.b, box1.r, box1.t);
    }

    public boolean isCrossedi(int l, int b, int r, int t) {
        return (l <= this.r && r >= this.l)
                && (b <= this.t && t >= this.b);
    }

    public IntBox init(Vec2f center, float r) {
        return init((int)(center.x - r), (int)(center.y - r), (int)(center.x + r), (int)(center.y + r));
    }

    public IntBox init(Vec2i center, int r) {
        return init(center.x - r, center.y - r, center.x + r, center.y + r);
    }

    public IntBox init(Vec2f va, Vec2f vb) {
        Vec2f center = vb.sub(va);
        float size = Math.max(Math.abs(center.x), Math.abs(center.y)) / 2;
        center = center.div(2).add(va);
        init((int)(center.x - size), (int)(center.y - size), (int)(center.x + size), (int)(center.y + size));
        return this;
    }

    public IntBox intersection(IntBox other) {
        return new IntBox().init(max(l, other.l), max(b, other.b), min(r, other.r), min(t, other.t));
    }

    public IntBox init(Vec2f va, Vec2f vb, Vec2f vc) {
        return init((int)Math.min(va.x, Math.min(vb.x, vc.x))
                , (int)Math.min(va.y, Math.min(vb.y, vc.y))
                , (int)Math.max(va.x, Math.max(vb.x, vc.x))
                , (int)Math.max(va.y, Math.max(vb.y, vc.y)));
    }

    public IntBox init(int l, int b, int r, int t) {
        if (l > r || b > t) {
            throw new Error("Cant create IntBox with " + l + " " + b + " " + r + " " + t);
        }
        this.l = l;
        this.b = b;
        this.r = r;
        this.t = t;
        w = r - l;
        h = t - b;
        return this;
    }

    public IntBox copy() {
        IntBox result = new IntBox();
        result.l = l;
        result.b = b;
        result.r = r;
        result.t = t;
        result.w = w;
        result.h = h;
        return result;
    }
}
