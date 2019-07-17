package javame.sockets.server;

import javame.sockets.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServerInteger {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.PORT);
        System.out.println("Server is listening on " + Constants.PORT);
        Socket socket = serverSocket.accept();
        Scanner scanner = new Scanner(socket.getInputStream());
        scanner.useDelimiter("\\A");
        try {
            int number = scanner.nextInt();
            System.out.println("The number Received was " + number + " multiply by 10 is " + (number * 10));
        } catch (InputMismatchException number) {
            System.out.println("The Sender didn't send a well defined Integer");
        } finally {
            socket.close();
        }
    }
}