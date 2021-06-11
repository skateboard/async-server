import me.brennan.socket.SocketServer;
import me.brennan.socket.listener.SocketListener;
import me.brennan.socket.threads.ClientConnection;

/**
 * @author Brennan
 * @since 6/11/2021
 **/
public class TestSocket {

    public static void main(String[] args) throws Exception {
        final SocketServer socketServer = new SocketServer(1337);
        socketServer.setSocketListener(new SocketListener() {
            @Override
            public void onConnect(SocketServer server, ClientConnection client) {
                System.out.println("New Client Connection");
            }

            @Override
            public void onDisconnect(SocketServer server, ClientConnection client) {
                System.out.println("Client Disconnect");
            }
        });

        socketServer.start();
    }

}
