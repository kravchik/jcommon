package yk.jcommon.utils;

/**
 * Poolable
 *
 * @author Yuri Kravchik Created 09.01.2009
 */
public class Poolable<T extends Poolable> {
    protected Pool<T> pool;
    int poolIndex;
}
