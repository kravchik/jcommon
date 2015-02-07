package yk.jcommon.utils;

/**
 * Copyright Yuri Kravchik 2007 Date: 25.11.2007 Time: 20:53:35
 */
public class CircleInterpolator extends LinearInterpolator {
    /**
     * Converts (linear) [0-1] interval to the S-like [0-1] interval
     *
     * @param value value to convert (must be in [0, 1])
     * @return curved value in [0, 1]
     */
    public static float getSCurve(float value) {
        if ((value < 0) || (value > 1)) {
            return value;
        }
        value *= 2;
        if (value >= 1) {
            value = 2 - value;
            value *= value;
            value = 2 - value;
        } else {
            value *= value;
        }
        return value / 2;
    }

    /**
     * Makes circle interpolation between 2 edge values with given position
     * between them.
     *
     * @param a left edge value
     * @param b right edge value
     * @param x position between left and right edges [0..1]
     * @return interpolated value
     */
    @Override
    public float interpolate(float a, float b, float x) {
        x = getSCurve(x);
        return super.interpolate(a, b, x);
    }

    /**
     * Makes circle interpolation between 4 edge values on the flat with given
     * position between them. <code>
     * <br><b>a</b> -x- <b>b</b>
     * <br>   |
     * <br>   y
     * <br>   |
     * <br><b>c</b> -x- <b>d</b></code>
     *
     * @param a left top edge value
     * @param b right top edge value
     * @param c left bottom edge value
     * @param d right bottom edge value
     * @param x position between left and right edges [0..1]
     * @param y position between top and bottom edges [0..1]
     * @return interpolated value
     */
    @Override
    public float interpolate(float a, float b, float c, float d, float x, float y) {
        x = getSCurve(x);
        y = getSCurve(y);
        return super.interpolate(a, b, c, d, x, y);
    }
}