package yk.jcommon.net.oldio;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 9/28/12
 * Time: 12:20 PM
 */
public class TestServer {

    public static void main(String[] args) {
        final Commander commander = new Commander();
        commander.addService(new TestServiceImpl());
        SocketServer socketServer = new SocketServer(commander);
        socketServer.ticketFactory = new SocketTicketFactory() {
            @Override
            public SocketTicket createSocketTicket() {
                return new TestSocketTicket();
            }
        };

        new Thread() {
            @Override
            public void run() {
                while(true) {
                    commander.executeCommandsQueue();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new Error(e);
                    }
                }
            }
        }.start();


    }

}
