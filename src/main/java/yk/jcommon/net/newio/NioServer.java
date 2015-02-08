package yk.jcommon.net.newio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 5/8/13
 * Time: 1:22 PM
 */
public class NioServer implements Runnable {
    private Selector selector;
    private AcceptCallback acceptCallback;

    public NioServer(String hostAddress, int port, AcceptCallback acceptCallback) {
        ServerSocketChannel serverChannel = null;
        this.acceptCallback = acceptCallback;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
                this.selector.select();

                for (Iterator selectedKeys = this.selector.selectedKeys().iterator(); selectedKeys.hasNext(); ) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) this.accept(key);
                    else if (key.isReadable()) this.read(key);
                    else if (key.isWritable()) this.write(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        NioTicket newTicket = new NioTicket();
        socketChannel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE).attach(newTicket);
        acceptCallback.onAccept(newTicket);
    }

    private void read(SelectionKey key) {
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            NioTicket ticket = (NioTicket) key.attachment();
            int numRead = socketChannel.read(ticket.readBuffer);
            ticket.workRead();

            if (numRead == -1) {
                ticket.offline = true;
                key.channel().close();
                key.cancel();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        NioTicket ticket = (NioTicket) key.attachment();

        while (true) {
            ticket.workWrite();
            socketChannel.write(ticket.outBuffer);
            if (ticket.outBuffer.hasRemaining() || ticket.commands.isEmpty()) break;
        }
    }

}