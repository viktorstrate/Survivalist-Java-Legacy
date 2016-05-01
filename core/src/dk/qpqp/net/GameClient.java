package dk.qpqp.net;

import dk.qpqp.net.packets.Packet00Login;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by viktorstrate on 01/05/16.
 */
public class GameClient implements Runnable {

    private DatagramSocket socket;
    private InetAddress serverIp;
    private int serverPort;

    public GameClient(InetAddress serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        System.out.println("Client listening on 127.0.0.1:"+socket.getLocalPort());

        Thread thread = new Thread(this);
        thread.start();

        Packet00Login login = new Packet00Login("bob", 0, 0);
        sendData(login.getData());
    }

    public void parseData(DatagramPacket packet){
        System.out.println("Raw server packet: "+new String(packet.getData()));
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
}
