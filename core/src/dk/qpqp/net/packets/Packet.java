package dk.qpqp.net.packets;

import java.net.DatagramPacket;

/**
 * Created by viktorstrate on 01/05/16.
 */
public abstract class Packet {
    public static enum PacketType{
        INVALID(-1), LOGIN(00), SERVER_STATE(01), CONNECT(02), CONNECT_REPLY(03), PLAYER_MOVE(04), DISCONNECT(05);

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

    public abstract byte[] getDataToServer();
    public abstract byte[] getDataToClient();

    public static PacketType findPacketType(DatagramPacket packet){

        String msg = new String(packet.getData()).trim();

        if(msg.length()<2){
            return PacketType.INVALID;
        }

        int id = Integer.parseInt(msg.substring(0,2));

        for(PacketType t: PacketType.values()){
            if(t.id == id){
                return t;
            }
        }
        return PacketType.INVALID;
    }
}
