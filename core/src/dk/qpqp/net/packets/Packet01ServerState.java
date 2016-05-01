package dk.qpqp.net.packets;

import dk.qpqp.net.GameClientConnection;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class Packet01ServerState extends Packet {

    GameClientConnection[] connections;

    public Packet01ServerState(GameClientConnection[] connections) {
        super(01);
        this.connections = connections;
    }

    public Packet01ServerState(byte[] data){
        super(01);
        String[] parts = new String(data).trim().substring(2).split(",");
        ArrayList<GameClientConnection> list = new ArrayList<>();
        for(int i = 0; i < parts.length; i+=3){
            list.add(new GameClientConnection(parts[i], Integer.parseInt(parts[i+1]), Integer.parseInt(parts[i+2])));
        }
        connections = (GameClientConnection[]) list.toArray();
    }

    @Override
    public byte[] getData() {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("01".getBytes());

        for(GameClientConnection c: connections){
            buffer.put((c.getUsername()+","+c.getX()+","+c.getY()).getBytes());
        }

        return Arrays.copyOfRange(buffer.array(), 0, buffer.position());
    }
}
