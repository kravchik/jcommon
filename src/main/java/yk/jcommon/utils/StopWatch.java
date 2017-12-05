package yk.jcommon.utils;

/**
 * Kravchik Yuri
 * Date: 02.03.2012
 * Time: 11:28 PM
 */
public class StopWatch {
    private long lastCount;
    private long start;

    public StopWatch() {
        start();
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        lastCount = System.currentTimeMillis() - start;
    }

    public void restart() {
        long cur = System.currentTimeMillis();
        lastCount = cur - start;
        start = cur;
    }

    public long getLastCount() {
        return lastCount;
    }

    public String getCurrentTime() {
        return "" + (System.currentTimeMillis() - start) / 1000f + "s";
    }

    @Override
    public String toString() {
        return "" + lastCount / 1000f + "s";
    }

}
