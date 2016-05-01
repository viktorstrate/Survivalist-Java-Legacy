package dk.qpqp.net.server;

import dk.qpqp.net.GameClientConnection;
import dk.qpqp.net.packets.Packet;
import dk.qpqp.net.packets.Packet00Login;
import dk.qpqp.net.packets.Packet01ServerState;

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
        Packet.PacketType type = Packet.findPacket(Integer.parseInt(message.substring(0, 2)));

        switch (type) {
            case LOGIN:
                addConnection(packet);
        }

    }

    public void addConnection(DatagramPacket packet) {
        Packet00Login loginPacket = new Packet00Login(packet.getData());
        System.out.printf("%s has connected", loginPacket.getUsername());

        GameServerConnection newConnection = new GameServerConnection(packet.getAddress(), packet.getPort(),
                loginPacket.getUsername(), loginPacket.getX(), loginPacket.getY());


        GameClientConnection[] conns = new GameClientConnection[connections.size()];

        for(int i = 0; i < connections.size(); i++){
            conns[i] = connections.get(i);
        }

        sendData(new Packet01ServerState(conns).getData(), newConnection);

        sendDataToAllClients(loginPacket.getData());

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

    public ArrayList<GameServerConnection> getConnections() {
        return connections;
    }
}
