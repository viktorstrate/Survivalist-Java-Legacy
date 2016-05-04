package dk.qpqp.net;

/**
 * Created by viktorstrate on 02/05/16.
 */
public class GameConnection {
    private String username;
    private float x, y;
    private String id;

    public GameConnection(String username, float x, float y, String id) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }
}
