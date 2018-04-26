package yk.jcommon.fastgeom;

import yk.jcommon.utils.MyMath;

import java.io.Serializable;

import static java.lang.Math.abs;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 24/09/15
 * Time: 11:10
 */
public final class Vec3i implements Serializable {//TODO jcommon
    public int x, y, z;

    //common auto generated text
    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3i v3(int x, int y, int z) {
        return new Vec3i(x, y, z);
    }

    public Vec3i(Vec3i other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public void copyFrom(Vec3i other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public Vec3i add(Vec3i other) {
        return new Vec3i(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vec3i add(int scalar) {
        return new Vec3i(x + scalar, y + scalar, z + scalar);
    }

    public Vec3i add(int x, int y, int z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }

    public void seAdd(Vec3i other) {
        x = x + other.x;
        y = y + other.y;
        z = z + other.z;
    }

    public void seAdd(int scalar) {
        x = x + scalar;
        y = y + scalar;
        z = z + scalar;
    }

    public void seAdd(int x, int y, int z) {
        this.x = this.x + x;
        this.y = this.y + y;
        this.z = this.z + z;
    }

    public Vec3i sub(Vec3i other) {
        return new Vec3i(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vec3i sub(int scalar) {
        return new Vec3i(x - scalar, y - scalar, z - scalar);
    }

    public Vec3i sub(int x, int y, int z) {
        return new Vec3i(this.x - x, this.y - y, this.z - z);
    }

    public void seSub(Vec3i other) {
        x = x - other.x;
        y = y - other.y;
        z = z - other.z;
    }

    public void seSub(int scalar) {
        x = x - scalar;
        y = y - scalar;
        z = z - scalar;
    }

    public void seSub(int x, int y, int z) {
        this.x = this.x - x;
        this.y = this.y - y;
        this.z = this.z - z;
    }

    public Vec3i mul(Vec3i other) {
        return new Vec3i(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vec3i mul(int scalar) {
        return new Vec3i(x * scalar, y * scalar, z * scalar);
    }

    public Vec3i mul(int x, int y, int z) {
        return new Vec3i(this.x * x, this.y * y, this.z * z);
    }

    public void seMul(Vec3i other) {
        x = x * other.x;
        y = y * other.y;
        z = z * other.z;
    }

    public void seMul(int scalar) {
        x = x * scalar;
        y = y * scalar;
        z = z * scalar;
    }

    public void seMul(int x, int y, int z) {
        this.x = this.x * x;
        this.y = this.y * y;
        this.z = this.z * z;
    }

    public Vec3i div(Vec3i other) {
        return new Vec3i(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public Vec3i div(int scalar) {
        return new Vec3i(x / scalar, y / scalar, z / scalar);
    }

    public Vec3i div(int x, int y, int z) {
        return new Vec3i(this.x / x, this.y / y, this.z / z);
    }

    public void seDiv(Vec3i other) {
        x = x / other.x;
        y = y / other.y;
        z = z / other.z;
    }

    public void seDiv(int scalar) {
        x = x / scalar;
        y = y / scalar;
        z = z / scalar;
    }

    public void seDiv(int x, int y, int z) {
        this.x = this.x / x;
        this.y = this.y / y;
        this.z = this.z / z;
    }

    public float dist(Vec3i other) {
        return (float)Math.sqrt(MyMath.sqr(other.x - this.x) + MyMath.sqr(other.y - this.y) + MyMath.sqr(other.z - this.z));
    }

    public float length() {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public int scalarProduct(Vec3i other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public int dot(Vec3i other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public String toString() {
        return "" + x + " " + y + " " + z;
    }


//common auto generated text

    public Vec3f toVec3f() {
        return new Vec3f(x, y, z);
    }

    public static Vec3i v3i(int x, int y, int z) {
        return new Vec3i(x, y, z);
    }

    public int manhattanDistanceTo(Vec3i other) {
        return abs(other.x - x) + abs(other.y - y) + abs(other.z - z);
    }

    public Vec3i() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vec3i vec3i = (Vec3i) o;

        if (x != vec3i.x) return false;
        if (y != vec3i.y) return false;
        if (z != vec3i.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    public Vec2i getXy() {
        return new Vec2i(x, y);
    }
    public Vec2i getXz() {
        return new Vec2i(x, z);
    }
    public Vec2i getYz() {
        return new Vec2i(y, z);
    }
}
