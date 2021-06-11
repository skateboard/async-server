package me.brennan.socket;

import me.brennan.socket.listener.SocketListener;
import me.brennan.socket.threads.ClientConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class SocketServer implements Runnable {
    private final List<ClientConnection> connections = new LinkedList<>();

    private final AsynchronousServerSocketChannel server;

    private SocketListener socketListener;

    public SocketServer(int port) throws Exception {
        this.server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress("0.0.0.0", port));
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (server.isOpen()) {
                final Future<AsynchronousSocketChannel> acceptCon = server.accept();
                final AsynchronousSocketChannel client = acceptCon.get();
                if ((client!= null) && (client.isOpen()))
                    new Thread(new ClientConnection(this, client)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(server.isOpen()) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Closed Socket Server");
            }
        }
    }

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    public SocketListener getSocketListener() {
        return socketListener;
    }
}
