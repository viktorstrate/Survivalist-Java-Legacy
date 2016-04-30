package dk.qpqp.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by viktorstrate on 28/04/16.
 */
public class Server {

    private int port;
    private ServerSocket server;
    private ArrayList<GameClient> clients;
    private boolean running = true;

    public Server(int port) {
        this.port = port;

        clients = new ArrayList<>();

        try {
            setupServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupServer() throws IOException {
        server = new ServerSocket(port);

        System.out.println("Server started, listening on 127.0.0.1:"+port);

        listen();

    }

    private void listen() throws IOException {
        System.out.println("Waiting for people to connect");
        while (running) {
            GameClient client = new GameClient(server.accept());

            System.out.println("Client connected: "+client.getId());

            Thread t = new Thread(client);
            t.start();

        }
    }


}
