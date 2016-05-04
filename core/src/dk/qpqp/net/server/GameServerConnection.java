package dk.qpqp.net.server;

import dk.qpqp.net.GameClient;
import dk.qpqp.net.GameClientConnection;
import dk.qpqp.net.GameConnection;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameServerConnection extends GameConnection {
    private InetAddress ip;
    private int port;
    private String secret;


    public GameServerConnection(InetAddress ip, int port, String username, int x, int y, String secret, String id) {
        super(username, x, y, id);
        this.ip = ip;
        this.port = port;
        this.secret = secret;
    }

    public GameServerConnection(InetAddress ip, int port, String username, int x, int y, String secret){
        this(ip, port, username, x, y, UUID.randomUUID().toString(), secret);
    }

    public GameServerConnection(InetAddress ip, int port, String username, int x, int y){
        this(ip, port, username, x, y, UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getSecret() {
        return secret;
    }
}
