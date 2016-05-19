package yk.jcommon.net.anio.test.m1;

import yk.jcommon.net.services.Service;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 14/05/16
 * Time: 09:41
 */
public interface CommonCs extends Service {
    void clientCall(String s);

    void receiveConfig(String s);
}
