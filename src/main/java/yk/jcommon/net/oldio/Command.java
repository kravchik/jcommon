package yk.jcommon.net.oldio;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:48 PM
 */
public class Command implements Serializable {
//    @Skip
    transient public SocketTicket ticket;
    private String serviceName;
    private String methodName;
    private List params;


    public Command() {
    }

    public Command(SocketTicket ticket, String serviceName, String methodName, List params) {
        this.ticket = ticket;
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
    public SocketTicket getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return "Command{" +
                "ticket=" + ticket +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + params +
                '}';
    }
}
