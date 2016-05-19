package yk.jcommon.net.anio.test.m1;

import yk.jcommon.net.anio.ASocket;
import yk.jcommon.net.services.Command;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 14/05/16
 * Time: 09:37
 */
public class ServerM1 {

    public static void main(String[] args) {

        ASocket server = new ASocket(7777, socket -> {
            System.out.println("server on client connected");
            RemoteClient rc = new RemoteClient();
            rc.cs = new RemoteClientServices(socket::send);

            rc.ss = new ServerServices();
            rc.ss.serverService = new ServerServiceImpl();
            socket.onData = data -> rc.ss.call((Command) data);

            rc.cs.commonCs.receiveConfig("config strings");
        });

        ASocket client = new ASocket("", 7777, socket -> {
            System.out.println("client on connected");
            RemoteServer rs = new RemoteServer();
            rs.ss = new RemoteServerServices(socket::send);

            rs.cs = new LocalClientServices();
            rs.cs.commonCs = new CommonCsImpl();
            socket.onData = data -> {
                System.out.println("client: command received: " + data);
                rs.cs.call((Command) data);
            };
        });



        server.tick();
        client.tick();
        server.tick();
        client.tick();
    }


}
