package yk.jcommon.utils;

import yk.jcommon.fastgeom.Vec2f;
import yk.jcommon.fastgeom.Vec3f;
import yk.jcommon.fastgeom.Vec4f;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 5/9/14
 * Time: 3:02 PM
 */
public class MyMath {
    public static float PI = (float) Math.PI;

    public static int module(int v, int m) {
        int res = v % m;
        return res < 0 ? res + m : res;
    }

    //POSITIVE remainder of the division
    public static float module(float v, float m) {
        float res = v % m;
        return res < 0 ? res + m : res;
    }

    public static int floorBy(int x, int by) {
        return (int) (Math.floor((float) x / by) * by);
    }

    public static int floorBy(float x, float by) {
        return (int) (Math.floor(x / by) * by);
    }
    public static int max(int arg0, int arg1) {return Math.max(arg0, arg1);}
    public static int min(int arg0, int arg1) {return Math.min(arg0, arg1);}
    public static float to01(float value, float from, float to) {return (value - from) / (to - from);}

    public static void mix(Vec3f from, Vec3f to, float progress, Vec3f res) {
        res.x = mix(from.x, to.x, progress);
        res.y = mix(from.y, to.y, progress);
        res.z = mix(from.z, to.z, progress);
    }

    //make angle [0..2PI)
    public static float angleNormalize02PI(float a) {
        return module(a, 2 * PI);
    }

    //make angle (-PI..PI]
    public static float angleNormalizeSigned(float a) {
        a = angleNormalize02PI(a);
        if (a > PI) a -= 2 * PI;
        return a;
    }

    //only [0..2PI) angles are accepted
    public static float mixAngle02PI(float a, float b, float mix) {
        if (b > a) return b - a > PI ? angleNormalize02PI(mix(a + 2 * PI, b, mix)) : mix(a, b, mix);
        return a - b > PI ? angleNormalize02PI(mix(a, b + 2 * PI, mix)) : mix(a, b, mix);
    }

    //only [0..2PI) angles are accepted
    public static float angleDif02PI(float a, float b) {
        if (b > a) return b - a > PI ? a + 2 * PI - b : b - a;
        return a - b > PI ? b + 2 * PI - a : a - b;
    }

    /**
     * Converts 'progress [0-1]' to hemicircle with top on 0.5 and bots at 0 and 1
     */
    public static float circleCurve(float progress) {
        return (float) sin(acos(clamp(progress, 0, 1) * 2 - 1));
    }





