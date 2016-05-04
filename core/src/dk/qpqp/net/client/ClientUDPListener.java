package dk.qpqp.net.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class ClientUDPListener implements Runnable {

    private GameClient gameClient;
    private DatagramSocket socket;
    private Thread thread;
    private boolean stopped = false;

    public ClientUDPListener(GameClient gameClient) {
        this.gameClient = gameClient;

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (!stopped){

            System.out.println("Listening for UDP");
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Received UDP Packet");
            gameClient.parseData(packet.getData());
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void stop(){
        stopped = true;
    }
}
