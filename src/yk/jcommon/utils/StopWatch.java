package yk.jcommon.utils;

/**
 * Kravchik Yuri
 * Date: 02.03.2012
 * Time: 11:28 PM
 */
public class StopWatch {
    private long count;
    private long start;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        count += System.currentTimeMillis() - start;
    }

    @Override
    public String toString() {
        return "" + count / 1000;
    }
}
