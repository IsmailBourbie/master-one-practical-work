package rmi

class RemoteObject : RemoteInterface {

    override fun printMessage() {
        println("Hello world with RMI")
    }
}