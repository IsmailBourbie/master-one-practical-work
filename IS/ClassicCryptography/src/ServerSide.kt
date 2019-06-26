import threads.ReceiveThread
import threads.SendThread
import java.io.IOException
import java.net.ServerSocket

fun main() {
    var serverSocket: ServerSocket? = null
    try {
        serverSocket = ServerSocket(Constants.PORT)
        println("Socket Ready! Waiting for Client to accept...")
    } catch (e: IOException) {
        e.printStackTrace()
        println("Port is Busy!")
        System.exit(1)
    }

    val client = serverSocket!!.accept()
    val receiver = ReceiveThread(client)
    val sender = SendThread(client)
    sender.start()
    receiver.start()
}