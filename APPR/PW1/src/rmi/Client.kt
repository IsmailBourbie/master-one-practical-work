package rmi

import javame.rmi.RemoteInterface
import java.rmi.registry.LocateRegistry

fun main() {
    try {
        val registry = LocateRegistry.getRegistry("localhost", 12345)
        val remoteObject = registry.lookup("remoteObject") as RemoteInterface
        remoteObject.printMessage()
        println("Method was invoked")
    } catch (e: Exception) {
        println("Client exception was ${e.localizedMessage}")
    }
}