package yk.jcommon.net.oldio;

import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:56 PM
 */
public class TestClient {

    public static void main(String[] args) throws IOException {
        Commander commander = new Commander();
        commander.addService(new TestServiceImpl());
        TestSocketTicket ticket = new TestSocketTicket();
        ticket.init(new Socket("localhost", 4445), commander);
        ticket.testServiceOut.mul2(4);
    }

}
