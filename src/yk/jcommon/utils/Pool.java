package yk.jcommon.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool
 *
 * @author Yuri Kravchik Created 09.01.2009
 */
public class Pool<T extends Poolable> {
    public List<T> pool = new ArrayList<T>();
    public int firstFree = 0;//TODO hide behind iterator

    private void exchange(final int a, final int b) {
        final T temp = pool.get(a);
        pool.set(a, pool.get(b));
        pool.set(b, temp);
        pool.get(a).poolIndex = a;
        pool.get(b).poolIndex = b;
    }

    public T borrow() {
        if (firstFree >= pool.size()) {
            throw new Error("Pool dryed out! " + toString());
        }
        return pool.get(firstFree++);
    }

    public int capacity() {
        return pool.size();
    }

    public void fillPool(final T item) {
        item.poolIndex = pool.size();
        item.pool = this;
        pool.add(item);
    }

    public T getActive(final int index) {
        return pool.get(index);
    }

    public int getActiveSize() {
        return firstFree;
    }

    private void returnByIndex(final int index) {
        if (index >= firstFree) {
            throw new Error("Trying to return item with index " + index + "(" + toString() + ")");
        }
        exchange(index, --firstFree);
    }

    public void returnItem(final T item) {
        returnByIndex(item.poolIndex);
    }

    @Override
    public String toString() {
        return "pool size: " + pool.size() + " first free: " + firstFree;
    }

//    public void forEach(Block<? super T> block) {
//        for (int i = 0; i < firstFree; i++) block.accept(pool.get(i));
//    }
}

