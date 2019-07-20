package rmi

import java.rmi.registry.LocateRegistry
import java.rmi.server.UnicastRemoteObject

fun main() {
    try {
        val remoteObject = RemoteObject()
        val stub = UnicastRemoteObject.exportObject(remoteObject, 12345) as RemoteInterface
        LocateRegistry.createRegistry(12345).bind("remoteObject", stub)
        println("Server is ready")
        while (true) {
        }
    } catch (e: Exception) {
        println("Server exception was ${e.localizedMessage}")
        e.printStackTrace()
    }
}