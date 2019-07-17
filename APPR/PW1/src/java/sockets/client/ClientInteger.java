package java.sockets.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.sockets.Constants;

public class ClientInteger {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(Constants.IP_ADDRESS, Constants.PORT)) {
            PrintStream writer = new PrintStream(socket.getOutputStream());
            writer.print(10);
            writer.flush();
        } finally {
            System.out.println("Message was sent, checkout the server");
        }
    }
}