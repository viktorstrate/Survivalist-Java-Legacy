package dk.qpqp.net.server;

/**
 * Created by viktorstrate on 28/04/16.
 */
public enum PacketID {

    CLIENT_ID('1');

    private char id;

    PacketID(char id) {
        this.id = id;
    }

    public char getId() {
        return id;
    }
}
