package yk.jcommon.fastgeom;

import yk.jcommon.utils.BadException;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 17/08/16
 * Time: 11:49
 */
public class Vec3fStatic {
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

    public static void sub(Vec3f a, Vec3f b, Vec3f result) {
        result.x = a.x - b.x;
        result.y = a.y - b.y;
        result.z = a.z - b.z;
    }

    public static void sub(Vec3f a, Vec3f b) {
        a.x = a.x - b.x;
        a.y = a.y - b.y;
        a.z = a.z - b.z;
    }

    public static void sub(Vec3f a, float b, Vec3f result) {
        result.x = a.x - b;
        result.y = a.y - b;
        result.z = a.z - b;
    }

    public static void sub(Vec3f a, float b) {
        a.x = a.x - b;
        a.y = a.y - b;
        a.z = a.z - b;
    }

    public static void add(Vec3f a, Vec3f b, Vec3f result) {
        result.x = a.x + b.x;
        result.y = a.y + b.y;
        result.z = a.z + b.z;
    }

    public static void add(Vec3f a, Vec3f b) {
        a.x = a.x + b.x;
        a.y = a.y + b.y;
        a.z = a.z + b.z;
    }

    public static void add(Vec3f a, float b, Vec3f result) {
        result.x = a.x + b;
        result.y = a.y + b;
        result.z = a.z + b;
    }

    public static void add(Vec3f a, float b) {
        a.x = a.x + b;
        a.y = a.y + b;
        a.z = a.z + b;
    }

    public static void mul(Vec3f a, Vec3f b, Vec3f result) {
        result.x = a.x * b.x;
        result.y = a.y * b.y;
        result.z = a.z * b.z;
    }

    public static void mul(Vec3f a, Vec3f b) {
        a.x = a.x * b.x;
        a.y = a.y * b.y;
        a.z = a.z * b.z;
    }

    public static void mul(Vec3f a, float b, Vec3f result) {
        result.x = a.x * b;
        result.y = a.y * b;
        result.z = a.z * b;
    }

    public static void mul(Vec3f a, float b) {
        a.x = a.x * b;
        a.y = a.y * b;
        a.z = a.z * b;
    }

    public static void div(Vec3f a, Vec3f b, Vec3f result) {
        result.x = a.x / b.x;
        result.y = a.y / b.y;
        result.z = a.z / b.z;
    }

    public static void div(Vec3f a, Vec3f b) {
        a.x = a.x / b.x;
        a.y = a.y / b.y;
        a.z = a.z / b.z;
    }

    public static void div(Vec3f a, float b, Vec3f result) {
        result.x = a.x / b;
        result.y = a.y / b;
        result.z = a.z / b;
    }

    public static void div(Vec3f a, float b) {
        a.x = a.x / b;
        a.y = a.y / b;
        a.z = a.z / b;
    }

    public static void crossProduct(Vec3f a, Vec3f b, Vec3f res) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;

        res.x = x;//so we can pass here res == a
        res.y = y;
        res.z = z;
    }

    public static void normalize(Vec3f vec, float r) {
        final float m = r / vec.length();
        if (Float.isNaN(m)) BadException.die("NaN for " + vec);
        if (Float.isInfinite(m)) BadException.die("Infinite for " + vec);
        vec.x = vec.x * m;
        vec.y = vec.y * m;
        vec.z = vec.z * m;
    }
}
