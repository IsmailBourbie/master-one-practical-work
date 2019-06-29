package sides

import Constants
import threads.ReceiveThread
import threads.SendThread
import java.net.Socket

fun main() {
    val clientSocket = Socket(Constants.IP, Constants.PORT)
    val receiver = ReceiveThread(clientSocket)
    val sender = SendThread(clientSocket)
    receiver.start()
    sender.start()
}
