package yk.jcommon.net.anio;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/12/13
 * Time: 10:17 PM
 */
public interface ASerializer {
    public Object deserialize(byte[] data);
    public byte[] serialize(Object o);
}
