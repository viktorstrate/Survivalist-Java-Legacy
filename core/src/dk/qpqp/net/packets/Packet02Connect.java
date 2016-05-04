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
        super(data);
        username = parts[0];
    }

    @Override
    public byte[] getData() {
        return ("02"+username+";").getBytes();
    }

    public String getUsername() {
        return username;
    }
}
