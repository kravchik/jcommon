package yk.jcommon.net.anio;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;

import static yk.jcommon.collections.YArrayList.al;

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
    public BiConsumer<Object, Integer> onStats;

    public boolean closed;

    public void send(Object data) {
        byte[] bytes = serializer.serialize(data);
        if (onStats != null) onStats.accept(data, bytes.length);
        outBytes.add(bytes);
    }

    final ASerializer serializer;
    public List<byte[]> outBytes = al();
    ByteBuffer outBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    List<Object> inCommands = al();
    int nextInSize = -1;
    ByteBuffer inBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    {
        outBuffer.flip();
    }
    void workWrite() {
        outBuffer.clear();
        if (outBytes.isEmpty()) return;
        byte[] bytes = outBytes.remove(0);
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


