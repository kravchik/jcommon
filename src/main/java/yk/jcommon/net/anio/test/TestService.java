package yk.jcommon.net.anio.test;

import yk.jcommon.net.anio.ASocket;
import yk.jcommon.net.services.Command;
import yk.jcommon.net.services.CommandSender;
import yk.jcommon.net.services.LocalCall;
import yk.jcommon.net.services.RemoteCall;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/16/13
 * Time: 11:03 PM
 */
public class TestService {

    public static void main(String[] args) {

        ASocket server = new ASocket(8000, socket -> {
            RemoteClientServices r = new RemoteClientServices(socket::send);
            ServerServices s = new ServerServices();
            r.clientService.receiveConfig("config strings");
            socket.onData = data -> {
                s.call((Command) data);
            };

        });

        ASocket client = new ASocket("", 8000, socket -> {
            RemoteServerServices r = new RemoteServerServices(socket::send);
            ClientServices s = new ClientServices(r);

            socket.onData = data -> {
                s.call((Command) data);

            };
        });


    }


    public interface ClientService {
        void clientCall(String s);
        void receiveConfig(String s);
    }

    public interface ServerService {
        void serverCall(String s);
    }

    public static class ClientServiceImpl implements ClientService {
        RemoteServerServices r;

        public ClientServiceImpl(RemoteServerServices r) {
            this.r = r;
        }

        @Override
        public void clientCall(String s) {
            System.out.println("Client called with " + s);
        }

        @Override
        public void receiveConfig(String s) {
            System.out.println("config received");

        }
    }

    public static class ServerServiceImpl implements ServerService {

        @Override
        public void serverCall(String s) {
            System.out.println("Server called with " + s);
        }
    }

    public static class ClientServices extends LocalCall {
        ClientService clientService;

        public ClientServices(RemoteServerServices r) {
            clientService = new ClientServiceImpl(r);
        }

    }

    public static class ServerServices extends LocalCall {
        ServerService serverService;
    }

    public static class RemoteClientServices extends RemoteCall {
        ClientService clientService;
        public RemoteClientServices(CommandSender sender) {
            super(sender);
        }
    }

    public static class RemoteServerServices extends RemoteCall {
        ServerService serverService;
        public RemoteServerServices(CommandSender sender) {
            super(sender);
        }
    }
}
