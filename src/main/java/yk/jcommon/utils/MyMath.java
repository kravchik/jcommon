package yk.jcommon.utils;

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
}
