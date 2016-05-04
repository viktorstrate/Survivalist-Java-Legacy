package dk.qpqp.net.packets;

import java.net.DatagramPacket;
import java.util.Arrays;

/**
 * Created by viktorstrate on 01/05/16.
 */
public abstract class Packet {

    protected String[] parts;

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

    public Packet(byte[] data){
        this.packetId = Byte.parseByte(new String(data).substring(0,2));
        String msg = new String(data).trim().substring(2);
        if(!msg.endsWith(";")){
            System.out.println("Invalid packet");
            return;
        }
        msg = msg.substring(0,msg.length()-1);
        parts = msg.split(",");
    }

    public String readData(byte[] data) {
        return new String(data).trim().substring(2);
    }

    public abstract byte[] getData();

    public PacketType getPacketType(){
        return findPacketType(getData());
    }

    public static PacketType findPacketType(DatagramPacket packet){
        return findPacketType(packet.getData());
    }

    public static PacketType findPacketType(byte[] data){
        String msg = new String(data).trim();

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
