package dk.qpqp.net;

/**
 * Created by viktorstrate on 02/05/16.
 */
public class GameConnection {
    protected String username;
    protected float x, y;
    protected String id;

    public GameConnection(String username, float x, float y, String id) {
        this.username = username;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public GameConnection(){

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }
}
