package yk.jcommon.net.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:47 PM
 */
public class LocalCall {

    public void call(Command command) {
        try {
            Object service = lookup(command.getServiceName());
            Method method = findMethod(service, command);
            method.setAccessible(true);
            Object[] params = new Object[command.getParams().size()];
            for (int i = 0; i < command.getParams().size(); i++) {
                params[i] = command.getParams().get(i);//some conversions possible
            }
            method.invoke(service, params);
        } catch (InvocationTargetException t) {
            t.getCause().printStackTrace();
            //command.setResult(t.getTargetException());//t contains thrown exception as cause
        } catch (Throwable e) {
            e.printStackTrace();
            //command.setResult(e);
        }
    }

    private Object lookup(String serviceName) {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().toLowerCase().equals(serviceName.toLowerCase())) try {
                return field.get(this);
            } catch (Exception e) {
                RuntimeException re = new RuntimeException("Looking up service " + serviceName + " | " + e.getMessage());
                re.setStackTrace(e.getStackTrace());
                throw re;
            }
        }
        throw new Error("Service '" + serviceName + "' not exists.");
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
