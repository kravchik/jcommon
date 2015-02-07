package yk.jcommon.collections;

/**
 * Kravchik Yuri
 * Date: 1/18/12
 * Time: 9:09 PM
 */
public class Array2<T> {
    private T[] t;
    public final int width;
    public final int height;

    public Array2(int width, int height) {
        this.width = width;
        this.height = height;
        t = (T[]) new Object[width * height];

    }

    public Array2(Array2<T> other) {
        this(other.width, other.height);
        for (int x = 0; x < width; x++) for (int y = 0; y < height; y++) set(x, y, other.get(x, y));
    }

    public Array2(int width, int height, T defaultValue) {
        this(width, height);
        for (int i = 0, tLength = t.length; i < tLength; i++) {
            t[i] = defaultValue;
        }
    }

    private int calcIndex(int x, int y) {
        return x + y * width;
    }

    private boolean isInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    private int getIndex(int x, int y) {
        if (!isInRange(x, y)) throw new Error(x + " " + y + "(of " + width + " " + height + ")");
        return calcIndex(x, y);

    }

    public T get(int x, int y) {
        return t[getIndex(x, y)];
    }

    public T set(int x, int y, T value) {
        int i = getIndex(x, y);
        T old = t[i];
        t[i] = value;
        return old;
    }

    public static void main(String[] args) {
        Array2<Integer> ii = new Array2<Integer>(10, 10);
        ii.set(5, 5, 10);
        System.out.println(ii.get(5, 5));
    }
}
