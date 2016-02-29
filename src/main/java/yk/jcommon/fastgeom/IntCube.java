package yk.jcommon.fastgeom;

import static yk.jcommon.utils.MyMath.ceil;
import static yk.jcommon.utils.MyMath.floor;

/**
 * Created by IntelliJ IDEA.
 * User: Yuri Kravchik
 * Date: 22.01.2010
 * Time: 14:11:01
 */
public class IntCube {//TODO remove this, or IntBox
    //left bottom
    public int l;
    public int b;
    //right top
    public int r;
    public int t;
    //near far
    public int n;
    public int f;

    public int size;

    //true if given box intersects with this box
    public boolean isCrossed(IntCube box1) {
        return isCrossedi(box1.l, box1.b, box1.n, box1.r, box1.t, box1.f);
    }

    public boolean isInside(IntCube other) {
        return l >= other.l && r <= other.r && b >= other.b && t <= other.t && n >= other.n && f <= other.f;
    }

    public boolean isCrossedi(int l, int b, int n, int r, int t, int f) {
        return (l <= this.r && r >= this.l)
                && (b <= this.t && t >= this.b)
                && (n <= this.f && f >= this.n);
    }

    public IntCube init(Vec3f center, float r) {
        return init((int)floor(center.x - r), (int)floor(center.y - r), (int)floor(center.z - r), (int)ceil(center.x + r), (int)ceil(center.y + r), (int)ceil(center.z + r));
    }

    public IntCube init(Vec3f va, Vec3f vb) {
        //Vec3f center = vb.sub(va);
        //float size = Math.max(Math.abs(center.x), Math.abs(center.y)) / 2;
        //center = center.div(2).add(va);
        //init((int)(center.x - size), (int)(center.y - size), (int)(center.x + size), (int)(center.y + size));
        init(
                (int)floor(va.x),
                (int)floor(va.y),
                (int)floor(va.z),
                (int)ceil(vb.x),
                (int)ceil(vb.y),
                (int)ceil(vb.z)
                );
        return this;
    }

    public IntCube init(int l, int b, int n, int r, int t, int f) {
        if (l > r || b > t || n > f) {
            throw new Error("Cant create IntBox with " + l + " " + b + " " + r + " " + t + " " + n + " " + f);
        }
        //this.size = r - l;
        //if (t - b != size || f - n != size) {
        this.size = r - l+1;
        if (t - b != size-1 || f - n != size-1) {
            throw new Error("Cant create IntBox with " + l + " " + b + " " + r + " " + t + " " + n + " " + f);
        }
        this.l = l;
        this.b = b;
        this.r = r;
        this.t = t;
        this.n = n;
        this.f = f;
        return this;
    }

    public IntCube copy() {
        IntCube result = new IntCube();
        result.l = l;
        result.b = b;
        result.r = r;
        result.t = t;
        result.n = n;
        result.f = f;
        result.size = size;
        return result;
    }

    @Override
    public String toString() {
        return "IntCube{" +
                "l=" + l +
                ", b=" + b +
                ", n=" + n +
                ", r=" + r +
                ", t=" + t +
                ", f=" + f +
                ", size=" + size +
                '}';
    }
}
