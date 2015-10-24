/**
 * File Vec2f.java
 * @author Yuri Kravchik
 * Created 04.01.2009
 */
package yk.jcommon.fastgeom;

import java.io.*;

import static yk.jcommon.utils.Util.sqr;

/**
 * Vec2f
 *
 * @author Yuri Kravchik Created 04.01.2009
 */
public class Vec2f implements Serializable {
    public static final Vec2f ZERO = new Vec2f();
    public float x, y;//TODO final

    public Vec2f() {}

    public static Vec2f v2(final float x, final float y) {
        return new Vec2f(x, y);
    }

    public Vec2f(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(final Vec2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vec2f add(final Vec2f b) {
        return new Vec2f(x + b.x, y + b.y);
    }

    public Vec2f add(float x, float y) {
        return new Vec2f(this.x + x, this.y + y);
    }

    public void addLocal(final Vec2f b) {
        x += b.x; y += b.y;
    }

    public Vec2f div(final float v) {
        return new Vec2f(x / v, y / v);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float lengthSqr() {
        return x * x + y * y;
    }

    public float maxAbs() {
        return Math.max(Math.abs(x), Math.abs(y));
    }

    public float minAbs() {
        return Math.min(Math.abs(x), Math.abs(y));
    }

    public Vec2f mul(final float b) {
        return new Vec2f(x * b, y * b);
    }

    public Vec2f mul(final Vec2f b) {
        return new Vec2f(x * b.x, y * b.y);
    }

    public float mulScalar(final Vec2f b) {
        return x * b.x + y * b.y;
    }

    public Vec2f sub(final Vec2f b) {
        return new Vec2f(x - b.x, y - b.y);
    }

    @Override
    public String toString() {
        return "x: " + x + " y: " + y;
    }

    public Vec2f normalized() {
        return normalized(1);
    }

    public Vec2f normalized(float len) {
        float m = len / length();
        return new Vec2f(x * m, y * m);
    }

    public static Vec2f i(DataInputStream dis) throws IOException {
        return new Vec2f(dis.readFloat(), dis.readFloat());
    }

    public void o(DataOutputStream dos) throws IOException {
        dos.writeFloat(x);
        dos.writeFloat(y);
    }

    public Vec2f quaternionDiv(Vec2f by) {
        float len = 1 / by.length();
        return new Vec2f(x * by.x + y * by.y, -x * by.y + y * by.x).mul(len);
    }

    public Vec2f quaternionDiv1(Vec2f by) {
        return new Vec2f(x * by.x + y * by.y, -x * by.y + y * by.x);
    }

    public Vec2f quaternionMul(Vec2f by) {
        return new Vec2f(x * by.x - y * by.y, x * by.y + y * by.x);
    }

    public Vec2f rot90() {
        return new Vec2f(-y, x);
    }

    public Vec2f rot_90() {
        return new Vec2f(y, -x);
    }

    public Vec2f min(Vec2f other) {
        return new Vec2f(Math.min(x, other.x), Math.min(y, other.y));
    }
    public Vec2f max(Vec2f other) {
        return new Vec2f(Math.max(x, other.x), Math.max(y, other.y));
    }

    public float cross(Vec2f other) {
        return x * other.y - y * other.x;
    }

    public float distanceSquared(Vec2f other) {
        return sqr(other.x - x) + sqr(other.y - y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec2f vec2f = (Vec2f) o;

        if (Float.compare(vec2f.x, x) != 0) return false;
        if (Float.compare(vec2f.y, y) != 0) return false;

        return true;
    }

    public Vec2f cDivFast(Vec2f unit) {
        return new Vec2f(x*unit.x+y*unit.y, -x*unit.y+y*unit.x);
    }

    public Vec2f cMul(Vec2f other) {
        return new Vec2f(x*other.x-y*other.y, x*other.y+y*other.x);
    }

    public float getAngle() {
        return (float) (Math.atan2(y, x));
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    public float dis(Vec2f pos) {
        return (float) Math.sqrt(distanceSquared(pos));
    }

    public static Vec2f fromAngle(float angle) {
        return new Vec2f((float)Math.cos(angle), (float) Math.sin(angle));
    }

    public boolean isBehind(Vec2f b) {
        return this.mulScalar(b) < 0;
    }

    public Vec3f asXZ(float y) {
        return new Vec3f(x, y, this.y);
    }

    public Vec2f multiply(float f) {//groovy operator overloading
        return mul(f);
    }

    public Vec2f multiply(double f) {//groovy operator overloading
        return mul((float)f);
    }

    public Vec2f negative() {
        return new Vec2f(-x, -y);
    }

}
