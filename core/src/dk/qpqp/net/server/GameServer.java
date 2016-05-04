package dk.qpqp.net.server;

import com.sun.istack.internal.Nullable;
import dk.qpqp.net.packets.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static dk.qpqp.net.packets.Packet.PacketType.CONNECT;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameServer {

    private int port;

    private ServerTCPListener tcpListener;
    private ServerUDPListener udpListener;
    private ArrayList<GameServerConnection> connections;

    public GameServer(int port) {
        this.port = port;
        connections = new ArrayList<>();


        System.out.println("Server listening on 127.0.0.1:" + port);

        tcpListener = new ServerTCPListener(this);
        udpListener = new ServerUDPListener(this);

    }

    public void parseDataUDP(DatagramPacket p){
        Packet packet = parseData(p.getData());

        switch (packet.getPacketType()) {
            case PLAYER_MOVE:
                for(GameServerConnection c: getConnections()){
                    if(((Packet04PlayerMove)packet).getIdOrSecret().equals(c.getSecret())){
                        System.out.println("Set port for client");
                        c.setPortUDP(p.getPort());
                    }
                }
                updatePlayerPosition(new Packet04PlayerMove(packet.getData()));
                break;
            default:
                System.out.println("Couldn't parse data for UDP packet");
        }
    }

    public void parseDataTCP(byte[] data, GameServerConnection connection) {
        Packet packet = parseData(data);

        switch (packet.getPacketType()) {
            case CONNECT:
                addConnection(data, connection);
                break;
            case DISCONNECT:
                disconnectPlayer(new Packet05Disconnect(data));
                break;
            default:
                System.out.println("Couldn't parse data for TCP packet");
        }

    }

    private Packet parseData(byte[] data){
        String message = new String(data).trim();
        System.out.println("Raw Client Packet " + message);
        Packet.PacketType type = Packet.findPacketType(data);

        switch (type) {
            case CONNECT:
                return new Packet02Connect(data);
            case PLAYER_MOVE:
                return new Packet04PlayerMove(data);
            case DISCONNECT:
                return new Packet05Disconnect(data);
            default:
                System.out.println("Couldn't parse data for packet");
                return null;
        }
    }

    public void disconnectPlayer(Packet05Disconnect packet){
        System.out.println("DISCONNECT PACKET");
        for(GameServerConnection c: connections){
            if(c.getSecret().equals(packet.getSecretOrId())){
                System.out.println(c.getUsername()+" Disconnected");
                connections.remove(c);
                sendDataToAllClientsTCP(new Packet05Disconnect(c.getId()).getData());
                return;
            }
        }
        System.out.println("Player to disconnect not found");
    }

    public void updatePlayerPosition(Packet04PlayerMove packet){
        System.out.println("Player move: "+packet.getX()+":"+packet.getY());
        GameServerConnection con = findServerConnection(packet.getIdOrSecret());
        con.setX(packet.getX());
        con.setY(packet.getY());

        Packet04PlayerMove movePacketToSend = new Packet04PlayerMove(con.getId(), packet.getX(), packet.getY());

        sendDataToAllClientsUDPExcept(movePacketToSend.getData(), con);
    }

    public GameServerConnection findServerConnection(String secret){
        for(GameServerConnection con: connections){
            if(con.getSecret().equals(secret)){
                return con;
            }
        }
        return null;
    }

    public void addConnection(byte[] data, GameServerConnection connection) {
        final int x = 32*64;
        final int y = 32*64;
        Packet02Connect connectPacket = new Packet02Connect(data);

        System.out.printf("%s has connected\n", connectPacket.getUsername());

        connection.setUsername(connectPacket.getUsername());
        connection.setX(x);
        connection.setY(y);

        GameServerConnection[] conns = new GameServerConnection[connections.size()];

        for(int i = 0; i < connections.size(); i++){
            conns[i] = connections.get(i);
        }

        Packet03ConnectReply replyPacket = new Packet03ConnectReply(connection.getSecret(), connection.getId(), x, y);
        connection.sendDataTCP(replyPacket.getData());

        if(conns.length>0)
            connection.sendDataTCP(new Packet01ServerState(conns).getData());

        sendDataToAllClientsTCPExcept(new Packet00Login(connectPacket.getUsername(), x, y, connection.getId()).getData(), connection);

        connections.add(connection);


    }

    public void sendDataUDP(byte[] data, GameServerConnection connection){
        System.out.println("Sending UDP data");
        DatagramPacket packet = new DatagramPacket(data, data.length, connection.getIp(), connection.getPortUDP());
        try {
            udpListener.getSocket().send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Send UDP data");
    }

    public void sendDataToAllClientsUDP(byte[] data) {
        for (GameServerConnection c : connections) {
            sendDataUDP(data, c);
        }
    }

    public void sendDataToAllClientsUDPExcept(byte[] data, GameServerConnection exception) {
        for (GameServerConnection c : connections) {
            if(exception.equals(c)) continue;
            sendDataUDP(data, c);
        }
    }

    public void sendDataToAllClientsTCP(byte[] data){
        for (GameServerConnection c : connections) {
            c.sendDataTCP(data);
        }
    }

    public void sendDataToAllClientsTCPExcept(byte[] data, GameServerConnection exception){
        for (GameServerConnection c : connections) {
            if(exception.equals(c)) continue;
            c.sendDataTCP(data);
        }
    }

    public ArrayList<GameServerConnection> getConnections() {
        return connections;
    }

    public int getPort() {
        return port;
    }

    public void dispose(){
        tcpListener.stop();
    }
}
