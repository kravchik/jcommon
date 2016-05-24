package yk.jcommon.net.anio;

import yk.jcommon.utils.BadException;

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
 * Date: 12/12/13
 * Time: 11:18 PM
 */
public class ASocket {
    public OnConnection onConnection;
    private Selector selector;
    private ASerializer serializer;
    public AClient clientSocket;
    private ServerSocketChannel serverChannel;
    private SocketChannel clientSocketChannel;

    //server side constructor
    public ASocket(int port, OnConnection onConnection) {
        this(port, new AJavaSerializer(), onConnection);
    }

    public void close() {
        try {
            if (serverChannel != null) serverChannel.close();
            if (clientSocketChannel != null) clientSocketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ASocket(int port, ASerializer serializer, OnConnection onConnection) {
        this.serializer = serializer;
        this.onConnection = onConnection;
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

    //client side constructor
    public ASocket(String address, int port, OnConnection onConnection) {
        this(address, port, new AJavaSerializer(), onConnection);
    }

    public ASocket(String address, int port, ASerializer serializer, OnConnection onConnection) {
        this.serializer = serializer;
        this.onConnection = onConnection;
        clientSocket = new AClient(serializer);
        try {
            clientSocketChannel = SocketChannel.open();
            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.connect(new InetSocketAddress(address, port));
            selector = Selector.open();
            clientSocketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tick() {
        try {
            if (this.selector.selectNow() == 0) return;

            for (Iterator selectedKeys = this.selector.selectedKeys().iterator(); selectedKeys.hasNext(); ) {
                SelectionKey key = (SelectionKey) selectedKeys.next();
                selectedKeys.remove();

                if (!key.isValid()) System.out.println("WARNING: not valid key");
                if (key.isValid() && key.isConnectable()) this.connect(key);
                if (key.isValid() && key.isAcceptable()) this.accept(key);
                if (key.isValid() && key.isReadable()) this.read(key);
                if (key.isValid() && key.isWritable()) this.write(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        try {//TODO handle right
            socketChannel.finishConnect();
        } catch (IOException e) {
            System.out.println(e);
            key.cancel();
            return;
        }
        // Register an interest in writing on this channel
        socketChannel.configureBlocking(false);
        socketChannel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, clientSocket);
        onConnection.call(clientSocket);
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        AClient aclient = new AClient(serializer);
        socketChannel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, aclient);
        onConnection.call(aclient);
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AClient ticket = (AClient) key.attachment();
        try {
            int numRead = socketChannel.read(ticket.inBuffer);//TODO read just as much as needed! Will be faster, don't need move data by .compact()
            ticket.inBuffer.flip();
            ticket.workRead();
            ticket.inBuffer.compact();
            if (numRead == -1) closeChannel(key, ticket);
        } catch (Exception e) {
            closeChannel(key, ticket);

        }
    }

    private void closeChannel(SelectionKey key, AClient ticket) {
        //            e.printStackTrace();
        ticket.closed = true;
        if (ticket.onDisconnect != null) ticket.onDisconnect.call();
        try {
            key.channel().close();
        } catch (IOException e1) {
            e1.printStackTrace();
            BadException.die("error while closing key.channel " + e1);
        }
        key.cancel();
    }

    public void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        AClient ticket = (AClient) key.attachment();

        while (true) {
            if (!ticket.outBuffer.hasRemaining()) {
                ticket.outBuffer.flip();
                ticket.workWrite();
                ticket.outBuffer.flip();
            }
            if (!ticket.outBuffer.hasRemaining()) break;

            int numWrite = socketChannel.write(ticket.outBuffer);
            if (ticket.outBuffer.hasRemaining() || ticket.outBytes.isEmpty()) break;
        }
    }
}
