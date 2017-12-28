/**
 * File Vec2f.java
 * @author Yuri Kravchik
 * Created 04.01.2009
 */
package yk.jcommon.fastgeom;

import yk.jcommon.utils.BadException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import static yk.jcommon.utils.Util.sqr;

/**
 * Vec2f
 *
 * @author Yuri Kravchik Created 04.01.2009
 */
@SuppressWarnings("unused")
public final class Vec2f implements Serializable {
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

    public Vec2f add(float v) {
        return add(v, v);
    }

    public void addLocal(final Vec2f b) {
        x += b.x; y += b.y;
    }

    public Vec2f div(float vx, float vy) {
        return new Vec2f(x / vx, y / vy);
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

    public Vec2f mul(final Vec2f b) {
        return new Vec2f(x * b.x, y * b.y);
    }

    public Vec2f mul(float vx, float vy) {
        return new Vec2f(x * vx, y * vy);
    }

    public Vec2f mul(final float b) {
        return mul(b, b);
    }

    public float mulScalar(final Vec2f b) {
        return x * b.x + y * b.y;
    }

    public float dot(final Vec2f b) {
        return x * b.x + y * b.y;
    }

    public Vec2f sub(final Vec2f b) {
        return new Vec2f(x - b.x, y - b.y);
    }

    public Vec2f sub(float vx, float vy) {
        return new Vec2f(x - vx, y - vy);
    }

    public Vec2f sub(float v) {
        return sub(v, v);
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
        if (Float.isNaN(m)) BadException.die("NaN for " + this);
        if (Float.isInfinite(m)) BadException.die("Infinite for " + this);
        return new Vec2f(x * m, y * m);
    }

    public static Vec2f i(DataInputStream dis) throws IOException {
        return new Vec2f(dis.readFloat(), dis.readFloat());
    }

    public void o(DataOutputStream dos) throws IOException {
        dos.writeFloat(x);
        dos.writeFloat(y);
    }

    public Vec2f complexDiv(Vec2f by) {
        float len = 1 / by.length();
        return new Vec2f(x * by.x + y * by.y, -x * by.y + y * by.x).mul(len);
    }

    public Vec2f complexDiv1(Vec2f by) {
        return new Vec2f(x * by.x + y * by.y, -x * by.y + y * by.x);
    }

    public Vec2f complexMul(Vec2f by) {
        return new Vec2f(x * by.x - y * by.y, x * by.y + y * by.x);
    }

    public Vec2f rot90() {
        return new Vec2f(-y, x);
    }

    public Vec2f rot_90() {
        return new Vec2f(y, -x);
    }

    public float cross  (Vec2f other) {
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

    public Vec3f withZ(float z) {
        return new Vec3f(x, y, z);
    }

    public Vec3f withY(float y) {
        return new Vec3f(this.x, y, this.y);
    }

    public Vec3f withX(float x) {
        return new Vec3f(x, this.x, this.y);
    }

    public Vec3f asXZ(float y) {
        return new Vec3f(x, y, this.y);
    }

    public Vec2f negative() {
        return new Vec2f(-x, -y);
    }

    public Vec2f copy() {
        return new Vec2f(x, y);
    }

    public void copyFrom(Vec2f other) {
        this.x = other.x;
        this.y = other.y;
    }

    public float dist(Vec2f b) {
        return sub(b).length();
    }

//gglsl auto generated text
public Vec2f plus(Vec2f arg1) {return Vec2f.v2((float)this.x+(float)arg1.x, (float)this.y+(float)arg1.y);}
public Vec2f plus(float arg1) {return Vec2f.v2((float)this.x+(float)arg1, (float)this.y+(float)arg1);}
public Vec2f plus(Float arg1) {return Vec2f.v2((float)this.x+(float)arg1, (float)this.y+(float)arg1);}
public Vec2f plus(Number arg1) {return Vec2f.v2((float)this.x+(float)arg1, (float)this.y+(float)arg1);}
public Vec2f minus(Vec2f arg1) {return Vec2f.v2((float)this.x-(float)arg1.x, (float)this.y-(float)arg1.y);}
public Vec2f minus(float arg1) {return Vec2f.v2((float)this.x-(float)arg1, (float)this.y-(float)arg1);}
public Vec2f minus(Float arg1) {return Vec2f.v2((float)this.x-(float)arg1, (float)this.y-(float)arg1);}
public Vec2f minus(Number arg1) {return Vec2f.v2((float)this.x-(float)arg1, (float)this.y-(float)arg1);}
public Vec2f multiply(Vec2f arg1) {return Vec2f.v2((float)this.x*(float)arg1.x, (float)this.y*(float)arg1.y);}
public Vec2f multiply(float arg1) {return Vec2f.v2((float)this.x*(float)arg1, (float)this.y*(float)arg1);}
public Vec2f multiply(Float arg1) {return Vec2f.v2((float)this.x*(float)arg1, (float)this.y*(float)arg1);}
public Vec2f multiply(Number arg1) {return Vec2f.v2((float)this.x*(float)arg1, (float)this.y*(float)arg1);}
public Vec2f div(Vec2f arg1) {return Vec2f.v2((float)this.x/(float)arg1.x, (float)this.y/(float)arg1.y);}
public Vec2f div(float arg1) {return Vec2f.v2((float)this.x/(float)arg1, (float)this.y/(float)arg1);}
public Vec2f div(Float arg1) {return Vec2f.v2((float)this.x/(float)arg1, (float)this.y/(float)arg1);}
public Vec2f div(Number arg1) {return Vec2f.v2((float)this.x/(float)arg1, (float)this.y/(float)arg1);}
public Vec2f radians() {return Vec2f.v2((float)(this.x/180f*Math.PI), (float)(this.y/180f*Math.PI));}
public Vec2f degrees() {return Vec2f.v2((float)(this.x/Math.PI*180), (float)(this.y/Math.PI*180));}
public Vec2f sin() {return Vec2f.v2((float)Math.sin(this.x), (float)Math.sin(this.y));}
public Vec2f cos() {return Vec2f.v2((float)Math.cos(this.x), (float)Math.cos(this.y));}
public Vec2f tan() {return Vec2f.v2((float)Math.tan(this.x), (float)Math.tan(this.y));}
public Vec2f asin() {return Vec2f.v2((float)Math.asin(this.x), (float)Math.asin(this.y));}
public Vec2f acos() {return Vec2f.v2((float)Math.acos(this.x), (float)Math.acos(this.y));}
public Vec2f atan() {return Vec2f.v2((float)Math.atan(this.x), (float)Math.atan(this.y));}
public Vec2f atan(Vec2f x) {return Vec2f.v2((float)Math.atan2(this.x, x.x), (float)Math.atan2(this.y, x.y));}
public Vec2f pow(Vec2f power) {return Vec2f.v2((float)Math.pow(this.x, power.x), (float)Math.pow(this.y, power.y));}
public Vec2f exp() {return Vec2f.v2((float)Math.exp(this.x), (float)Math.exp(this.y));}
public Vec2f log() {return Vec2f.v2((float)Math.log(this.x), (float)Math.log(this.y));}
public Vec2f sqrt() {return Vec2f.v2((float)Math.sqrt(this.x), (float)Math.sqrt(this.y));}
public Vec2f abs() {return Vec2f.v2(Math.abs(this.x), Math.abs(this.y));}
public Vec2f sign() {return Vec2f.v2(Math.signum(this.x), Math.signum(this.y));}
public Vec2f floor() {return Vec2f.v2((float)Math.floor(this.x), (float)Math.floor(this.y));}
public Vec2f ceil() {return Vec2f.v2((float)Math.ceil(this.x), (float)Math.ceil(this.y));}
public Vec2f fract() {return Vec2f.v2(this.x - (float)Math.floor(this.x), this.y - (float)Math.floor(this.y));}
public Vec2f mod(Vec2f by) {return Vec2f.v2((float)(this.x-by.x*Math.floor(this.x/by.x)), (float)(this.y-by.y*Math.floor(this.y/by.y)));}
public Vec2f min(Vec2f arg1) {return Vec2f.v2(Math.min(this.x, arg1.x), Math.min(this.y, arg1.y));}
public Vec2f min(float arg1) {return Vec2f.v2(Math.min(this.x, arg1), Math.min(this.y, arg1));}
public Vec2f max(Vec2f arg1) {return Vec2f.v2(Math.max(this.x, arg1.x), Math.max(this.y, arg1.y));}
public Vec2f max(float arg1) {return Vec2f.v2(Math.max(this.x, arg1), Math.max(this.y, arg1));}
public Vec2f clamp(Vec2f min, Vec2f max) {return Vec2f.v2(Math.max(min.x, Math.min(this.x, max.x)), Math.max(min.y, Math.min(this.y, max.y)));}
public Vec2f clamp(float min, float max) {return Vec2f.v2(Math.max(min, Math.min(this.x, max)), Math.max(min, Math.min(this.y, max)));}
public Vec2f mix(Vec2f to, Vec2f progress) {return Vec2f.v2(this.x * (1 - progress.x) + to.x * progress.x, this.y * (1 - progress.y) + to.y * progress.y);}
public Vec2f mix(Vec2f to, float progress) {return Vec2f.v2(this.x * (1 - progress) + to.x * progress, this.y * (1 - progress) + to.y * progress);}
public Vec2f step(Vec2f value) {return Vec2f.v2(value.x < this.x ? 0 : 1, value.y < this.y ? 0 : 1);}
public Vec2f smoothstep(Vec2f to, Vec2f progress) {return Vec2f.v2(progress.x < this.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < this.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y));}

//gglsl auto generated text
}
