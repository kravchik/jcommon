package yk.jcommon.net.newio;

import yk.jcommon.net.services.Command;
import yk.jcommon.net.services.CommandSender;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.List;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 11/3/13
 * Time: 4:00 PM
 */
public class NioTicket implements CommandSender {
    public boolean offline;
    public List<Command> commandsFromRemote = list();

    public List<byte[]> commands = list();
    public ByteBuffer outBuffer;

    public int nextCommandSize;
    public ByteBuffer readBuffer = ByteBuffer.allocate(8192);

    public void workWrite() {
        if (commands.isEmpty()) return;
        if (outBuffer.hasRemaining()) return;
        outBuffer.clear();
        byte[] bytes = commands.remove(0);
        outBuffer.put(ByteBuffer.allocate(4).putInt(bytes.length).array());
        outBuffer.put(bytes);
        outBuffer.flip();
    }

    public void workRead() {
        try {
            readBuffer.flip();
            while (true) {

                if (nextCommandSize == -1) if (readBuffer.remaining() >= 4) nextCommandSize = readBuffer.getInt();
                if (nextCommandSize != -1) {
                    if (readBuffer.remaining() >= nextCommandSize) {
                        byte[] bytes = new byte[nextCommandSize];
                        readBuffer.get(bytes);
                        nextCommandSize = -1;
                        commandsFromRemote.add((Command) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject());
                        continue;
                    }
                }
                break;
            }
            readBuffer.flip();
            readBuffer.compact();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Command command) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new ObjectOutputStream(baos).writeObject(command);
            commands.add(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
