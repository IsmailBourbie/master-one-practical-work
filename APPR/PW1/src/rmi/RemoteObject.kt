package rmi

import javame.rmi.RemoteInterface

class RemoteObject : RemoteInterface {

    override fun printMessage() {
        println("Hello world with RMI")
    }
}