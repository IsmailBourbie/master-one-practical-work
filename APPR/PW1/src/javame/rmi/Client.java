package javame.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 12345);
            RemoteInterface remoteObject = (RemoteInterface) registry.lookup("remoteObject");
            remoteObject.printMessage();
            System.out.println("Method was invoked");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}