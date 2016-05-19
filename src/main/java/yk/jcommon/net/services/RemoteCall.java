package yk.jcommon.net.services;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 11/3/13
 * Time: 6:36 PM
 */
public class RemoteCall {
    public RemoteCall(final CommandSender sender) {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (Service.class.isAssignableFrom(field.getType())) {
                    field.set(this, Proxy.newProxyInstance(field.getType().getClassLoader(),
                            new Class<?>[]{field.getType()},
                            (proxy, method, args) -> {
                                if (method.getName().equals("toString")) return null;
                                Command command = new Command(method.getDeclaringClass().getSimpleName(), method.getName(), args == null ? list() : list(args));
//                                System.out.println("sending " + command);
                                try {
                                    sender.send(command);
                                } catch (Exception e) {
                                    throw new Error(e);
                                }
                                return null;
                            }));
                }
            }
        } catch (Exception e) {
            throw new Error(e);
        }

    }
}
