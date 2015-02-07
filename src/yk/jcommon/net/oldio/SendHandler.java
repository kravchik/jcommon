package yk.jcommon.net.oldio;

import yk.jcommon.utils.Util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:08 AM
 */
public class SendHandler implements InvocationHandler {

    public SocketWriter socketWriter;
    public SocketTicket ticket;

    public SendHandler(SocketWriter socketWriter, SocketTicket ticket) {
        this.socketWriter = socketWriter;
        this.ticket = ticket;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (method.getName().equals("toString")) return null;
        Command command = new Command(ticket, method.getDeclaringClass().getSimpleName(), method.getName(), objects == null ? list() : list(objects));
        //System.out.println("sending " + command);
        if (ticket.isOnline) {
            try {
                socketWriter.sendCommand(command);
            } catch (Exception e) {
                ticket.isOnline = false;
                System.out.println(command);
                System.out.println("error sending command " + Util.append(Arrays.asList(e.getStackTrace()), "\n"));
            }

        }
        return null;
    }
}
