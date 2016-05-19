package yk.jcommon.net.anio.test.m1;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 14/05/16
 * Time: 09:42
 */
public class ServerServiceImpl implements ServerService {

    @Override
    public void serverCall(String s) {
        System.out.println("Server called with " + s);
    }
}
