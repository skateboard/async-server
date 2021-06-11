package me.brennan.socket.listener;

import me.brennan.socket.SocketServer;
import me.brennan.socket.threads.ClientConnection;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public interface SocketListener {

    void onConnect(SocketServer server, ClientConnection client);

    // #TODO packet receive

    void onDisconnect(SocketServer server, ClientConnection client);

}
