package dk.qpqp.net.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class ClientTCPListener implements Runnable {

    private GameClient gameClient;
    private Socket socket;
    private OutputStreamWriter outputStream;
    private InputStreamReader inputStream;
    private Thread thread;

    public ClientTCPListener(GameClient gameClient, InetAddress ip, int port) {
        this.gameClient = gameClient;

        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        try {
            outputStream = new OutputStreamWriter(socket.getOutputStream());
            inputStream = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (socket.isConnected()){
            try {

                char[] c = new char[1024];
                int length = inputStream.read(c);
                byte[] data = new String(c).trim().getBytes();

                gameClient.parseData(data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
