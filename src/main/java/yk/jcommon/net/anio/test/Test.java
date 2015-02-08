package yk.jcommon.net.anio.test;

import yk.jcommon.net.anio.ASocket;
import yk.jcommon.utils.Threads;

import static yk.jcommon.utils.Threads.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 12/12/13
 * Time: 10:10 PM
 */
public class Test {

    static ASocket client1;
    static ASocket client2;

    public static void main(String[] args) {

        ASocket server = new ASocket(8000, socket -> {
            socket.send("hello!");
            int a = 3;
            socket.onData = data -> {
                System.out.println(a);
            };
        });

        client1 = new ASocket("localhost", 8000, socket -> {
            socket.send("hello1b");
            socket.onData = data -> {
//                client1.clientSocket.send("hello1b");
            };
        });

        client2 = new ASocket("localhost", 8000, socket -> {
            System.out.println("client2 connected");
            socket.onData = data -> {
                System.out.println("client2 received: " + data);
//                client2.clientSocket.send("hello2b");
            };
        });

        client1.clientSocket.send("hello1");
        client2.clientSocket.send("hello2");
        client2.clientSocket.send("hello3");
        client2.clientSocket.send("hello4");
        client2.clientSocket.send("hello5");
        client2.clientSocket.send("hello6");
        client2.clientSocket.send("hello7");


        for (int i = 0; i < 100; i++) {
            sleep(10);
//            System.out.println("\ntick server");
            for (int j = 0; j < 10; j++) server.tick();
//            System.out.println("\ntick client 1");
            for (int j = 0; j < 10; j++) client1.tick();
//            System.out.println("\ntick client 2");
            for (int j = 0; j < 10; j++) client2.tick();
        }


    }


}
