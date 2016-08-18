package yk.jcommon.utils;

import yk.jcommon.collections.YList;
import yk.jcommon.fastgeom.FloatRange;
import yk.jcommon.fastgeom.IntRange;
import yk.jcommon.fastgeom.Vec2f;
import yk.jcommon.fastgeom.Vec3f;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static yk.jcommon.collections.YArrayList.al;
import static yk.jcommon.collections.YArrayList.toYList;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 4/28/14
 * Time: 10:53 PM
 */
public class Rnd {
    @Deprecated//only for fast tests and prototypes
    public static Rnd instance = new Rnd();

    public Random rnd = new Random();

    public Rnd() {
    }

    public Rnd(long seed) {
        setSeed(seed);
    }

    public <T> T from(List<T> f) {
        return f.get(rnd.nextInt(f.size()));
    }

    public <T> T fromList(List<T> list) {
        return from(list);
    }

    public <T> T from(T[] array) {
        return array[nextInt(array.length)];
    }

    public <T> YList<T> from(YList<T> list, int count) {
        return from(list, count, false);
    }

    public <T> YList<T> from(YList<T> list, int count, boolean duplicates) {
        if (list.isEmpty() || !duplicates && count >= list.size()) {
            return list;
        }
        YList<T> from = duplicates ? list : toYList(list);
        YList<T> to = al();
        while (to.size() < count) {
            T t = from(from);
            if (!duplicates) {
                from.remove(t);
            }
            to.add(t);
        }
        return to;
    }

    public <T> YList<T> shuffledCopy(Collection<T> list) {
        YList<T> result = toYList(list);
        shuffle(result);
        return result;
    }

    public <T> void shuffle(List<T> result) {
        Collections.shuffle(result, rnd);
    }

    private long seed;
    public void setSeed(long seed) {
        this.seed = seed;
        rnd.setSeed(seed);
    }

    public long getSeed() {
        return seed;
    }

    public float nextFloat(float min, float max) {
        return (rnd.nextFloat() * (max - min)) + min;
    }

    public Vec2f nextVec2f() {
        return new Vec2f(nextFloat(), nextFloat());
    }

    public Vec2f nextVec2f(float r) {
        return new Vec2f(nextFloat() * r - r / 2, nextFloat() * r - r / 2);
    }

    public Vec3f nextVec3f() {
        return new Vec3f(nextFloat(), nextFloat(), nextFloat());
    }

    public Vec2f nextVec2f(float l, float r, float t, float b) {
        return new Vec2f(nextFloat(l, t), nextFloat(b, t));
    }

    public boolean nextBoolean() {
        return rnd.nextBoolean();
    }

    public boolean nextBoolean(double probability) {
        return probability >= 1 || probability > 0 && nextFloat() <= probability;
    }

    public boolean nextBoolean(int probability) {
        return nextBoolean(probability / 100.);
    }

    public int nextInt(int lesserThan) {
        return rnd.nextInt(lesserThan);
    }

    public int from(int min, int max) {
        return max > min ? min + rnd.nextInt(max - min + 1) : min;
    }

    public float from(float min, float max) {
        return nextFloat() * (max - min) + min;
    }

    public int from(IntRange r) {
        return from(r.min, r.max);
    }

    public float nextFloat() {
        return rnd.nextFloat();
    }

    public float from(FloatRange r) {
        return nextFloat(r.min, r.max);
    }

    public float from(FloatRange r, float minLimit, float maxLimit) {
        return Math.min(minLimit, Math.max(maxLimit, nextFloat(r.min, r.max)));
    }
}
