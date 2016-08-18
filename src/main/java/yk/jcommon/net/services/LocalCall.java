package yk.jcommon.net.services;

import yk.jcommon.collections.YMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static yk.jcommon.collections.YHashMap.hm;

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
        Method method = getMethod(service, command.getMethodName());
        if (method == null) throw new IllegalArgumentException("Could not invoke service: method " + command.getServiceName() + "." + command.getMethodName() + " not found");
        return method;
//        for (Method m : service.getClass().getMethods()) {
//            if (m.getName().equals(command.getMethodName())) {
//                return m;
//            }
//        }
//        throw new IllegalArgumentException("Could not invoke service: method " + command.getServiceName() + "." + command.getMethodName() + " not found");
    }


    //OPTIMIZATIONS

    public static YMap<String, Method> METHODS = hm();
    public static Method getMethod(Object o, String name) {
        String key = o.getClass().toString() + ":" + name;
        if (METHODS.containsKey(key)) return METHODS.get(key);
        Method result = null;
        Method[] methods = o.getClass().getMethods();
        for (int i = 0, methodsLength = methods.length; i < methodsLength; i++) {
            Method m = methods[i];
            if (m.getName().equals(name)) result = m;
        }
        if (result != null) result.setAccessible(true);
        METHODS.put(key, result);
        return result;
    }
}
