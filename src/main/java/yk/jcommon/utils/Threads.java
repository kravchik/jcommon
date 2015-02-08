package yk.jcommon.utils;

import yk.jcommon.utils.ErrorAlert;

import java.util.List;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/15/13
 * Time: 10:06 PM
 */
public class Threads {

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void thread(Foo executable) {
        thread(executable, 100);
    }

    //must return event dispatcher object, so I can listen for Exception?
    //pass this dispatcher to execute as argument, so I can dispatch new events on demand?
    //also I can react on signals transferred to this object
    //Actor model?
    public static void thread(final Foo executable, final long sleepMs) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (executable.execute()) {
                        Thread.sleep(sleepMs);
                    }
                } catch (Throwable t) {
                    new ErrorAlert(t);
                    t.printStackTrace();
//                    System.exit(1);
                }
            }
        }).start();
    }

    //TODO rename
    public static interface Foo {
        public boolean execute() throws Exception;
    }

    public static void tick(Tickable t, long dt, Object... other) {
        final long dts[] = new long[other.length / 2 + 1];
        final long lastTick[] = new long[dts.length];
        final List<Tickable> tickable = Util.list(t);
        dts[0] = dt;
        lastTick[0] = System.currentTimeMillis();

        for (int i = 0; i < other.length; i += 2) {
            tickable.add((Tickable) other[i]);
            dts[i/2+1] = ((Number) other[i+1]).longValue();
            lastTick[i/2+1] = System.currentTimeMillis();
        }

        new Thread(new Runnable() {
            boolean exit;
            @Override
            public void run() {
                while(!exit) {
                    long curTime = System.currentTimeMillis();
                    for (int i = 0; i < dts.length; i++) {
                        if (tickable.get(i).exit) exit = true;//TODO beautify
                        if (lastTick[i] + dts[i] <= curTime) {
                            try {
                                tickable.get(i).tick((curTime - lastTick[i]) / 1000f);
                            } catch (Exception e) {
                                //new ErrorAlert(e);
                                e.printStackTrace();
                            }
                            lastTick[i] = curTime;
                        }
                    }
                    //System.gc();//forgotten rocket base in siberia
                    sleep(10);
                }
            }
        }).start();

    }

    public static abstract class Tickable {
        public boolean exit;
        abstract public void tick(float dt) throws Exception;
    }
}
