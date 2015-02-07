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
public class Vec3f implements Serializable {
    public final float x, y, z;

    public static final Vec3f ZERO = new Vec3f(0, 0, 0);

    public static final Vec3f AXISX = new Vec3f(1, 0, 0);

    public static final Vec3f AXISY = new Vec3f(0, 1, 0);

    public static final Vec3f AXISZ = new Vec3f(0, 0, 1);

    public Vec3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3f v(final float x, final float y, final float z) {
        return new Vec3f(x, y, z);
    }

    public static Vec3f v3(final float x, final float y, final float z) {
        return new Vec3f(x, y, z);
    }

    public Vec3f(Vec3f from) {
        this.x = from.x;
        this.y = from.y;
        this.z = from.z;
    }

    public Vec3f add(final Vec3f b) {
        return new Vec3f(x + b.x, y + b.y, z + b.z);
    }

    public Vec3f add(float x, float y, float z) {
        return new Vec3f(this.x + x, this.y + y, this.z + z);
    }

    public Vec3f crossProduct(final Vec3f b) {
        return new Vec3f(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
    }

    public Vec3f div(final float value) {
        return new Vec3f(x / value, y / value, z / value);
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

    public Vec2f getYZ() {
        return new Vec2f(y, z);
    }

    public float getZ() {
        return z;
    }

    public Vec3f mul(final float b) {
        return new Vec3f(x * b, y * b, z * b);
    }

    public Vec3f mul(final Vec3f b) {
        return new Vec3f(x * b.x, y * b.y, z * b.z);
    }

    public Vec3f mul(float x, float y, float z) {
        return new Vec3f(this.x * x, this.y * y, this.z * z);
    }

    public Vec3f normalized() {
        final float radius = 1 / (float) Math.sqrt(x * x + y * y + z * z);
        return new Vec3f(x * radius, y * radius, z * radius);
    }

    public Vec3f normalized(float r) {
        final float radius = r / (float) Math.sqrt(x * x + y * y + z * z);
        return new Vec3f(x * radius, y * radius, z * radius);
    }

    public float scalarProduct(final Vec3f b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public Vec3f sub(final Vec3f b) {
        return new Vec3f(x - b.x, y - b.y, z - b.z);
    }

    public Vec3f sub(float x, float y, float z) {
        return new Vec3f(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y + " z: " + z;
    }

    public float dist(Vec3f b) {
        return sub(b).length();
    }

    public float length() {
        return (float) Math.sqrt(x*x+y*y+z*z);
    }

    public static Vec3f mean(Vec3f... vv) {
        return sum(vv).div(vv.length);
    }

    public static Vec3f sum(Vec3f... vv) {
        float x = 0;
        float y = 0;
        float z = 0;
        for (int i = 0; i < vv.length; i++) {
            x += vv[i].x;
            y += vv[i].y;
            z += vv[i].z;
        }
        return new Vec3f(x, y, z);
    }

    public Vec3f mirror(Vec3f around) {
        return sub(around).mul(-1).add(around);
    }

    /**
     * this 0 -> 1 to
     * @param to
     * @param blend
     * @return
     */
    public Vec3f lerp(Vec3f to, float blend) {
        return to.sub(this).mul(blend).add(this);
    }

    //0 - a, 1 - b
    public static Vec3f mix(Vec3f a, Vec3f b, float ab) {
        return b.sub(a).mul(ab).add(a);
    }

    public Vec3f plus(Vec3f other) {//groovy operator overloading
        return add(other);
    }

    public Vec3f multiply(Vec3f other) {//groovy operator overloading
        return mul(other);
    }

    public Vec3f multiply(float f) {//groovy operator overloading
        return mul(f);
    }

    public Vec2f getXy() {
        return new Vec2f(x, y);
    }
}
