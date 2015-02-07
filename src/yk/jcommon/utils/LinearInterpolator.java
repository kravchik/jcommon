package yk.jcommon.utils;

/**
 * Copyright Yuri Kravchik 2007
 * Date: 21.11.2007
 * Time: 22:17:40
 */
public class LinearInterpolator {

    /**
     * Makes linear interpolation between 4 edge values on the flat with given position between them.
     * <code>
     * <br><b>a</b> -x- <b>b</b>
     * <br>   |
     * <br>   y
     * <br>   |
     * <br><b>c</b> -x- <b>d</b></code>
     * @param a left top edge value
     * @param b right top edge value
     * @param c left bottom edge value
     * @param d right bottom edge value
     * @param x position between left and right edges [0..1]
     * @param y position between top and bottom edges [0..1]
     * @return interpolated value
     */
    public float interpolate(float a, float b, float c, float d, float x, float y){
        return d * x * y + c * (1 - x) * y + b * x * (1 - y) + a * (1 - x) * (1 - y);
    }

    /**
     * Makes linear interpolation between 2 edge values with given position between them.
     * @param a left edge value
     * @param b right edge value
     * @param x position between left and right edges [0..1]
     * @return interpolated value
     */
    public float interpolate(float a, float b, float x){
        return b * x + a * (1 - x);
    }
}