/**
 * File Vec3.java
 * @author Yuri Kravchik
 * Created 16.10.2008
 */
package yk.jcommon.fastgeom;

import java.io.Serializable;

/**
 * Vec3
 *
 * @author Yuri Kravchik Created 16.10.2008
 */
public class Vec4f implements Serializable {
    public final float w, x, y, z;

    public static final Vec4f ZERO = new Vec4f(0, 0, 0, 0);

    public Vec4f(final float w, final float x, final float y, final float z) {
        this.w = w;
        this.x = x;this.y = y;
        this.z = z;
    }

    public static Vec4f v4(final float w, final float x, final float y, final float z) {
        return new Vec4f(w, x, y, z);
    }

    public static Vec4f v34(Vec3f v3, final float w) {
        return new Vec4f(v3, w);
    }

    public Vec4f(Vec3f v, float w) {
        this(w, v.x, v.y, v.z);
    }

    public Vec4f(Vec4f from) {
        this.w = from.w;
        this.x = from.x;
        this.y = from.y;
        this.z = from.z;
    }

    public Vec4f add(final Vec4f b) {
        return new Vec4f(w + b.w, x + b.x, y + b.y, z + b.z);
    }

    public Vec4f add(float w, float x, float y, float z) {
        return new Vec4f(this.w + w, this.x + x, this.y + y, this.z + z);
    }

//    public Vec4f crossProduct(final Vec4f b) {
//        return new Vec4f(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
//    }

    public Vec4f div(final float value) {
        return new Vec4f(w / value, x / value, y / value, z / value);
    }

    public Vec4f mul(final float b) {
        return new Vec4f(w * b, x * b, y * b, z * b);
    }

    public Vec4f mul(final Vec4f b) {
        return new Vec4f(w * b.w, x * b.x, y * b.y, z * b.z);
    }

    public Vec4f mul(float w, float x, float y, float z) {
        return new Vec4f(this.w * w, this.x * x, this.y * y, this.z * z);
    }

    public Vec4f normalized() {
        return normalized(1);
    }

    public Vec4f normalized(float r) {
        final float m = r / length();
        return new Vec4f(w * m, x * m, y * m, z * m);
    }

//    public float scalarProduct(final Vec4f b) {
//        return x * b.x + y * b.y + z * b.z;
//    }

    public Vec4f sub(final Vec4f b) {
        return new Vec4f(w - b.w, x - b.x, y - b.y, z - b.z);
    }

    public Vec4f sub(float w, float x, float y, float z) {
        return new Vec4f(this.w - w, this.x - x, this.y - y, this.z - z);
    }

    @Override
    public String toString() {
        return "w: " + w + " x: " + x + " y: " + y + " z: " + z;
    }

    public float dist(Vec4f b) {
        return sub(b).length();
    }

    public float length() {
        return (float) Math.sqrt(w*w+x*x+y*y+z*z);
    }

    public static Vec4f mean(Vec4f... vv) {
        return sum(vv).div(vv.length);
    }

    public static Vec4f sum(Vec4f... vv) {
        float w = 0;
        float x = 0;
        float y = 0;
        float z = 0;
        for (int i = 0; i < vv.length; i++) {
            w += vv[i].w;
            x += vv[i].x;
            y += vv[i].y;
            z += vv[i].z;
        }
        return new Vec4f(w, x, y, z);
    }

    /**
     * this 0 -> 1 to
     * @param to
     * @param blend
     * @return
     */
    public Vec4f lerp(Vec4f to, float blend) {
        return mix(this, to, blend);
    }

    //0 - a, 1 - b
    public static Vec4f mix(Vec4f a, Vec4f b, float ab) {
        return b.sub(a).mul(ab).add(a);
    }

    public Vec4f plus(Vec4f other) {//groovy operator overloading
        return add(other);
    }

    public Vec4f multiply(Vec4f other) {//groovy operator overloading
        return mul(other);
    }

    public Vec4f multiply(float f) {//groovy operator overloading
        return mul(f);
    }

    public Vec2f getXy() {
        return new Vec2f(x, y);
    }
    public Vec2f getYZ() {
        return new Vec2f(y, z);
    }

    public float getZ() {
        return z;
    }

    public float getX() {
        return x;
    }

    public Vec2f getXY() {
        return new Vec2f(x, y);
    }

    public Vec2f getXZ() {
        return new Vec2f(x, z);
    }

    public float getY() {
        return y;
    }

    public Vec3f getXyz() {
        return new Vec3f(x, y, z);
    }

}
