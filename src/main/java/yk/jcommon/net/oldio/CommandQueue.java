package yk.jcommon.net.oldio;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:48 PM
 */
public class CommandQueue {
    private final Deque<Command> queue = new LinkedBlockingDeque<Command>();

    public void submit(Command command) {
        queue.addLast(command);
    }

    //returns null if queue is empty
    public Command poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
