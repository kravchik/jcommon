package yk.jcommon.utils;

/**
 * User: Yuri
 * Date: 21.10.11
 * Time: 1:59
 */
@Deprecated //use Stopwatch because of bad name
public class Timer {
    private long start;
    public long value;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        value += System.currentTimeMillis() - start;
    }
}
