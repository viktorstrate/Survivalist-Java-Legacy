package dk.qpqp.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class ServerUDPListener implements Runnable {

    private GameServer server;
    private DatagramSocket socket;
    private Thread thread;

    public ServerUDPListener(GameServer server) {
        this.server = server;
        try {
            socket = new DatagramSocket(server.getPort());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            server.parseDataUDP(packet);
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
