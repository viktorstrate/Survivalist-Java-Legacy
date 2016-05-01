package dk.qpqp.net.packets;

import dk.qpqp.net.GameClient;
import dk.qpqp.net.server.GameServer;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet00Login extends Packet {

    private String username;
    private int x, y;

    public Packet00Login(byte[] data) {
        super(00);
        String message = new String(data).trim();
        String[] messages = message.substring(2).split(",");
        username = messages[0];
        x = Integer.parseInt(messages[1]);
        y = Integer.parseInt(messages[2]);

    }

    public Packet00Login(String username, int x, int y){
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    @Override
    public byte[] getData() {
        return ("00"+username+","+x+","+y).getBytes();
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
}
