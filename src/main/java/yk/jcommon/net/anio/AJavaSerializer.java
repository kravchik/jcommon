package yk.jcommon.net.anio;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/12/13
 * Time: 10:23 PM
 */
public class AJavaSerializer implements ASerializer {
    @Override
    public Object deserialize(byte[] bytes) {
        try {
            return new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] serialize(Object o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(baos).writeObject(o);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }
}
