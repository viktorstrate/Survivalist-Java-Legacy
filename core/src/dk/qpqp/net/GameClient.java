package dk.qpqp.net;

import com.badlogic.gdx.math.Vector2;
import dk.qpqp.net.packets.*;
import dk.qpqp.scenes.game.GameScene;
import dk.qpqp.scenes.game.entity.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameClient implements Runnable {

    private DatagramSocket socket;
    private InetAddress serverIp;
    private int serverPort;
    private GameScene gameScene;
    private ArrayList<GameClientConnection> connections;
    private String secret;
    private String clientId;

    public GameClient(InetAddress serverIp, int serverPort, GameScene gameScene) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        this.connections = new ArrayList<>();
        this.gameScene = gameScene;

        System.out.println("Client listening on 127.0.0.1:"+socket.getLocalPort());

        Thread thread = new Thread(this);
        thread.start();

        Packet02Connect connectPacket = new Packet02Connect("bob");
        sendData(connectPacket.getDataToServer());
    }

    public void parseData(DatagramPacket packet){
        String message = new String(packet.getData());
        System.out.println("Raw server packet: "+message);

        Packet.PacketType packetType = Packet.findPacketType(packet);

        switch (packetType){
            case CONNECT_REPLY:
                System.out.println("CONNECT_REPLY PACKET");
                Packet03ConnectReply replyPacket = new Packet03ConnectReply(packet.getData());
                secret = replyPacket.getSecret();
                clientId = replyPacket.getClientId();
                gameScene.setPlayer(new Player(64 * 32, 64 * 32, gameScene, true));
                break;
            case LOGIN:
                System.out.println("LOGIN PACKET");
                Packet00Login loginPacket = new Packet00Login(packet.getData());
                addPlayer(new GameClientConnection(loginPacket, this));
                break;
            case PLAYER_MOVE:
                System.out.println("PLAYER MOVE PACKET");
                Packet04PlayerMove movePacket = new Packet04PlayerMove(packet.getData());
                movePlayer(movePacket);
                break;
            case SERVER_STATE:
                System.out.println("SERVER STATE");
                Packet01ServerState statePacket = new Packet01ServerState(packet.getData());
                for(GameConnection c: statePacket.getConnections()){
                    System.out.println("Added player to the game");
                    addPlayer(new GameClientConnection(c.getUsername(), c.getX(), c.getY(), c.getId(), this));
                }
                break;
            case DISCONNECT:
                System.out.println("PLAYER DISCONNECTED");
                Packet05Disconnect disconnectPacket = new Packet05Disconnect(packet.getData());
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
        System.out.println(connection.getUsername()+" connected to the game");
        connections.add(connection);
        connection.setPlayer(gameScene.addNetworkPlayer(connection));
    }

    public void sendData(byte[] data){
        sendData(data, serverIp, serverPort);
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
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

    @Override
    public void run() {
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            parseData(packet);
        }
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
        sendData(disconnectPacket.getDataToServer());
    }
}
