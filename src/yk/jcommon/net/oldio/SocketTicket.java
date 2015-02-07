package yk.jcommon.net.oldio;

import yk.jcommon.net.services.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:26 PM
 */
abstract public class SocketTicket {
    //TODO remember about                   NIO

    public boolean isOnline = true;
    public SocketWriter socketWriter;//TODO remove field, and remove class (inline logic here)
    public SocketReader socketReader;//TODO remove and inline logic in THIS CLASS

    public void init(Socket socket, Commander commander) {
        socketWriter = new SocketWriter(socket);
        socketReader = new SocketReader(socket, this, commander);
        socketReader.start();

        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (Service.class.isAssignableFrom(field.getType())) {
                        field.set(this, Proxy.newProxyInstance(field.getType().getClassLoader(),
                                new Class<?>[]{field.getType()},
                                new SendHandler(socketWriter, this)));
                }
            }

        } catch (Exception e) {
            throw new Error(e);
        }

    }
}
