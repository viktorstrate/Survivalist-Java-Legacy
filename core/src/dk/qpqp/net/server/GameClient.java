package dk.qpqp.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by viktorstrate on 29/04/16.
 */
public class GameClient implements Runnable {
    private int id;
    private Socket socket;
    private InputStreamReader input;
    private OutputStreamWriter output;

    public GameClient(Socket socket) {
        this.socket = socket;
        id = generateClientID();
    }

    public static int generateClientID() {
        return (int) Math.round((Math.random() * 9999f) + 1000);
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {

            init();

            String message;
            while ((message = new BufferedReader(input).readLine()) != null) {

                System.out.printf("Client[%d]: %s", getId(), message);
            }

            System.out.println("Client disconnected: "+getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        // Setup input / output streams
        output = new OutputStreamWriter(getSocket().getOutputStream());
        output.flush();
        input = new InputStreamReader(getSocket().getInputStream());

        System.out.println("Sent id packet");
        output.write(PacketID.CLIENT_ID.getId()+""+getId());
        output.flush();
    }
}
