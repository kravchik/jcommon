package yk.jcommon.net.services;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:48 PM
 */
public class Command implements Serializable {
    public String serviceName;
    public String methodName;
    public List params;

    transient public Field service;
    transient public Field method;

    public Command() {
    }

    public Command(String serviceName, String methodName, List params) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.params = params;
    }

    public String getServiceName() {
        return Character.toUpperCase(serviceName.charAt(0)) + serviceName.substring(1);
    }
    public String getMethodName() {
        return methodName;
    }
    public List getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "Command{" +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + params +
                '}';
    }
}
