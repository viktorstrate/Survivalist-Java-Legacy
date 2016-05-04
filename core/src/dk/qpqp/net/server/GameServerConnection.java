package dk.qpqp.net.server;


import dk.qpqp.net.GameConnection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameServerConnection extends GameConnection implements Runnable {
    private InetAddress ip;
    private int portTCP;
    private int portUDP;
    private String secret;
    private Socket socket;
    private GameServer server;
    private OutputStreamWriter outputStream;
    private InputStreamReader inputStream;
    private boolean closed = false;

    public GameServerConnection(InetAddress ip, int portTCP, String username, int x, int y, String secret, String id, Socket socket, GameServer server) {
        super(username, x, y, id);
        init(ip, portTCP, secret, id, socket, server);
    }

    public GameServerConnection(InetAddress ip, int portTCP, String username, int x, int y, String secret, Socket socket, GameServer server){
        this(ip, portTCP, username, x, y, UUID.randomUUID().toString(), secret, socket, server);
    }

    public GameServerConnection(InetAddress ip, int portTCP, String username, int x, int y, Socket socket, GameServer server){
        this(ip, portTCP, username, x, y, UUID.randomUUID().toString(), UUID.randomUUID().toString(), socket, server);
    }

    public GameServerConnection(InetAddress ip, int portTCP, String secret, String id, Socket socket, GameServer server){
        init(ip, portTCP, secret, id, socket, server);
    }

    public GameServerConnection(InetAddress ip, int portTCP, String id, Socket socket, GameServer server){
        this(ip, portTCP, UUID.randomUUID().toString(), id, socket, server);
    }

    public GameServerConnection(InetAddress ip, int portTCP, Socket socket, GameServer server){
        this(ip, portTCP, UUID.randomUUID().toString(), UUID.randomUUID().toString(), socket, server);
    }

    private void init(InetAddress ip, int portTCP, String secret, String id, Socket socket, GameServer server){
        this.ip = ip;
        this.portTCP = portTCP;
        this.socket = socket;
        this.secret = secret;
        this.id = id;
        this.server = server;

        try {
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            inputStream = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getportTCP() {
        return portTCP;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public void run() {
        try {

            while(!closed) {

                char[] c = new char[1024];

                int length = inputStream.read(c);

                if(length==-1){
                    closed = true;
                    break;
                }

                byte[] data = new String(c).getBytes();

                server.parseDataTCP(data, this);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataTCP(byte[] data){
        try {
            outputStream.write(new String(data));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataUDP(byte[] data) {
        server.sendDataUDP(data, this);
    }

    public int getPortUDP() {
        return portUDP;
    }

    public void setPortUDP(int portUDP) {
        this.portUDP = portUDP;
    }
}
