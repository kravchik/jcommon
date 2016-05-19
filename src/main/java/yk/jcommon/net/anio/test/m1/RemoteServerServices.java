package yk.jcommon.net.anio.test.m1;

import yk.jcommon.net.services.CommandSender;
import yk.jcommon.net.services.RemoteCall;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 14/05/16
 * Time: 09:42
 */
public class RemoteServerServices extends RemoteCall {
    public ServerService serverService;

    public RemoteServerServices(CommandSender sender) {
        super(sender);
    }
}