    //gglsl auto generated text
public static Float plus(Float arg0, Float arg1) {return (float)arg0+(float)arg1;}
public static Vec2f plus(Vec2f arg0, Vec2f arg1) {return Vec2f.v2((float)arg0.x+(float)arg1.x, (float)arg0.y+(float)arg1.y);}
public static Vec3f plus(Vec3f arg0, Vec3f arg1) {return Vec3f.v3((float)arg0.x+(float)arg1.x, (float)arg0.y+(float)arg1.y, (float)arg0.z+(float)arg1.z);}
public static Vec4f plus(Vec4f arg0, Vec4f arg1) {return Vec4f.v4((float)arg0.x+(float)arg1.x, (float)arg0.y+(float)arg1.y, (float)arg0.z+(float)arg1.z, (float)arg0.w+(float)arg1.w);}
public static Vec2f plus(float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y);}
public static Vec3f plus(float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z);}
public static Vec4f plus(float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z, (float)arg0+(float)arg1.w);}
public static Vec2f plus(Float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y);}
public static Vec3f plus(Float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z);}
public static Vec4f plus(Float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z, (float)arg0+(float)arg1.w);}
public static Vec2f plus(Number arg0, Vec2f arg1) {return Vec2f.v2((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y);}
public static Vec3f plus(Number arg0, Vec3f arg1) {return Vec3f.v3((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z);}
public static Vec4f plus(Number arg0, Vec4f arg1) {return Vec4f.v4((float)arg0+(float)arg1.x, (float)arg0+(float)arg1.y, (float)arg0+(float)arg1.z, (float)arg0+(float)arg1.w);}
public static Vec2f plus(Vec2f arg0, float arg1) {return Vec2f.v2((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1);}
public static Vec3f plus(Vec3f arg0, float arg1) {return Vec3f.v3((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1);}
public static Vec4f plus(Vec4f arg0, float arg1) {return Vec4f.v4((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1, (float)arg0.w+(float)arg1);}
public static Vec2f plus(Vec2f arg0, Float arg1) {return Vec2f.v2((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1);}
public static Vec3f plus(Vec3f arg0, Float arg1) {return Vec3f.v3((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1);}
public static Vec4f plus(Vec4f arg0, Float arg1) {return Vec4f.v4((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1, (float)arg0.w+(float)arg1);}
public static Vec2f plus(Vec2f arg0, Number arg1) {return Vec2f.v2((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1);}
public static Vec3f plus(Vec3f arg0, Number arg1) {return Vec3f.v3((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1);}
public static Vec4f plus(Vec4f arg0, Number arg1) {return Vec4f.v4((float)arg0.x+(float)arg1, (float)arg0.y+(float)arg1, (float)arg0.z+(float)arg1, (float)arg0.w+(float)arg1);}
public static Float minus(Float arg0, Float arg1) {return (float)arg0-(float)arg1;}
public static Vec2f minus(Vec2f arg0, Vec2f arg1) {return Vec2f.v2((float)arg0.x-(float)arg1.x, (float)arg0.y-(float)arg1.y);}
public static Vec3f minus(Vec3f arg0, Vec3f arg1) {return Vec3f.v3((float)arg0.x-(float)arg1.x, (float)arg0.y-(float)arg1.y, (float)arg0.z-(float)arg1.z);}
public static Vec4f minus(Vec4f arg0, Vec4f arg1) {return Vec4f.v4((float)arg0.x-(float)arg1.x, (float)arg0.y-(float)arg1.y, (float)arg0.z-(float)arg1.z, (float)arg0.w-(float)arg1.w);}
public static Vec2f minus(float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y);}
public static Vec3f minus(float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z);}
public static Vec4f minus(float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z, (float)arg0-(float)arg1.w);}
public static Vec2f minus(Float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y);}
public static Vec3f minus(Float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z);}
public static Vec4f minus(Float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z, (float)arg0-(float)arg1.w);}
public static Vec2f minus(Number arg0, Vec2f arg1) {return Vec2f.v2((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y);}
public static Vec3f minus(Number arg0, Vec3f arg1) {return Vec3f.v3((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z);}
public static Vec4f minus(Number arg0, Vec4f arg1) {return Vec4f.v4((float)arg0-(float)arg1.x, (float)arg0-(float)arg1.y, (float)arg0-(float)arg1.z, (float)arg0-(float)arg1.w);}
public static Vec2f minus(Vec2f arg0, float arg1) {return Vec2f.v2((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1);}
public static Vec3f minus(Vec3f arg0, float arg1) {return Vec3f.v3((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1);}
public static Vec4f minus(Vec4f arg0, float arg1) {return Vec4f.v4((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1, (float)arg0.w-(float)arg1);}
public static Vec2f minus(Vec2f arg0, Float arg1) {return Vec2f.v2((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1);}
public static Vec3f minus(Vec3f arg0, Float arg1) {return Vec3f.v3((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1);}
public static Vec4f minus(Vec4f arg0, Float arg1) {return Vec4f.v4((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1, (float)arg0.w-(float)arg1);}
public static Vec2f minus(Vec2f arg0, Number arg1) {return Vec2f.v2((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1);}
public static Vec3f minus(Vec3f arg0, Number arg1) {return Vec3f.v3((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1);}
public static Vec4f minus(Vec4f arg0, Number arg1) {return Vec4f.v4((float)arg0.x-(float)arg1, (float)arg0.y-(float)arg1, (float)arg0.z-(float)arg1, (float)arg0.w-(float)arg1);}
public static Float multiply(Float arg0, Float arg1) {return (float)arg0*(float)arg1;}
public static Vec2f multiply(Vec2f arg0, Vec2f arg1) {return Vec2f.v2((float)arg0.x*(float)arg1.x, (float)arg0.y*(float)arg1.y);}
public static Vec3f multiply(Vec3f arg0, Vec3f arg1) {return Vec3f.v3((float)arg0.x*(float)arg1.x, (float)arg0.y*(float)arg1.y, (float)arg0.z*(float)arg1.z);}
public static Vec4f multiply(Vec4f arg0, Vec4f arg1) {return Vec4f.v4((float)arg0.x*(float)arg1.x, (float)arg0.y*(float)arg1.y, (float)arg0.z*(float)arg1.z, (float)arg0.w*(float)arg1.w);}
public static Vec2f multiply(float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y);}
public static Vec3f multiply(float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z);}
public static Vec4f multiply(float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z, (float)arg0*(float)arg1.w);}
public static Vec2f multiply(Float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y);}
public static Vec3f multiply(Float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z);}
public static Vec4f multiply(Float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z, (float)arg0*(float)arg1.w);}
public static Vec2f multiply(Number arg0, Vec2f arg1) {return Vec2f.v2((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y);}
public static Vec3f multiply(Number arg0, Vec3f arg1) {return Vec3f.v3((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z);}
public static Vec4f multiply(Number arg0, Vec4f arg1) {return Vec4f.v4((float)arg0*(float)arg1.x, (float)arg0*(float)arg1.y, (float)arg0*(float)arg1.z, (float)arg0*(float)arg1.w);}
public static Vec2f multiply(Vec2f arg0, float arg1) {return Vec2f.v2((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1);}
public static Vec3f multiply(Vec3f arg0, float arg1) {return Vec3f.v3((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1);}
public static Vec4f multiply(Vec4f arg0, float arg1) {return Vec4f.v4((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1, (float)arg0.w*(float)arg1);}
public static Vec2f multiply(Vec2f arg0, Float arg1) {return Vec2f.v2((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1);}
public static Vec3f multiply(Vec3f arg0, Float arg1) {return Vec3f.v3((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1);}
public static Vec4f multiply(Vec4f arg0, Float arg1) {return Vec4f.v4((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1, (float)arg0.w*(float)arg1);}
public static Vec2f multiply(Vec2f arg0, Number arg1) {return Vec2f.v2((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1);}
public static Vec3f multiply(Vec3f arg0, Number arg1) {return Vec3f.v3((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1);}
public static Vec4f multiply(Vec4f arg0, Number arg1) {return Vec4f.v4((float)arg0.x*(float)arg1, (float)arg0.y*(float)arg1, (float)arg0.z*(float)arg1, (float)arg0.w*(float)arg1);}
public static Float div(Float arg0, Float arg1) {return (float)arg0/(float)arg1;}
public static Vec2f div(Vec2f arg0, Vec2f arg1) {return Vec2f.v2((float)arg0.x/(float)arg1.x, (float)arg0.y/(float)arg1.y);}
public static Vec3f div(Vec3f arg0, Vec3f arg1) {return Vec3f.v3((float)arg0.x/(float)arg1.x, (float)arg0.y/(float)arg1.y, (float)arg0.z/(float)arg1.z);}
public static Vec4f div(Vec4f arg0, Vec4f arg1) {return Vec4f.v4((float)arg0.x/(float)arg1.x, (float)arg0.y/(float)arg1.y, (float)arg0.z/(float)arg1.z, (float)arg0.w/(float)arg1.w);}
public static Vec2f div(float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y);}
public static Vec3f div(float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z);}
public static Vec4f div(float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z, (float)arg0/(float)arg1.w);}
public static Vec2f div(Float arg0, Vec2f arg1) {return Vec2f.v2((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y);}
public static Vec3f div(Float arg0, Vec3f arg1) {return Vec3f.v3((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z);}
public static Vec4f div(Float arg0, Vec4f arg1) {return Vec4f.v4((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z, (float)arg0/(float)arg1.w);}
public static Vec2f div(Number arg0, Vec2f arg1) {return Vec2f.v2((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y);}
public static Vec3f div(Number arg0, Vec3f arg1) {return Vec3f.v3((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z);}
public static Vec4f div(Number arg0, Vec4f arg1) {return Vec4f.v4((float)arg0/(float)arg1.x, (float)arg0/(float)arg1.y, (float)arg0/(float)arg1.z, (float)arg0/(float)arg1.w);}
public static Vec2f div(Vec2f arg0, float arg1) {return Vec2f.v2((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1);}
public static Vec3f div(Vec3f arg0, float arg1) {return Vec3f.v3((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1);}
public static Vec4f div(Vec4f arg0, float arg1) {return Vec4f.v4((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1, (float)arg0.w/(float)arg1);}
public static Vec2f div(Vec2f arg0, Float arg1) {return Vec2f.v2((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1);}
public static Vec3f div(Vec3f arg0, Float arg1) {return Vec3f.v3((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1);}
public static Vec4f div(Vec4f arg0, Float arg1) {return Vec4f.v4((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1, (float)arg0.w/(float)arg1);}
public static Vec2f div(Vec2f arg0, Number arg1) {return Vec2f.v2((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1);}
public static Vec3f div(Vec3f arg0, Number arg1) {return Vec3f.v3((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1);}
public static Vec4f div(Vec4f arg0, Number arg1) {return Vec4f.v4((float)arg0.x/(float)arg1, (float)arg0.y/(float)arg1, (float)arg0.z/(float)arg1, (float)arg0.w/(float)arg1);}
public static float radians(float arg0) {return (float)(arg0/180f*Math.PI);}
public static Vec2f radians(Vec2f arg0) {return Vec2f.v2((float)(arg0.x/180f*Math.PI), (float)(arg0.y/180f*Math.PI));}
public static Vec3f radians(Vec3f arg0) {return Vec3f.v3((float)(arg0.x/180f*Math.PI), (float)(arg0.y/180f*Math.PI), (float)(arg0.z/180f*Math.PI));}
public static Vec4f radians(Vec4f arg0) {return Vec4f.v4((float)(arg0.x/180f*Math.PI), (float)(arg0.y/180f*Math.PI), (float)(arg0.z/180f*Math.PI), (float)(arg0.w/180f*Math.PI));}
public static float degrees(float arg0) {return (float)(arg0/Math.PI*180);}
public static Vec2f degrees(Vec2f arg0) {return Vec2f.v2((float)(arg0.x/Math.PI*180), (float)(arg0.y/Math.PI*180));}
public static Vec3f degrees(Vec3f arg0) {return Vec3f.v3((float)(arg0.x/Math.PI*180), (float)(arg0.y/Math.PI*180), (float)(arg0.z/Math.PI*180));}
public static Vec4f degrees(Vec4f arg0) {return Vec4f.v4((float)(arg0.x/Math.PI*180), (float)(arg0.y/Math.PI*180), (float)(arg0.z/Math.PI*180), (float)(arg0.w/Math.PI*180));}
public static float sin(float arg0) {return (float)Math.sin(arg0);}
public static Vec2f sin(Vec2f arg0) {return Vec2f.v2((float)Math.sin(arg0.x), (float)Math.sin(arg0.y));}
public static Vec3f sin(Vec3f arg0) {return Vec3f.v3((float)Math.sin(arg0.x), (float)Math.sin(arg0.y), (float)Math.sin(arg0.z));}
public static Vec4f sin(Vec4f arg0) {return Vec4f.v4((float)Math.sin(arg0.x), (float)Math.sin(arg0.y), (float)Math.sin(arg0.z), (float)Math.sin(arg0.w));}
public static float cos(float arg0) {return (float)Math.cos(arg0);}
public static Vec2f cos(Vec2f arg0) {return Vec2f.v2((float)Math.cos(arg0.x), (float)Math.cos(arg0.y));}
public static Vec3f cos(Vec3f arg0) {return Vec3f.v3((float)Math.cos(arg0.x), (float)Math.cos(arg0.y), (float)Math.cos(arg0.z));}
public static Vec4f cos(Vec4f arg0) {return Vec4f.v4((float)Math.cos(arg0.x), (float)Math.cos(arg0.y), (float)Math.cos(arg0.z), (float)Math.cos(arg0.w));}
public static float tan(float arg0) {return (float)Math.tan(arg0);}
public static Vec2f tan(Vec2f arg0) {return Vec2f.v2((float)Math.tan(arg0.x), (float)Math.tan(arg0.y));}
public static Vec3f tan(Vec3f arg0) {return Vec3f.v3((float)Math.tan(arg0.x), (float)Math.tan(arg0.y), (float)Math.tan(arg0.z));}
public static Vec4f tan(Vec4f arg0) {return Vec4f.v4((float)Math.tan(arg0.x), (float)Math.tan(arg0.y), (float)Math.tan(arg0.z), (float)Math.tan(arg0.w));}
public static float asin(float arg0) {return (float)Math.asin(arg0);}
public static Vec2f asin(Vec2f arg0) {return Vec2f.v2((float)Math.asin(arg0.x), (float)Math.asin(arg0.y));}
public static Vec3f asin(Vec3f arg0) {return Vec3f.v3((float)Math.asin(arg0.x), (float)Math.asin(arg0.y), (float)Math.asin(arg0.z));}
public static Vec4f asin(Vec4f arg0) {return Vec4f.v4((float)Math.asin(arg0.x), (float)Math.asin(arg0.y), (float)Math.asin(arg0.z), (float)Math.asin(arg0.w));}
public static float acos(float arg0) {return (float)Math.acos(arg0);}
public static Vec2f acos(Vec2f arg0) {return Vec2f.v2((float)Math.acos(arg0.x), (float)Math.acos(arg0.y));}
public static Vec3f acos(Vec3f arg0) {return Vec3f.v3((float)Math.acos(arg0.x), (float)Math.acos(arg0.y), (float)Math.acos(arg0.z));}
public static Vec4f acos(Vec4f arg0) {return Vec4f.v4((float)Math.acos(arg0.x), (float)Math.acos(arg0.y), (float)Math.acos(arg0.z), (float)Math.acos(arg0.w));}
public static float atan(float arg0) {return (float)Math.atan(arg0);}
public static Vec2f atan(Vec2f arg0) {return Vec2f.v2((float)Math.atan(arg0.x), (float)Math.atan(arg0.y));}
public static Vec3f atan(Vec3f arg0) {return Vec3f.v3((float)Math.atan(arg0.x), (float)Math.atan(arg0.y), (float)Math.atan(arg0.z));}
public static Vec4f atan(Vec4f arg0) {return Vec4f.v4((float)Math.atan(arg0.x), (float)Math.atan(arg0.y), (float)Math.atan(arg0.z), (float)Math.atan(arg0.w));}
public static float atan(float y, float x) {return (float)Math.atan2(y, x);}
public static Vec2f atan(Vec2f y, Vec2f x) {return Vec2f.v2((float)Math.atan2(y.x, x.x), (float)Math.atan2(y.y, x.y));}
public static Vec3f atan(Vec3f y, Vec3f x) {return Vec3f.v3((float)Math.atan2(y.x, x.x), (float)Math.atan2(y.y, x.y), (float)Math.atan2(y.z, x.z));}
public static Vec4f atan(Vec4f y, Vec4f x) {return Vec4f.v4((float)Math.atan2(y.x, x.x), (float)Math.atan2(y.y, x.y), (float)Math.atan2(y.z, x.z), (float)Math.atan2(y.w, x.w));}
public static float pow(float value, float power) {return (float)Math.pow(value, power);}
public static Vec2f pow(Vec2f value, Vec2f power) {return Vec2f.v2((float)Math.pow(value.x, power.x), (float)Math.pow(value.y, power.y));}
public static Vec3f pow(Vec3f value, Vec3f power) {return Vec3f.v3((float)Math.pow(value.x, power.x), (float)Math.pow(value.y, power.y), (float)Math.pow(value.z, power.z));}
public static Vec4f pow(Vec4f value, Vec4f power) {return Vec4f.v4((float)Math.pow(value.x, power.x), (float)Math.pow(value.y, power.y), (float)Math.pow(value.z, power.z), (float)Math.pow(value.w, power.w));}
public static float exp(float arg0) {return (float)Math.exp(arg0);}
public static Vec2f exp(Vec2f arg0) {return Vec2f.v2((float)Math.exp(arg0.x), (float)Math.exp(arg0.y));}
public static Vec3f exp(Vec3f arg0) {return Vec3f.v3((float)Math.exp(arg0.x), (float)Math.exp(arg0.y), (float)Math.exp(arg0.z));}
public static Vec4f exp(Vec4f arg0) {return Vec4f.v4((float)Math.exp(arg0.x), (float)Math.exp(arg0.y), (float)Math.exp(arg0.z), (float)Math.exp(arg0.w));}
public static float log(float arg0) {return (float)Math.log(arg0);}
public static Vec2f log(Vec2f arg0) {return Vec2f.v2((float)Math.log(arg0.x), (float)Math.log(arg0.y));}
public static Vec3f log(Vec3f arg0) {return Vec3f.v3((float)Math.log(arg0.x), (float)Math.log(arg0.y), (float)Math.log(arg0.z));}
public static Vec4f log(Vec4f arg0) {return Vec4f.v4((float)Math.log(arg0.x), (float)Math.log(arg0.y), (float)Math.log(arg0.z), (float)Math.log(arg0.w));}
public static float sqrt(float arg0) {return (float)Math.sqrt(arg0);}
public static Vec2f sqrt(Vec2f arg0) {return Vec2f.v2((float)Math.sqrt(arg0.x), (float)Math.sqrt(arg0.y));}
public static Vec3f sqrt(Vec3f arg0) {return Vec3f.v3((float)Math.sqrt(arg0.x), (float)Math.sqrt(arg0.y), (float)Math.sqrt(arg0.z));}
public static Vec4f sqrt(Vec4f arg0) {return Vec4f.v4((float)Math.sqrt(arg0.x), (float)Math.sqrt(arg0.y), (float)Math.sqrt(arg0.z), (float)Math.sqrt(arg0.w));}
public static float abs(float arg0) {return Math.abs(arg0);}
public static Vec2f abs(Vec2f arg0) {return Vec2f.v2(Math.abs(arg0.x), Math.abs(arg0.y));}
public static Vec3f abs(Vec3f arg0) {return Vec3f.v3(Math.abs(arg0.x), Math.abs(arg0.y), Math.abs(arg0.z));}
public static Vec4f abs(Vec4f arg0) {return Vec4f.v4(Math.abs(arg0.x), Math.abs(arg0.y), Math.abs(arg0.z), Math.abs(arg0.w));}
public static float sign(float arg0) {return Math.signum(arg0);}
public static Vec2f sign(Vec2f arg0) {return Vec2f.v2(Math.signum(arg0.x), Math.signum(arg0.y));}
public static Vec3f sign(Vec3f arg0) {return Vec3f.v3(Math.signum(arg0.x), Math.signum(arg0.y), Math.signum(arg0.z));}
public static Vec4f sign(Vec4f arg0) {return Vec4f.v4(Math.signum(arg0.x), Math.signum(arg0.y), Math.signum(arg0.z), Math.signum(arg0.w));}
public static float floor(float arg0) {return (float)Math.floor(arg0);}
public static Vec2f floor(Vec2f arg0) {return Vec2f.v2((float)Math.floor(arg0.x), (float)Math.floor(arg0.y));}
public static Vec3f floor(Vec3f arg0) {return Vec3f.v3((float)Math.floor(arg0.x), (float)Math.floor(arg0.y), (float)Math.floor(arg0.z));}
public static Vec4f floor(Vec4f arg0) {return Vec4f.v4((float)Math.floor(arg0.x), (float)Math.floor(arg0.y), (float)Math.floor(arg0.z), (float)Math.floor(arg0.w));}
public static float ceil(float arg0) {return (float)Math.ceil(arg0);}
public static Vec2f ceil(Vec2f arg0) {return Vec2f.v2((float)Math.ceil(arg0.x), (float)Math.ceil(arg0.y));}
public static Vec3f ceil(Vec3f arg0) {return Vec3f.v3((float)Math.ceil(arg0.x), (float)Math.ceil(arg0.y), (float)Math.ceil(arg0.z));}
public static Vec4f ceil(Vec4f arg0) {return Vec4f.v4((float)Math.ceil(arg0.x), (float)Math.ceil(arg0.y), (float)Math.ceil(arg0.z), (float)Math.ceil(arg0.w));}
public static float fract(float arg0) {return arg0 - (float)Math.floor(arg0);}
public static Vec2f fract(Vec2f arg0) {return Vec2f.v2(arg0.x - (float)Math.floor(arg0.x), arg0.y - (float)Math.floor(arg0.y));}
public static Vec3f fract(Vec3f arg0) {return Vec3f.v3(arg0.x - (float)Math.floor(arg0.x), arg0.y - (float)Math.floor(arg0.y), arg0.z - (float)Math.floor(arg0.z));}
public static Vec4f fract(Vec4f arg0) {return Vec4f.v4(arg0.x - (float)Math.floor(arg0.x), arg0.y - (float)Math.floor(arg0.y), arg0.z - (float)Math.floor(arg0.z), arg0.w - (float)Math.floor(arg0.w));}
public static float mod(float value, float by) {return (float)(value-by*Math.floor(value/by));}
public static Vec2f mod(Vec2f value, Vec2f by) {return Vec2f.v2((float)(value.x-by.x*Math.floor(value.x/by.x)), (float)(value.y-by.y*Math.floor(value.y/by.y)));}
public static Vec3f mod(Vec3f value, Vec3f by) {return Vec3f.v3((float)(value.x-by.x*Math.floor(value.x/by.x)), (float)(value.y-by.y*Math.floor(value.y/by.y)), (float)(value.z-by.z*Math.floor(value.z/by.z)));}
public static Vec4f mod(Vec4f value, Vec4f by) {return Vec4f.v4((float)(value.x-by.x*Math.floor(value.x/by.x)), (float)(value.y-by.y*Math.floor(value.y/by.y)), (float)(value.z-by.z*Math.floor(value.z/by.z)), (float)(value.w-by.w*Math.floor(value.w/by.w)));}
public static float min(float arg0, float arg1) {return Math.min(arg0, arg1);}
public static Vec2f min(Vec2f arg0, Vec2f arg1) {return Vec2f.v2(Math.min(arg0.x, arg1.x), Math.min(arg0.y, arg1.y));}
public static Vec3f min(Vec3f arg0, Vec3f arg1) {return Vec3f.v3(Math.min(arg0.x, arg1.x), Math.min(arg0.y, arg1.y), Math.min(arg0.z, arg1.z));}
public static Vec4f min(Vec4f arg0, Vec4f arg1) {return Vec4f.v4(Math.min(arg0.x, arg1.x), Math.min(arg0.y, arg1.y), Math.min(arg0.z, arg1.z), Math.min(arg0.w, arg1.w));}
public static Vec2f min(Vec2f arg0, float arg1) {return Vec2f.v2(Math.min(arg0.x, arg1), Math.min(arg0.y, arg1));}
public static Vec3f min(Vec3f arg0, float arg1) {return Vec3f.v3(Math.min(arg0.x, arg1), Math.min(arg0.y, arg1), Math.min(arg0.z, arg1));}
public static Vec4f min(Vec4f arg0, float arg1) {return Vec4f.v4(Math.min(arg0.x, arg1), Math.min(arg0.y, arg1), Math.min(arg0.z, arg1), Math.min(arg0.w, arg1));}
public static float max(float arg0, float arg1) {return Math.max(arg0, arg1);}
public static Vec2f max(Vec2f arg0, Vec2f arg1) {return Vec2f.v2(Math.max(arg0.x, arg1.x), Math.max(arg0.y, arg1.y));}
public static Vec3f max(Vec3f arg0, Vec3f arg1) {return Vec3f.v3(Math.max(arg0.x, arg1.x), Math.max(arg0.y, arg1.y), Math.max(arg0.z, arg1.z));}
public static Vec4f max(Vec4f arg0, Vec4f arg1) {return Vec4f.v4(Math.max(arg0.x, arg1.x), Math.max(arg0.y, arg1.y), Math.max(arg0.z, arg1.z), Math.max(arg0.w, arg1.w));}
public static Vec2f max(Vec2f arg0, float arg1) {return Vec2f.v2(Math.max(arg0.x, arg1), Math.max(arg0.y, arg1));}
public static Vec3f max(Vec3f arg0, float arg1) {return Vec3f.v3(Math.max(arg0.x, arg1), Math.max(arg0.y, arg1), Math.max(arg0.z, arg1));}
public static Vec4f max(Vec4f arg0, float arg1) {return Vec4f.v4(Math.max(arg0.x, arg1), Math.max(arg0.y, arg1), Math.max(arg0.z, arg1), Math.max(arg0.w, arg1));}
public static float clamp(float value, float min, float max) {return Math.max(min, Math.min(value, max));}
public static Vec2f clamp(Vec2f value, Vec2f min, Vec2f max) {return Vec2f.v2(Math.max(min.x, Math.min(value.x, max.x)), Math.max(min.y, Math.min(value.y, max.y)));}
public static Vec3f clamp(Vec3f value, Vec3f min, Vec3f max) {return Vec3f.v3(Math.max(min.x, Math.min(value.x, max.x)), Math.max(min.y, Math.min(value.y, max.y)), Math.max(min.z, Math.min(value.z, max.z)));}
public static Vec4f clamp(Vec4f value, Vec4f min, Vec4f max) {return Vec4f.v4(Math.max(min.x, Math.min(value.x, max.x)), Math.max(min.y, Math.min(value.y, max.y)), Math.max(min.z, Math.min(value.z, max.z)), Math.max(min.w, Math.min(value.w, max.w)));}
public static Vec2f clamp(Vec2f value, float min, float max) {return Vec2f.v2(Math.max(min, Math.min(value.x, max)), Math.max(min, Math.min(value.y, max)));}
public static Vec3f clamp(Vec3f value, float min, float max) {return Vec3f.v3(Math.max(min, Math.min(value.x, max)), Math.max(min, Math.min(value.y, max)), Math.max(min, Math.min(value.z, max)));}
public static Vec4f clamp(Vec4f value, float min, float max) {return Vec4f.v4(Math.max(min, Math.min(value.x, max)), Math.max(min, Math.min(value.y, max)), Math.max(min, Math.min(value.z, max)), Math.max(min, Math.min(value.w, max)));}
public static float mix(float from, float to, float progress) {return from * (1 - progress) + to * progress;}
public static Vec2f mix(Vec2f from, Vec2f to, Vec2f progress) {return Vec2f.v2(from.x * (1 - progress.x) + to.x * progress.x, from.y * (1 - progress.y) + to.y * progress.y);}
public static Vec3f mix(Vec3f from, Vec3f to, Vec3f progress) {return Vec3f.v3(from.x * (1 - progress.x) + to.x * progress.x, from.y * (1 - progress.y) + to.y * progress.y, from.z * (1 - progress.z) + to.z * progress.z);}
public static Vec4f mix(Vec4f from, Vec4f to, Vec4f progress) {return Vec4f.v4(from.x * (1 - progress.x) + to.x * progress.x, from.y * (1 - progress.y) + to.y * progress.y, from.z * (1 - progress.z) + to.z * progress.z, from.w * (1 - progress.w) + to.w * progress.w);}
public static Vec2f mix(Vec2f from, Vec2f to, float progress) {return Vec2f.v2(from.x * (1 - progress) + to.x * progress, from.y * (1 - progress) + to.y * progress);}
public static Vec3f mix(Vec3f from, Vec3f to, float progress) {return Vec3f.v3(from.x * (1 - progress) + to.x * progress, from.y * (1 - progress) + to.y * progress, from.z * (1 - progress) + to.z * progress);}
public static Vec4f mix(Vec4f from, Vec4f to, float progress) {return Vec4f.v4(from.x * (1 - progress) + to.x * progress, from.y * (1 - progress) + to.y * progress, from.z * (1 - progress) + to.z * progress, from.w * (1 - progress) + to.w * progress);}
public static float step(float edge, float value) {return value < edge ? 0 : 1;}
public static Vec2f step(Vec2f edge, Vec2f value) {return Vec2f.v2(value.x < edge.x ? 0 : 1, value.y < edge.y ? 0 : 1);}
public static Vec3f step(Vec3f edge, Vec3f value) {return Vec3f.v3(value.x < edge.x ? 0 : 1, value.y < edge.y ? 0 : 1, value.z < edge.z ? 0 : 1);}
public static Vec4f step(Vec4f edge, Vec4f value) {return Vec4f.v4(value.x < edge.x ? 0 : 1, value.y < edge.y ? 0 : 1, value.z < edge.z ? 0 : 1, value.w < edge.w ? 0 : 1);}
public static Vec2f step(float edge, Vec2f value) {return Vec2f.v2(value.x < edge ? 0 : 1, value.y < edge ? 0 : 1);}
public static Vec3f step(float edge, Vec3f value) {return Vec3f.v3(value.x < edge ? 0 : 1, value.y < edge ? 0 : 1, value.z < edge ? 0 : 1);}
public static Vec4f step(float edge, Vec4f value) {return Vec4f.v4(value.x < edge ? 0 : 1, value.y < edge ? 0 : 1, value.z < edge ? 0 : 1, value.w < edge ? 0 : 1);}
public static float smoothstep(float from, float to, float progress) {return progress < from ? 0 : progress > to ? 1 : progress*progress*(3 - 2*progress);}
public static Vec2f smoothstep(Vec2f from, Vec2f to, Vec2f progress) {return Vec2f.v2(progress.x < from.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y));}
public static Vec3f smoothstep(Vec3f from, Vec3f to, Vec3f progress) {return Vec3f.v3(progress.x < from.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < from.z ? 0 : progress.z > to.z ? 1 : progress.z*progress.z*(3 - 2*progress.z));}
public static Vec4f smoothstep(Vec4f from, Vec4f to, Vec4f progress) {return Vec4f.v4(progress.x < from.x ? 0 : progress.x > to.x ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from.y ? 0 : progress.y > to.y ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < from.z ? 0 : progress.z > to.z ? 1 : progress.z*progress.z*(3 - 2*progress.z), progress.w < from.w ? 0 : progress.w > to.w ? 1 : progress.w*progress.w*(3 - 2*progress.w));}
public static Vec2f smoothstep(float from, float to, Vec2f progress) {return Vec2f.v2(progress.x < from ? 0 : progress.x > to ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from ? 0 : progress.y > to ? 1 : progress.y*progress.y*(3 - 2*progress.y));}
public static Vec3f smoothstep(float from, float to, Vec3f progress) {return Vec3f.v3(progress.x < from ? 0 : progress.x > to ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from ? 0 : progress.y > to ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < from ? 0 : progress.z > to ? 1 : progress.z*progress.z*(3 - 2*progress.z));}
public static Vec4f smoothstep(float from, float to, Vec4f progress) {return Vec4f.v4(progress.x < from ? 0 : progress.x > to ? 1 : progress.x*progress.x*(3 - 2*progress.x), progress.y < from ? 0 : progress.y > to ? 1 : progress.y*progress.y*(3 - 2*progress.y), progress.z < from ? 0 : progress.z > to ? 1 : progress.z*progress.z*(3 - 2*progress.z), progress.w < from ? 0 : progress.w > to ? 1 : progress.w*progress.w*(3 - 2*progress.w));}

//gglsl auto generated text
}
