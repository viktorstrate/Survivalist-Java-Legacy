package dk.qpqp.net.client;

import com.badlogic.gdx.math.Vector2;
import dk.qpqp.net.GameConnection;
import dk.qpqp.net.packets.Packet00Login;
import dk.qpqp.scenes.game.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * An object containing information about the connection for the client
 * Created by viktorstrate on 01/05/16.
 */
public class GameClientConnection extends GameConnection {
    private GameClient gameClient;
    private Player player;



    public GameClientConnection(String username, float x, float y, String id, GameClient gameClient) {
        super(username, x, y, id);
        this.gameClient = gameClient;





    }

    public GameClientConnection(Packet00Login loginPacket, GameClient gameClient) {
        this(loginPacket.getUsername(), loginPacket.getX(), loginPacket.getY(), loginPacket.getId(), gameClient);
    }

    private void updatePosition(){
        gameClient.getGameScene().moveNetworkPlayer(player, new Vector2(getX(), getY()));
    }

    public void setX(float x) {
        super.setX(x);
        updatePosition();
    }

    public void setY(float y) {
        super.setY(y);
        updatePosition();
    }

    public void setPosition(Vector2 position){
        setX(position.x);
        setY(position.y);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
