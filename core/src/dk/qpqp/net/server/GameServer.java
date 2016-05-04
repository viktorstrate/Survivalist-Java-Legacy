package dk.qpqp.net.server;

import dk.qpqp.net.packets.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameServer {

    private int port;
    private DatagramSocket socket;
    private ArrayList<GameServerConnection> connections;

    public GameServer(int port) {
        this.port = port;
        connections = new ArrayList<>();
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Server listening on 127.0.0.1:" + socket.getLocalPort());

        running();
    }

    public void running() {
        while (true) {
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

    public void parseData(DatagramPacket packet) {
        byte[] data = packet.getData();
        String message = new String(data).trim();
        System.out.println("Raw Client Packet " + message);
        Packet.PacketType type = Packet.findPacketType(packet);

        switch (type) {
            case CONNECT:
                addConnection(packet);
                break;
            case PLAYER_MOVE:
                updatePlayerPosition(new Packet04PlayerMove(packet.getData()));
                break;
            case DISCONNECT:
                disconnectPlayer(new Packet05Disconnect(packet.getData()));
                break;
            default:
                System.out.println("Couldn't parse data for packet");
        }

    }

    public void disconnectPlayer(Packet05Disconnect packet){
        System.out.println("DISCONNECT PACKET");
        for(GameServerConnection c: connections){
            if(c.getSecret().equals(packet.getSecretOrId())){
                System.out.println(c.getUsername()+" Disconnected");
                connections.remove(c);
                sendDataToAllClients(new Packet05Disconnect(c.getId()).getDataToClient());
                return;
            }
        }
        System.out.println("Player to disconnect not found");
    }

    public void updatePlayerPosition(Packet04PlayerMove packet){
        GameServerConnection con = findServerConnection(packet.getIdOrSecret());
        con.setX(packet.getX());
        con.setY(packet.getY());

        Packet04PlayerMove movePacketToSend = new Packet04PlayerMove(con.getId(), packet.getX(), packet.getY());

        sendDataToAllClientsExcept(movePacketToSend.getDataToClient(), con);
    }

    public GameServerConnection findServerConnection(String secret){
        for(GameServerConnection con: connections){
            if(con.getSecret().equals(secret)){
                return con;
            }
        }
        return null;
    }

    public void addConnection(DatagramPacket packet) {
        Packet02Connect connectPacket = new Packet02Connect(packet.getData());

        System.out.printf("%s has connected\n", connectPacket.getUsername());

        GameServerConnection newConnection = new GameServerConnection(packet.getAddress(), packet.getPort(),
                connectPacket.getUsername(), 0, 0);

        GameServerConnection[] conns = new GameServerConnection[connections.size()];

        for(int i = 0; i < connections.size(); i++){
            conns[i] = connections.get(i);
        }

        Packet03ConnectReply replyPacket = new Packet03ConnectReply(newConnection.getSecret(), newConnection.getId(), 64 * 32, 64 * 32);
        sendData(replyPacket.getDataToClient(), newConnection);


        if(conns.length>0) {
            sendData(new Packet01ServerState(conns).getDataToClient(), newConnection);
            sendDataToAllClients(new Packet00Login(connectPacket.getUsername(), 64 * 32, 64 * 32, newConnection.getId()).getDataToClient());
        }

        connections.add(newConnection);


    }

    public void sendData(byte[] data, GameServerConnection connection) {
        sendData(data, connection.getIp(), connection.getPort());
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (GameServerConnection c : connections) {
            sendData(data, c.getIp(), c.getPort());
        }
    }

    public void sendDataToAllClientsExcept(byte[] data, GameServerConnection exception) {
        for (GameServerConnection c : connections) {
            if(exception.equals(c)) continue;
            sendData(data, c.getIp(), c.getPort());
        }
    }

    public ArrayList<GameServerConnection> getConnections() {
        return connections;
    }
}
