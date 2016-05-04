package dk.qpqp.net.packets;

import dk.qpqp.net.GameConnection;
import dk.qpqp.net.server.GameServerConnection;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet01ServerState extends Packet {

    GameConnection[] connections;

    public Packet01ServerState(GameServerConnection[] connections) {
        super(01);
        this.connections = connections;
    }

    public Packet01ServerState(byte[] data){
        super(01);
        String[] parts = new String(data).trim().substring(2).split(",");
        ArrayList<GameConnection> list = new ArrayList<>();
        for(int i = 0; i < parts.length; i+=4){
            list.add(new GameConnection(parts[i], Float.parseFloat(parts[i+1]), Float.parseFloat(parts[i+2]), parts[+3]));
        }
        connections = new GameConnection[list.size()];
        connections = list.toArray(connections);
    }

    @Override
    public byte[] getDataToClient() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("01".getBytes());

        for(GameConnection c: connections){
            buffer.put((c.getUsername()+","+c.getX()+","+c.getY()+","+c.getId()+",").getBytes());
        }

        return Arrays.copyOfRange(buffer.array(), 0, buffer.position());
    }

    @Override
    public byte[] getDataToServer() {
        return new byte[0];
    }

    public GameConnection[] getConnections() {
        return connections;
    }
}
