package dk.qpqp.net.packets;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet03ConnectReply extends Packet {

    private String secret;
    private String clientId;
    private int x, y;

    public Packet03ConnectReply(String secret, String clientId, int x, int y) {
        super(03);

        this.secret = secret;
        this.clientId = clientId;
        this.x = x;
        this.y = y;
    }


    public Packet03ConnectReply(byte[] data){
        super(data);

        clientId = parts[0];
        secret = parts[1];
        x = Integer.parseInt(parts[2]);
        y = Integer.parseInt(parts[3]);

    }

    @Override
    public byte[] getData() {
        return ("03"+clientId+","+secret+","+x+","+y+";").getBytes();
    }

    public String getSecret() {
        return secret;
    }

    public String getClientId() {
        return clientId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
