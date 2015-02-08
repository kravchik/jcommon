package yk.jcommon.utils;

public class Rgb {

    public static int get(int value, int i) {
        return (value >> (i * 8)) & 0xFF;
    }

    public static int packRgb(int r, int g, int b) {
        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }

    public static int packRgb(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int getR(int data) {
        return (data >>> 16)&0xFF;
    }

    public static int getG(int data) {
        return (data >>> 8)&0xFF;
    }

    public static int getB(int data) {
        return data&0xFF;
    }

    public static int getA(int data) {
        return (data >>> 24)&0xFF;
    }

    public static int bw(int value) {
        int res = (getR(value) + getG(value) + getB(value)) / 3;
        return packRgb(res, res, res);
    }

    public static float brightness(int value) {
        return (getR(value) + getG(value) + getB(value)) / 3f / 255f;
    }

    public static int merge(int c1, int c2, float proportion) {
        int r = (int) Math.min(255, getR(c1) * proportion + getR(c2) * (1 - proportion));
        int g = (int) Math.min(255, getG(c1) * proportion + getG(c2) * (1 - proportion));
        int b = (int) Math.min(255, getB(c1) * proportion + getB(c2) * (1 - proportion));
        int a = (int) Math.min(255, getA(c1) * proportion + getA(c2) * (1 - proportion));
        return packRgb(r, g, b, a);
    }
}
