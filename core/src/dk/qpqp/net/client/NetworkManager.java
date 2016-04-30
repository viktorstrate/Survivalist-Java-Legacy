package dk.qpqp.net.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * Created by viktorstrate on 30/04/16.
 */
public class NetworkManager implements Runnable {

    Socket socket;
    OutputStreamWriter output;
    InputStreamReader input;

    public NetworkManager() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 5765);
            output = new OutputStreamWriter(socket.getOutputStream());
            output.flush();
            input = new InputStreamReader(socket.getInputStream());

            Thread t = new Thread(this);
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Listening for packet");
                CharBuffer charBuffer = CharBuffer.allocate(128);
                int length = input.read(charBuffer);

                String message = "";
                charBuffer.position(0);

                for (int i = 0; i < length; i++) {
                    message += charBuffer.get();
                }

                System.out.println("Msg: "+message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
