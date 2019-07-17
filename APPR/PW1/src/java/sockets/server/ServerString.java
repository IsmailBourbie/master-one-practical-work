package sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sockets.Constants;
import java.util.Scanner;


public class ServerString {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.PORT);
        System.out.println("Server is listening on " + Constants.PORT);
        Socket socket = serverSocket.accept();
        Scanner scanner = new Scanner(socket.getInputStream());
        scanner.useDelimiter("\\A");
        String data = scanner.next();
        System.out.println("The message Received was " + data);
        socket.close();
    }
}