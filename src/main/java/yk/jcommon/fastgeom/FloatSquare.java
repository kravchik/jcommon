package yk.jcommon.fastgeom;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 03.02.2016
 */
public class FloatSquare {
    //left bottom
    public float l;
    public float b;
    //right top
    public float r;
    public float t;

    public float w;
    public float h;

    public boolean isCrossed(FloatSquare box1) {
        return isCrossed(box1.l, box1.b, box1.r, box1.t);
    }

    public boolean isCrossed(float l, float b, float r, float t) {
        return l <= this.r && r >= this.l && b <= this.t && t >= this.b;
    }

    public FloatSquare init(Vec2f center, float r) {
        return init(center.x - r, center.y - r, center.x + r, center.y + r);
    }

    public FloatSquare init(float l, float b, float r, float t) {
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
}
