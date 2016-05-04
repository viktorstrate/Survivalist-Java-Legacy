package dk.qpqp.net.packets;

import dk.qpqp.net.GameClient;
import dk.qpqp.net.server.GameServer;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet00Login extends Packet {

    private String username;
    private int x, y;
    private String id;

    public Packet00Login(byte[] data) {
        super(00);
        String message = new String(data).trim();
        String[] messages = message.substring(2).split(",");
        username = messages[0];
        x = Integer.parseInt(messages[1]);
        y = Integer.parseInt(messages[2]);
        id = messages[3];

    }

    public Packet00Login(String username, int x, int y, String id){
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public byte[] getDataToClient() {
        return ("00"+username+","+x+","+y+","+id).getBytes();
    }

    @Override
    public byte[] getDataToServer() {
        return getDataToClient();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }
}
