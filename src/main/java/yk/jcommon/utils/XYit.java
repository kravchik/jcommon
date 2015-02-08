package yk.jcommon.utils;

import java.awt.image.BufferedImage;
import java.util.Iterator;

/**
 * Kravchik Yuri
 * Date: 1/17/12
 * Time: 10:58 PM
 */
public class XYit implements Iterable<XYit>, Iterator<XYit> {
    //WARNING IT IS SLOWER THEN TWO FORS   (0.016 vs 0.013)
    public final int w, h;

    public int x;
    public int y;

    public XYit(BufferedImage im) {
        w = im.getWidth();
        h = im.getHeight();
    }

    public XYit(int width, int height) {
        w = width;
        h = height;
    }

    public static XYit im(BufferedImage im) {
        return new XYit(im);
    }

    public static XYit wh(int width, int height) {
        return new XYit(width, height);
    }

    public Iterator<XYit> iterator() {
        x = -1;
        y = 0;
        return this;
    }

    public boolean hasNext() {
        return x != w - 1 || y != h - 1;
    }

    public XYit next() {
        x++;
        if (x == w) {
            x = 0;
            y++;
        }
        return this;
    }

    public void remove() {
        throw new RuntimeException("not supported");
    }

    @Override
    public String toString() {
        return "XYit{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
//    @Override
//    public void forEach(Block<? super XYit> block) {
//    }
}
