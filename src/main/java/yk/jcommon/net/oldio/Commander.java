package yk.jcommon.net.oldio;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:47 PM
 */
public class Commander {
    private CommandQueue commandQueue = new CommandQueue();
    private Map<String, Object> services = new HashMap<String, Object>();

    public void submitCommand(Command command) {
        if (command == null) return;
        commandQueue.submit(command);
    }

    public void executeCommandsQueue() {
        Command command;
        while (null != (command = commandQueue.poll())) {
            workCommand(command);
        }
    }

    public void addService(Object impl) {
        String name = impl.getClass().getSimpleName();
        name = name.substring(0, name.length() - 4);
        services.put(name, impl);
    }

    private void workCommand(Command command) {
        try {
            Object service = lookup(command.getServiceName());

            Field field = service.getClass().getDeclaredField("ticket");
            field.setAccessible(true);
            field.set(service, command.ticket);

            Method method = findMethod(service, command);
//            currentTicket = command.getTicket();
            Object[] params = new Object[command.getParams().size()];
            for (int i = 0; i < command.getParams().size(); i++) {
                params[i] = command.getParams().get(i);
            }
            invokeCommand(service, method, params);
        } catch (InvocationTargetException t) {
            t.getCause().printStackTrace();
            //command.setResult(t.getTargetException());//t contains thrown exception as cause
        } catch (Throwable e) {
            System.out.println(e);
            //command.setResult(e);
        } finally {
//            currentTicket = null;
        }
    }

    protected void invokeCommand(Object service, Method method, Object[] params) throws IllegalAccessException, InvocationTargetException {
        method.invoke(service, params);
    }

    private Object lookup(String serviceName) {
        Object service = services.get(serviceName);
        if (service == null) {
            throw new Error("Service '" + serviceName + "' not exists.");
        }
        return service;
    }

    private Method findMethod(Object service, Command command) throws IllegalArgumentException {
        for (Method m : service.getClass().getMethods()) {
            if (m.getName().equals(command.getMethodName())) {
                return m;
            }
        }
        throw new IllegalArgumentException("Could not invoke service: method " + command.getServiceName() + "." + command.getMethodName() + " not found");
    }
}
