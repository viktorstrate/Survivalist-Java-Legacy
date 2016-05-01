package dk.qpqp.net.server;

import dk.qpqp.net.GameClientConnection;

import java.net.InetAddress;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameServerConnection extends GameClientConnection {
    private InetAddress ip;
    private int port;


    public GameServerConnection(InetAddress ip, int port, String username, int x, int y) {
        super(username, x, y);
        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
