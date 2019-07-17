package javame.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            RemoteObject remoteObject = new RemoteObject();
            Remote stub = UnicastRemoteObject.exportObject(remoteObject, 12345);
            LocateRegistry.createRegistry(12345).bind("remoteObject", stub);
            System.out.println("Server is ready");
            while (true) {
            }
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}