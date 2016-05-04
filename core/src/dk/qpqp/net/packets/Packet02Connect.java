package dk.qpqp.net.packets;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet02Connect extends Packet {

    String username;

    public Packet02Connect(String username) {
        super(02);
        this.username = username;
    }

    public Packet02Connect(byte[] data){
        super(02);
        username = new String(data).substring(2).trim();
    }

    @Override
    public byte[] getDataToServer() {
        return ("02"+username).getBytes();
    }

    @Override
    public byte[] getDataToClient() {
        return new byte[0];
    }

    public String getUsername() {
        return username;
    }
}
