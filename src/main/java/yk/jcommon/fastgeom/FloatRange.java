package yk.jcommon.fastgeom;

public class FloatRange {
    public static FloatRange ZERO = new FloatRange(0);
    public static FloatRange INFINITY = new FloatRange(Float.MIN_VALUE, Float.MAX_VALUE);

    public float min;
    public float max;

    public FloatRange(float value) {
        this(value, value);
    }

    public FloatRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean wider(FloatRange r) {
        return (min == r.min || max == r.max) && max - min > r.max - r.min;
    }

    public boolean contains(float value) {
        return min <= value && value <= max;
    }

    public FloatRange add(FloatRange r) {
        return add(r.min, r.max);
    }

    public FloatRange add(float v) {
        return new FloatRange(min + v, max + v);
    }

    public FloatRange add(float min, float max) {
        return new FloatRange(this.min + min, this.max + max);
    }

    public FloatRange mul(float v) {
        return new FloatRange(min * v, max * v);
    }

    public FloatRange div(float v) {
        return new FloatRange(min / v, max / v);
    }

    public FloatRange intersection(FloatRange other) {
        FloatRange result = new FloatRange(Math.max(min, other.min), Math.min(max, other.max));
        return result.max >= result.min ? result : null;
    }

    public float width() {
        return Math.abs(max - min);
    }

    @Override
    public String toString() {
        if (min == max) return "" + min;
        return "\"" + min + "-" + max + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloatRange that = (FloatRange) o;

        if (Float.compare(that.max, max) != 0) return false;
        if (Float.compare(that.min, min) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (min != +0.0f ? Float.floatToIntBits(min) : 0);
        result = 31 * result + (max != +0.0f ? Float.floatToIntBits(max) : 0);
        return result;
    }

    public float fit(float value) {
        return Math.max(min, Math.min(max, value));
    }
}
