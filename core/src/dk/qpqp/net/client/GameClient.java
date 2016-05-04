package dk.qpqp.net.client;

import com.badlogic.gdx.math.Vector2;
import com.sun.istack.internal.Nullable;
import dk.qpqp.net.GameConnection;
import dk.qpqp.net.packets.*;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameClient {


    private InetAddress serverIp;
    private int serverPort;
    private GameScene gameScene;
    private ArrayList<GameClientConnection> connections;
    private String secret;
    private String clientId;

    private ClientUDPListener UDPListener;
    private ClientTCPListener TCPListener;

    public GameClient(InetAddress serverIp, int serverPort, GameScene gameScene) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;


        this.connections = new ArrayList<>();
        this.gameScene = gameScene;

        TCPListener = new ClientTCPListener(this, serverIp, serverPort);
        UDPListener = new ClientUDPListener(this);

        System.out.println("Client listening on 127.0.0.1:"+UDPListener.getSocket().getLocalPort());

        Packet02Connect connectPacket = new Packet02Connect("bob");
        sendDataTCP(connectPacket.getData());
    }

    public void parseData(byte[] data, @Nullable GameClientConnection connection){
        String message = new String(data);
        System.out.println("Raw server packet: "+message);

        Packet.PacketType packetType = Packet.findPacketType(data);

        switch (packetType){
            case CONNECT_REPLY:
                System.out.println("CONNECT_REPLY PACKET");
                Packet03ConnectReply replyPacket = new Packet03ConnectReply(data);
                secret = replyPacket.getSecret();
                clientId = replyPacket.getClientId();
                gameScene.setPlayer(new Player(replyPacket.getX(), replyPacket.getY(), gameScene, true));
                break;
            case LOGIN:
                System.out.println("LOGIN PACKET");
                Packet00Login loginPacket = new Packet00Login(data);
                addPlayer(new GameClientConnection(loginPacket, this));
                break;
            case PLAYER_MOVE:
                System.out.println("PLAYER MOVE PACKET");
                Packet04PlayerMove movePacket = new Packet04PlayerMove(data);
                movePlayer(movePacket);
                break;
            case SERVER_STATE:
                System.out.println("SERVER STATE");
                Packet01ServerState statePacket = new Packet01ServerState(data);
                for(GameConnection c: statePacket.getConnections()){
                    System.out.println("Added player to the game");
                    addPlayer(new GameClientConnection(c.getUsername(), c.getX(), c.getY(), c.getId(), this));
                }
                break;
            case DISCONNECT:
                System.out.println("PLAYER DISCONNECTED");
                Packet05Disconnect disconnectPacket = new Packet05Disconnect(data);
                for(GameClientConnection c: connections){
                    if(c.getId().equals(disconnectPacket.getSecretOrId())){
                        System.out.println(c.getUsername()+" Disconnected");
                        gameScene.removeGameObject(c.getPlayer());
                    }
                }
                break;
            case INVALID:
                System.out.println("RECEIVED INVALID PACKET FROM CLIENT");

        }

    }

    public void parseData(byte[] data){
        parseData(data, null);
    }

    public void movePlayer(Packet04PlayerMove movePacket){
        GameClientConnection con = findServerConnection(movePacket.getIdOrSecret());
        if(con!=null) {
            System.out.println("Updating position for "+movePacket.getIdOrSecret());
            con.setPosition(new Vector2(movePacket.getX(), movePacket.getY()));
        } else {
            System.out.println("Couldn't find connection with id: "+movePacket.getIdOrSecret());
        }
    }

    public void addPlayer(GameClientConnection connection){
        if(connection.getId().equals(getClientId())){
            System.out.println("Player got the same id as client, returning");
            return;
        }
        System.out.println(connection.getUsername()+" connected to the game");
        connections.add(connection);
        connection.setPlayer(gameScene.addNetworkPlayer(connection));
    }

    public void sendDataUDP(byte[] data){
        sendDataUDP(data, serverIp, serverPort);
    }

    public void sendDataUDP(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            UDPListener.getSocket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataTCP(byte[] data){
        try {
            OutputStreamWriter outputStream = new OutputStreamWriter(TCPListener.getSocket().getOutputStream());
            outputStream.write(new String(data));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameClientConnection findServerConnection(String id){
        for(GameClientConnection con: connections){
            if(con.getId().equals(id)){
                return con;
            }
        }
        return null;
    }

    public ArrayList<GameClientConnection> getConnections() {
        return connections;
    }

    public String getSecret() {
        return secret;
    }

    public String getClientId() {
        return clientId;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void disconnect() {
        System.out.println("Disconnecting from server");
        Packet05Disconnect disconnectPacket = new Packet05Disconnect(secret);
        sendDataTCP(disconnectPacket.getData());

        UDPListener.stop();
    }

}
