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
    public float w, x, y, z;

    public static final Vec4f ZERO = new Vec4f(0, 0, 0, 0);

    public Vec4f(final float w, final float x, final float y, final float z) {//TODO replace with x y z w ?
        this.w = w;
        this.x = x;this.y = y;
        this.z = z;
    }

    public static Vec4f v4(final float x, final float y, final float z, final float w) {
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

    public Vec2f getXy() {
        return new Vec2f(x, y);
    }
    public Vec2f getYz() {
        return new Vec2f(y, z);
    }

    public Vec2f getXz() {
        return new Vec2f(x, z);
    }

    public Vec3f getXyz() {
        return new Vec3f(x, y, z);
    }

    public void setXyz(Vec3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public float dot(final Vec4f b) {
        return x * b.x + y * b.y + z * b.z + w * b.z;
    }

    public Vec4f negative() {
        return v4(x, y, z, w);
    }

    //gglsl auto generated text
public Vec4f plus(Vec4f arg1) {return Vec4f.v4((float)this.x+(float)arg1.x, (float)this.y+(float)arg1.y, (float)this.z+(float)arg1.z, (float)this.w+(float)arg1.w);}
public Vec4f plus(float arg1) {return Vec4f.v4((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1, (float)this.w+(float)arg1);}
public Vec4f plus(Float arg1) {return Vec4f.v4((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1, (float)this.w+(float)arg1);}
public Vec4f plus(Number arg1) {return Vec4f.v4((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1, (float)this.w+(float)arg1);}
public Vec4f minus(Vec4f arg1) {return Vec4f.v4((float)this.x-(float)arg1.x, (float)this.y-(float)arg1.y, (float)this.z-(float)arg1.z, (float)this.w-(float)arg1.w);}
public Vec4f minus(float arg1) {return Vec4f.v4((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1, (float)this.w-(float)arg1);}
public Vec4f minus(Float arg1) {return Vec4f.v4((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1, (float)this.w-(float)arg1);}
public Vec4f minus(Number arg1) {return Vec4f.v4((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1, (float)this.w-(float)arg1);}
public Vec4f multiply(Vec4f arg1) {return Vec4f.v4((float)this.x*(float)arg1.x, (float)this.y*(float)arg1.y, (float)this.z*(float)arg1.z, (float)this.w*(float)arg1.w);}
public Vec4f multiply(float arg1) {return Vec4f.v4((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1, (float)this.w*(float)arg1);}
public Vec4f multiply(Float arg1) {return Vec4f.v4((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1, (float)this.w*(float)arg1);}
public Vec4f multiply(Number arg1) {return Vec4f.v4((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1, (float)this.w*(float)arg1);}
public Vec4f div(Vec4f arg1) {return Vec4f.v4((float)this.x/(float)arg1.x, (float)this.y/(float)arg1.y, (float)this.z/(float)arg1.z, (float)this.w/(float)arg1.w);}
public Vec4f div(float arg1) {return Vec4f.v4((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1, (float)this.w/(float)arg1);}
public Vec4f div(Float arg1) {return Vec4f.v4((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1, (float)this.w/(float)arg1);}
public Vec4f div(Number arg1) {return Vec4f.v4((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1, (float)this.w/(float)arg1);}
public Vec4f radians() {return Vec4f.v4((float)(this.x/180f*Math.PI), (float)(this.y/180f*Math.PI), (float)(this.z/180f*Math.PI), (float)(this.w/180f*Math.PI));}
public Vec4f degrees() {return Vec4f.v4((float)(this.x/Math.PI*180), (float)(this.y/Math.PI*180), (float)(this.z/Math.PI*180), (float)(this.w/Math.PI*180));}
public Vec4f sin() {return Vec4f.v4((float)Math.sin(this.x), (float)Math.sin(this.y), (float)Math.sin(this.z), (float)Math.sin(this.w));}
public Vec4f cos() {return Vec4f.v4((float)Math.cos(this.x), (float)Math.cos(this.y), (float)Math.cos(this.z), (float)Math.cos(this.w));}
public Vec4f tan() {return Vec4f.v4((float)Math.tan(this.x), (float)Math.tan(this.y), (float)Math.tan(this.z), (float)Math.tan(this.w));}
public Vec4f asin() {return Vec4f.v4((float)Math.asin(this.x), (float)Math.asin(this.y), (float)Math.asin(this.z), (float)Math.asin(this.w));}
public Vec4f acos() {return Vec4f.v4((float)Math.acos(this.x), (float)Math.acos(this.y), (float)Math.acos(this.z), (float)Math.acos(this.w));}
public Vec4f atan() {return Vec4f.v4((float)Math.atan(this.x), (float)Math.atan(this.y), (float)Math.atan(this.z), (float)Math.atan(this.w));}
public Vec4f atan(Vec4f x) {return Vec4f.v4((float)Math.atan2(this.x, x.x), (float)Math.atan2(this.y, x.y), (float)Math.atan2(this.z, x.z), (float)Math.atan2(this.w, x.w));}
public Vec4f pow(Vec4f power) {return Vec4f.v4((float)Math.pow(this.x, power.x), (float)Math.pow(this.y, power.y), (float)Math.pow(this.z, power.z), (float)Math.pow(this.w, power.w));}
public Vec4f exp() {return Vec4f.v4((float)Math.exp(this.x), (float)Math.exp(this.y), (float)Math.exp(this.z), (float)Math.exp(this.w));}
public Vec4f log() {return Vec4f.v4((float)Math.log(this.x), (float)Math.log(this.y), (float)Math.log(this.z), (float)Math.log(this.w));}
public Vec4f sqrt() {return Vec4f.v4((float)Math.sqrt(this.x), (float)Math.sqrt(this.y), (float)Math.sqrt(this.z), (float)Math.sqrt(this.w));}
public Vec4f abs() {return Vec4f.v4(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));}
public Vec4f sign() {return Vec4f.v4(Math.signum(this.x), Math.signum(this.y), Math.signum(this.z), Math.signum(this.w));}
public Vec4f floor() {return Vec4f.v4((float)Math.floor(this.x), (float)Math.floor(this.y), (float)Math.floor(this.z), (float)Math.floor(this.w));}
public Vec4f ceil() {return Vec4f.v4((float)Math.ceil(this.x), (float)Math.ceil(this.y), (float)Math.ceil(this.z), (float)Math.ceil(this.w));}
public Vec4f fract() {return Vec4f.v4(this.x - (float)Math.floor(this.x), this.y - (float)Math.floor(this.y), this.z - (float)Math.floor(this.z), this.w - (float)Math.floor(this.w));}
public Vec4f mod(Vec4f by) {return Vec4f.v4((float)(this.x-by.x*Math.floor(this.x/by.x)), (float)(this.y-by.y*Math.floor(this.y/by.y)), (float)(this.z-by.z*Math.floor(this.z/by.z)), (float)(this.w-by.w*Math.floor(this.w/by.w)));}
public Vec4f min(Vec4f arg1) {return Vec4f.v4(Math.min(this.x, arg1.x), Math.min(this.y, arg1.y), Math.min(this.z, arg1.z), Math.min(this.w, arg1.w));}
public Vec4f min(float arg1) {return Vec4f.v4(Math.min(this.x, arg1), Math.min(this.y, arg1), Math.min(this.z, arg1), Math.min(this.w, arg1));}
public Vec4f max(Vec4f arg1) {return Vec4f.v4(Math.max(this.x, arg1.x), Math.max(this.y, arg1.y), Math.max(this.z, arg1.z), Math.max(this.w, arg1.w));}
public Vec4f max(float arg1) {return Vec4f.v4(Math.max(this.x, arg1), Math.max(this.y, arg1), Math.max(this.z, arg1), Math.max(this.w, arg1));}
public Vec4f clamp(Vec4f max, Vec4f value) {return Vec4f.v4(Math.max(max.x, Math.min(value.x, this.x)), Math.max(max.y, Math.min(value.y, this.y)), Math.max(max.z, Math.min(value.z, this.z)), Math.max(max.w, Math.min(value.w, this.w)));}
public Vec4f clamp(float max, float value) {return Vec4f.v4(Math.max(max, Math.min(value, this.x)), Math.max(max, Math.min(value, this.y)), Math.max(max, Math.min(value, this.z)), Math.max(max, Math.min(value, this.w)));}
public Vec4f mix(Vec4f to, Vec4f progress) {return Vec4f.v4(this.x * (1 - progress.x) + to.x * progress.x, this.y * (1 - progress.y) + to.y * progress.y, this.z * (1 - progress.z) + to.z * progress.z, this.w * (1 - progress.w) + to.w * progress.w);}
public Vec4f mix(Vec4f to, float progress) {return Vec4f.v4(this.x * (1 - progress) + to.x * progress, this.y * (1 - progress) + to.y * progress, this.z * (1 - progress) + to.z * progress, this.w * (1 - progress) + to.w * progress);}
public Vec4f step(Vec4f value) {return Vec4f.v4(value.x < this.x ? 0 : 1, value.y < this.y ? 0 : 1, value.z < this.z ? 0 : 1, value.w < this.w ? 0 : 1);}
public Vec4f smoothstep(Vec4f to, Vec4f progress) {return Vec4f.v4(progress.x < this.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < this.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < this.z ? 0 : progress.z > to.z ? 1 : progress.z*progress.z*(3 - 2*progress.z), progress.w < this.w ? 0 : progress.w > to.w ? 1 : progress.w*progress.w*(3 - 2*progress.w));}

//gglsl auto generated text
}
