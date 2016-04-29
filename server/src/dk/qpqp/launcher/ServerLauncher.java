package dk.qpqp.launcher;


import dk.qpqp.server.Server;

/**
 * Created by viktorstrate on 18/04/16.
 */
public class ServerLauncher {
    public static void main(String[] args) {
        new Server(5765);
        System.out.println("Start launcher here");
    }
}
