package yk.jcommon.utils;

import java.util.List;

import static yk.jcommon.utils.Util.list;
import static yk.jcommon.utils.Util.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/15/13
 * Time: 10:50 PM
 */
public class StaticRunner {
    public static final List<Threads.Foo> runnables = list();
    public static long sleepTime = 10;

    public static void spawn(Threads.Foo r) {
        synchronized (runnables) {
            runnables.add(r);
        }
    }

    static {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //noinspection InfiniteLoopStatement
                    while (true) {
                        synchronized (runnables) {
                            for (int i = 0; i < runnables.size(); i++) {
                                Threads.Foo runnable = runnables.get(i);
                                if (!runnable.execute()) {
                                    runnables.remove(i);
                                    i--;
                                    System.out.println(i + " removed");
                                }
                            }
                        }
                        sleep(sleepTime);
                    }
                } catch (Throwable t) {
                    System.out.println(t);
                }
            }
        });
        thread.setDaemon(false);//TODO decide
        thread.start();
    }

    public static void main(String[] args) {
        spawn(new Threads.Foo() {
            int i = 0;

            @Override
            public boolean execute() {
                System.out.println("t1: " + i);
                return i++ < 5;
            }
        });
        spawn(new Threads.Foo() {
            int i = 0;

            @Override
            public boolean execute() {
                System.out.println("t2: " + i);
                return i++ < 10;
            }
        });
    }

}
