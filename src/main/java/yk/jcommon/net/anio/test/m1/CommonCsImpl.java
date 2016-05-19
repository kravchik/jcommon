package yk.jcommon.net.anio.test.m1;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 14/05/16
 * Time: 09:42
 */
public class CommonCsImpl implements CommonCs {

    @Override
    public void clientCall(String s) {
        System.out.println("Client called with " + s);
    }

    @Override
    public void receiveConfig(String s) {
        System.out.println("config received");

    }
}
