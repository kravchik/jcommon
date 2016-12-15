package yk.jcommon.utils;

import java.util.List;

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
                    throw new RuntimeException(t);
//                    new ErrorAlert(t);
//                    t.printStackTrace();
//                    System.exit(1);
                }
            }
        }).start();
    }

    public static void thread(long sleepMs, boolean daemon, final Foo executable) {
        Thread thread = new Thread(() -> {
            try {
                while (executable.execute()) {
                    Thread.sleep(sleepMs);
                }
            } catch (Throwable t) {
                throw new RuntimeException(t);
//                    new ErrorAlert(t);
//                    t.printStackTrace();
//                    System.exit(1);
            }
        });
        thread.setDaemon(daemon);
        thread.start();
    }

    //TODO rename
    public interface Foo {
        boolean execute() throws Exception;
    }

    public static void tick(Tickable t, long dt, Object... other) {
        final long dts[] = new long[other.length / 2 + 1];
        final long lastTick[] = new long[dts.length];
        final List<Tickable> tickable = Util.list(t);
        dts[0] = dt;
        lastTick[0] = System.currentTimeMillis();

        for (int i = 0; i < other.length; i += 2) {
            tickable.add((Tickable) other[i]);
            dts[i / 2 + 1] = ((Number) other[i + 1]).longValue();
            lastTick[i / 2 + 1] = System.currentTimeMillis();
        }

        Thread result = new Thread(new Runnable() {
            boolean exit;

            @Override
            public void run() {
                while (!exit) {
                    long curTime = System.currentTimeMillis();
                    for (int i = 0; i < dts.length; i++) {
                        if (tickable.get(i).exit) exit = true;//TODO beautify
                        if (lastTick[i] + dts[i] <= curTime) {
                            try {
                                tickable.get(i).tick((curTime - lastTick[i]) / 1000f);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                                //new ErrorAlert(e);
//                                e.printStackTrace();
                            }
                            lastTick[i] = curTime;
                        }
                    }
                    //System.gc();//forgotten rocket base in siberia
                    sleep(10);
                }
            }
        });
        result.start();
    }

    public static void tick(long dt, boolean daemon, Tickable t) {
        final long[] lastTick = {System.currentTimeMillis()};

        Thread result = new Thread(new Runnable() {
            boolean exit;

            @Override
            public void run() {
                while (!exit) {
                    long curTime = System.currentTimeMillis();
                    if (t.exit) exit = true;//TODO beautify
                    if (lastTick[0] + dt <= curTime) {
                        try {
                            t.tick((curTime - lastTick[0]) / 1000f);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        lastTick[0] = curTime;
                    }
                    sleep(dt / 2);
                }
            }
        });
        result.setDaemon(daemon);
        result.start();
    }

    public static abstract class Tickable {
        public boolean exit;

        abstract public void tick(float dt) throws Exception;
    }

    //TODO

//    public static void tick20(Function<Float, Boolean> f) {
//        Threads.tick(new Threads.Tickable() {
//                         @Override
//                         public void tick(float dt) throws Exception {
//                             exit = !f.apply(dt);
//                         }
//                     }, 20
//        );
//    }

}
