package javame.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {

    public void printMessage() throws RemoteException;
}