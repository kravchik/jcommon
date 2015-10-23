package yk.jcommon.utils;

/**
 * Kravchik Yuri
 * Date: 02.03.2012
 * Time: 11:28 PM
 */
public class StopWatch {
    private long count;
    private long start;

    public StopWatch() {
        start();
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public String restart() {
        String result = stop();
        count = 0;
        start();
        return result;
    }

    public String stop() {
        count += System.currentTimeMillis() - start;
        return toString();
    }

    @Override
    public String toString() {
        return "" + count / 1000f;
    }
}
