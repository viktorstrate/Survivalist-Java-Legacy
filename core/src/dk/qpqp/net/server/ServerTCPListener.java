package dk.qpqp.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by viktorstrate on 04/05/16.
 */
public class ServerTCPListener implements Runnable {

    private GameServer server;
    private ServerSocket socket;
    private Thread thread;
    private boolean stopped = false;

    public ServerTCPListener(GameServer server) {
        this.server = server;
        try {
            socket = new ServerSocket(server.getPort());
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(!stopped){
                Socket s = socket.accept();
                System.out.println("Connection established");
                GameServerConnection connection = new GameServerConnection(s.getInetAddress(), s.getPort(), s, server);

                Thread c = new Thread(connection);
                c.start();

            }
            System.out.println("Tcp Listener stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        stopped = true;
    }
}
