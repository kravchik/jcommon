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
    public float x, y, z;

    public static final Vec3f ZERO = new Vec3f(0, 0, 0);

    public static final Vec3f AXISX = new Vec3f(1, 0, 0);

    public static final Vec3f AXISY = new Vec3f(0, 1, 0);

    public static final Vec3f AXISZ = new Vec3f(0, 0, 1);

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3f v3(final float x, final float y, final float z) {
        return new Vec3f(x, y, z);
    }

    public static Vec3f v23(Vec2f v, float z) {
        return new Vec3f(v, z);
    }

    public Vec3f(Vec2f v, float z) {
        this(v.x, v.y, z);
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

    public Vec3f add(float v) {
        return add(v, v, v);
    }

    public Vec3f crossProduct(final Vec3f b) {
        return new Vec3f(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
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
        return normalized(1);
    }

    public Vec3f normalized(float r) {
        final float m = r / length();
        return new Vec3f(x * m, y * m, z * m);
    }

    public float scalarProduct(final Vec3f b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public float dot(final Vec3f b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public Vec3f sub(final Vec3f b) {
        return new Vec3f(x - b.x, y - b.y, z - b.z);
    }

    public Vec3f sub(float x, float y, float z) {
        return new Vec3f(this.x - x, this.y - y, this.z - z);
    }

    public Vec3f sub(float v) {
        return sub(v, v, v);
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
        return mix(this, to, blend);
    }

    //0 - a, 1 - b
    public static Vec3f mix(Vec3f a, Vec3f b, float ab) {
        return b.sub(a).mul(ab).add(a);
    }

    public Vec2f getXy() {
        return new Vec2f(x, y);
    }

    public void setXy(Vec2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vec2f getXz() {
        return new Vec2f(x, z);
    }

    public Vec2f getYz() {
        return new Vec2f(y, z);
    }

    public Vec3f negative() {
        return new Vec3f(-x, -y, -z);
    }

    public Vec4f toVec4f(float w) {
        return new Vec4f(x, y, z, w);
    }



//gglsl auto generated text
public Vec3f plus(Vec3f arg1) {return Vec3f.v3((float)this.x+(float)arg1.x, (float)this.y+(float)arg1.y, (float)this.z+(float)arg1.z);}
public Vec3f plus(float arg1) {return Vec3f.v3((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1);}
public Vec3f plus(Float arg1) {return Vec3f.v3((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1);}
public Vec3f plus(Number arg1) {return Vec3f.v3((float)this.x+(float)arg1, (float)this.y+(float)arg1, (float)this.z+(float)arg1);}
public Vec3f minus(Vec3f arg1) {return Vec3f.v3((float)this.x-(float)arg1.x, (float)this.y-(float)arg1.y, (float)this.z-(float)arg1.z);}
public Vec3f minus(float arg1) {return Vec3f.v3((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1);}
public Vec3f minus(Float arg1) {return Vec3f.v3((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1);}
public Vec3f minus(Number arg1) {return Vec3f.v3((float)this.x-(float)arg1, (float)this.y-(float)arg1, (float)this.z-(float)arg1);}
public Vec3f multiply(Vec3f arg1) {return Vec3f.v3((float)this.x*(float)arg1.x, (float)this.y*(float)arg1.y, (float)this.z*(float)arg1.z);}
public Vec3f multiply(float arg1) {return Vec3f.v3((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1);}
public Vec3f multiply(Float arg1) {return Vec3f.v3((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1);}
public Vec3f multiply(Number arg1) {return Vec3f.v3((float)this.x*(float)arg1, (float)this.y*(float)arg1, (float)this.z*(float)arg1);}
public Vec3f div(Vec3f arg1) {return Vec3f.v3((float)this.x/(float)arg1.x, (float)this.y/(float)arg1.y, (float)this.z/(float)arg1.z);}
public Vec3f div(float arg1) {return Vec3f.v3((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1);}
public Vec3f div(Float arg1) {return Vec3f.v3((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1);}
public Vec3f div(Number arg1) {return Vec3f.v3((float)this.x/(float)arg1, (float)this.y/(float)arg1, (float)this.z/(float)arg1);}
public Vec3f radians() {return Vec3f.v3((float)(this.x/180f*Math.PI), (float)(this.y/180f*Math.PI), (float)(this.z/180f*Math.PI));}
public Vec3f degrees() {return Vec3f.v3((float)(this.x/Math.PI*180), (float)(this.y/Math.PI*180), (float)(this.z/Math.PI*180));}
public Vec3f sin() {return Vec3f.v3((float)Math.sin(this.x), (float)Math.sin(this.y), (float)Math.sin(this.z));}
public Vec3f cos() {return Vec3f.v3((float)Math.cos(this.x), (float)Math.cos(this.y), (float)Math.cos(this.z));}
public Vec3f tan() {return Vec3f.v3((float)Math.tan(this.x), (float)Math.tan(this.y), (float)Math.tan(this.z));}
public Vec3f asin() {return Vec3f.v3((float)Math.asin(this.x), (float)Math.asin(this.y), (float)Math.asin(this.z));}
public Vec3f acos() {return Vec3f.v3((float)Math.acos(this.x), (float)Math.acos(this.y), (float)Math.acos(this.z));}
public Vec3f atan() {return Vec3f.v3((float)Math.atan(this.x), (float)Math.atan(this.y), (float)Math.atan(this.z));}
public Vec3f atan(Vec3f x) {return Vec3f.v3((float)Math.atan2(this.x, x.x), (float)Math.atan2(this.y, x.y), (float)Math.atan2(this.z, x.z));}
public Vec3f pow(Vec3f power) {return Vec3f.v3((float)Math.pow(this.x, power.x), (float)Math.pow(this.y, power.y), (float)Math.pow(this.z, power.z));}
public Vec3f exp() {return Vec3f.v3((float)Math.exp(this.x), (float)Math.exp(this.y), (float)Math.exp(this.z));}
public Vec3f log() {return Vec3f.v3((float)Math.log(this.x), (float)Math.log(this.y), (float)Math.log(this.z));}
public Vec3f sqrt() {return Vec3f.v3((float)Math.sqrt(this.x), (float)Math.sqrt(this.y), (float)Math.sqrt(this.z));}
public Vec3f abs() {return Vec3f.v3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));}
public Vec3f sign() {return Vec3f.v3(Math.signum(this.x), Math.signum(this.y), Math.signum(this.z));}
public Vec3f floor() {return Vec3f.v3((float)Math.floor(this.x), (float)Math.floor(this.y), (float)Math.floor(this.z));}
public Vec3f ceil() {return Vec3f.v3((float)Math.ceil(this.x), (float)Math.ceil(this.y), (float)Math.ceil(this.z));}
public Vec3f fract() {return Vec3f.v3(this.x - (float)Math.floor(this.x), this.y - (float)Math.floor(this.y), this.z - (float)Math.floor(this.z));}
public Vec3f mod(Vec3f by) {return Vec3f.v3((float)(this.x-by.x*Math.floor(this.x/by.x)), (float)(this.y-by.y*Math.floor(this.y/by.y)), (float)(this.z-by.z*Math.floor(this.z/by.z)));}
public Vec3f min(Vec3f arg1) {return Vec3f.v3(Math.min(this.x, arg1.x), Math.min(this.y, arg1.y), Math.min(this.z, arg1.z));}
public Vec3f min(float arg1) {return Vec3f.v3(Math.min(this.x, arg1), Math.min(this.y, arg1), Math.min(this.z, arg1));}
public Vec3f max(Vec3f arg1) {return Vec3f.v3(Math.max(this.x, arg1.x), Math.max(this.y, arg1.y), Math.max(this.z, arg1.z));}
public Vec3f max(float arg1) {return Vec3f.v3(Math.max(this.x, arg1), Math.max(this.y, arg1), Math.max(this.z, arg1));}
public Vec3f clamp(Vec3f min, Vec3f max) {return Vec3f.v3(Math.max(min.x, Math.min(this.x, max.x)), Math.max(min.y, Math.min(this.y, max.y)), Math.max(min.z, Math.min(this.z, max.z)));}
public Vec3f clamp(float min, float max) {return Vec3f.v3(Math.max(min, Math.min(this.x, max)), Math.max(min, Math.min(this.y, max)), Math.max(min, Math.min(this.z, max)));}
public Vec3f mix(Vec3f to, Vec3f progress) {return Vec3f.v3(this.x * (1 - progress.x) + to.x * progress.x, this.y * (1 - progress.y) + to.y * progress.y, this.z * (1 - progress.z) + to.z * progress.z);}
public Vec3f mix(Vec3f to, float progress) {return Vec3f.v3(this.x * (1 - progress) + to.x * progress, this.y * (1 - progress) + to.y * progress, this.z * (1 - progress) + to.z * progress);}
public Vec3f step(Vec3f value) {return Vec3f.v3(value.x < this.x ? 0 : 1, value.y < this.y ? 0 : 1, value.z < this.z ? 0 : 1);}
public Vec3f smoothstep(Vec3f to, Vec3f progress) {return Vec3f.v3(progress.x < this.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < this.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < this.z ? 0 : progress.z > to.z ? 1 : progress.z*progress.z*(3 - 2*progress.z));}

//gglsl auto generated text

}
