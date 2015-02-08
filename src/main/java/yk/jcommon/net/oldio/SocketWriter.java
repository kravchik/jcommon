package yk.jcommon.net.oldio;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:01 AM
 */
public class SocketWriter {
    public DataOutputStream dos;
    //public EBONDeserializer des = new EBONDeserializer();
    //public EBONSerializer ser = new EBONSerializer();
    OutputStream os;

    public SocketWriter(Socket socket) {
        try {
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void sendCommand(Command command) throws Exception {
//        byte[] serialized = new EBONSerializer().serialize(command);
//        dos.writeInt(serialized.length);
//        dos.write(serialized);
        new ObjectOutputStream(os).writeObject(command);
    }

}
