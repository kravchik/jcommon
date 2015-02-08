package yk.jcommon.net.oldio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/27/12
 * Time: 11:51 PM
 */
public class SocketServer {
    public SocketTicketFactory ticketFactory;

    public SocketServer(final Commander commander) {
        final Vector<SocketTicket> clients = new Vector<SocketTicket>();
        new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket;
                    int port = 4445;
                    serverSocket = new ServerSocket(port);
                    System.out.println("Start listening " + port);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        System.out.println("Accepting client");
                        SocketTicket st = ticketFactory.createSocketTicket();
                        st.init(socket, commander);
                        clients.add(st);
                    }
                    //serverSocket.close();
                } catch (IOException e) {
                    throw new Error(e);
                }
            }
        }.start();
    }

}
