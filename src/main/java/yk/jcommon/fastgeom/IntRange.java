package yk.jcommon.fastgeom;

import java.io.Serializable;

public class IntRange implements Serializable {
    public static IntRange ZERO = new IntRange(0);
    public static IntRange INFINITY = new IntRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

    public int min;
    public int max;

    public IntRange(int value) {
        this(value, value);
    }

    public IntRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int average() {
        return (min + max) / 2;
    }

    public boolean wider(IntRange r) {
        return (min == r.min || max == r.max) && max - min > r.max - r.min;
    }

    public boolean contains(int value) {
        return min <= value && value <= max;
    }

    public IntRange add(IntRange r) {
        return add(r.min, r.max);
    }

    public IntRange add(int v) {
        return new IntRange(min + v, max + v);
    }

    public IntRange add(int min, int max) {
        return new IntRange(this.min + min, this.max + max);
    }

    public IntRange mul(int v) {
        return new IntRange(min * v, max * v);
    }

    public IntRange div(int v) {
        return new IntRange(min / v, max / v);
    }

    public IntRange intersection(IntRange other) {
        IntRange result = new IntRange(Math.max(min, other.min), Math.min(max, other.max));
        return result.max >= result.min ? result : null;
    }

    public float getInterpolation(float p) {
        return (1 - p) * min + p * max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntRange intRange = (IntRange) o;

        if (max != intRange.max) return false;
        if (min != intRange.min) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = min;
        result = 31 * result + max;
        return result;
    }

    @Override
    public String toString() {
        if (min == max) return "" + min;
        return "\"" + min + "-" + max + "\"";
    }
}
