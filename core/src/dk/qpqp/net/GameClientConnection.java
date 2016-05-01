package dk.qpqp.net;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameClientConnection {
    private String username;
    private int x, y;

    public GameClientConnection(String username, int x, int y) {
        this.username = username;
        this.x = x;
        this.y = y;
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
