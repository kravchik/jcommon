package yk.jcommon.net.oldio;

import java.io.*;
import java.net.Socket;

public class SocketReader extends Thread {
    private Socket socket = null;
    private Commander commander;
    private SocketTicket ticket;
    InputStream is;

    public SocketReader(Socket socket, SocketTicket ticket, Commander commander) {
        super("SocketReader");
        this.socket = socket;
        this.commander = commander;
        this.ticket = ticket;
    }

    public void run() {
        try {
//            byte[] buf = new byte[5000000];
            is = socket.getInputStream();
//            DataInputStream dis = new DataInputStream(is);
            //DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            //EBONDeserializer des = new EBONDeserializer();

            while(true) {
//                int size = dis.readInt();
//                int actual = 0;
//                while (actual != size) actual += dis.read(buf, actual, size - actual);
//                Command command = (Command) new EBONDeserializer().deserialize(buf);
                Command command = null;
                try {
                    command = (Command) new ObjectInputStream(is).readObject();
                } catch (ClassNotFoundException e) {
                    throw new Error(e);
                }
                command.ticket = ticket;
                commander.submitCommand(command);
                //dos.write(ser.serialize(result));
            }
            //socket.close();
        } catch (IOException e) {
            this.ticket.isOnline = false;
            e.printStackTrace();
        }
    }
}