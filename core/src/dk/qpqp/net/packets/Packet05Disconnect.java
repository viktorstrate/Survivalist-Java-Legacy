package dk.qpqp.net.packets;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class Packet05Disconnect extends Packet {

    private String secretOrId;

    public Packet05Disconnect(String secretOrId) {
        super(05);
        this.secretOrId = secretOrId;
    }

    public Packet05Disconnect(byte[] data){
        super(05);
        this.secretOrId = new String(data).substring(2).trim();
    }

    @Override
    public byte[] getDataToServer() {
        return ("05"+secretOrId).getBytes();
    }

    @Override
    public byte[] getDataToClient() {
        return getDataToServer();
    }

    public String getSecretOrId() {
        return secretOrId;
    }
}
