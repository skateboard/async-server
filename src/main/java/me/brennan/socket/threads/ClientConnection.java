package me.brennan.socket.threads;

import me.brennan.socket.SocketServer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class ClientConnection implements Runnable {

    private final SocketServer socketServer;
    private final AsynchronousSocketChannel socketChannel;


    public ClientConnection(SocketServer socketServer, AsynchronousSocketChannel socketChannel) {
        this.socketServer = socketServer;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        socketServer.getSocketListener().onConnect(socketServer, this);

        while (socketChannel.isOpen()) {
            try {
                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                final Future<Integer> read = socketChannel.read(buffer);
                read.get();

                final String inputString = new String(buffer.array()).trim();

                // #TODO packet receiving

                buffer.flip();
            } catch (Exception e) {
                socketServer.getSocketListener().onDisconnect(socketServer, this);
            }
        }
    }
}
