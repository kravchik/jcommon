package yk.jcommon.fastgeom;

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

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f toVec3f() {
        return new Vec3f(x, y, z);
    }

    public static Vec3i v3i(int x, int y, int z) {
        return new Vec3i(x, y, z);
    }

    public int manhattanDistanceTo(Vec3i other) {
        return abs(other.x - x) + abs(other.y - y) + abs(other.z - z);
    }

    @Override
    public String toString() {
        return "xyz: " + x + " " + y + " " + z;
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

    public Vec3i add(Vec3i pos) {
        return new Vec3i(x + pos.x, y + pos.y, z + pos.z);
    }

    public Vec3i add(int x, int y, int  z) {
        return new Vec3i(this.x + x, this.y + y, this.z + z);
    }

    public Vec3i sub(Vec3i pos) {
        return new Vec3i(x - pos.x, y - pos.y, z - pos.z);
    }

    public Vec2i getXy() {
        return new Vec2i(x, y);
    }
    public Vec2i getXz() {
        return new Vec2i(x, z);
    }
}
