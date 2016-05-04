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
        super(data);
        this.secretOrId = parts[0];
    }

    @Override
    public byte[] getData() {
        return ("05"+secretOrId+";").getBytes();
    }

    public String getSecretOrId() {
        return secretOrId;
    }
}
