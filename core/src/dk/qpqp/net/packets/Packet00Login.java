package dk.qpqp.net.packets;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet00Login extends Packet {

    private String username;
    private int x, y;
    private String id;

    public Packet00Login(byte[] data) {
        super(data);

        username = parts[0];
        x = Integer.parseInt(parts[1]);
        y = Integer.parseInt(parts[2]);
        id = parts[3];

    }

    public Packet00Login(String username, int x, int y, String id){
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public byte[] getData() {
        return ("00"+username+","+x+","+y+","+id+";").getBytes();
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
