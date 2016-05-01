package dk.qpqp.net.packets;

import dk.qpqp.net.GameClient;
import dk.qpqp.net.server.GameServer;

/**
 * Created by viktorstrate on 01/05/16.
 */
public abstract class Packet {
    public static enum PacketType{
        INVALID(-1), LOGIN(00), SERVER_STATE(01);

        private int id;

        PacketType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public byte packetId;

    public Packet(int packetId){
        this.packetId = (byte) packetId;
    }

    public String readData(byte[] data) {
        String message = new String(data).trim();
        return message.substring(2);
    }

    public abstract byte[] getData();

    public static PacketType findPacket(int id){
        for(PacketType t: PacketType.values()){
            if(t.id == id){
                return t;
            }
        }
        return PacketType.INVALID;
    }
}
