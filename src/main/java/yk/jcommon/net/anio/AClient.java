package yk.jcommon.net.anio;

import java.nio.ByteBuffer;
import java.util.List;

import static yk.jcommon.utils.Util.list;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/12/13
 * Time: 10:10 PM
 */
public class AClient {
    public static final int BUFFER_SIZE = 1000000;
    public OnData onData;
    public OnDisconnect onDisconnect;

    public boolean closed;

    public void send(Object data) {
        outCommands.add(data);
    }

    final ASerializer serializer;
    public List<Object> outCommands = list();
    ByteBuffer outBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    List<Object> inCommands = list();
    int nextInSize = -1;
    ByteBuffer inBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    {
        outBuffer.flip();
    }
    void workWrite() {
        outBuffer.clear();
        if (outCommands.isEmpty()) {
            return;
        }
        Object c = outCommands.remove(0);
        byte[] bytes = serializer.serialize(c);//TODO serialize on send
        System.out.println("command: " + c + " serialized: " + bytes.length);
        outBuffer.put(ByteBuffer.allocate(4).putInt(bytes.length).array());
        outBuffer.put(bytes);
    }

    private byte[] bytes = new byte[BUFFER_SIZE];
    void workRead() {
        while (true) {
            if (nextInSize == -1) if (inBuffer.remaining() >= 4) nextInSize = inBuffer.getInt();
            if (nextInSize > BUFFER_SIZE) throw new RuntimeException("in buffer size (" + BUFFER_SIZE + ") too low for data of size " + nextInSize);
            if (nextInSize != -1) {
                if (inBuffer.remaining() >= nextInSize) {
                    inBuffer.get(bytes, 0, nextInSize);
                    nextInSize = -1;
                    inCommands.add(serializer.deserialize(bytes));
                    continue;
                }
            }
            break;
        }

        for (Object o : inCommands) onData.call(o);
        inCommands.clear();
    }

    //server side constructor
    AClient(ASerializer serializer) {
        this.serializer = serializer;
    }
}


