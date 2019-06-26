import java.net.Socket

fun main() {
    val clientSocket = Socket(Constants.IP, Constants.PORT)
    val receiver = ReceiveThread(clientSocket)
    val sender = SendThread(clientSocket)
    receiver.start()
    sender.start()
}
