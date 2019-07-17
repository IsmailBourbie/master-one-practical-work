package sockets.server

import java.sockets.Constants
import java.net.ServerSocket
import java.util.*

fun main() {
    val port = Constants.PORT
    val serverSocket = ServerSocket(port)
    println("Server is listening on $port")
    val socket = serverSocket.accept()
    val scanner = Scanner(socket.getInputStream())
    scanner.useDelimiter("\\A")
    val message = scanner.next()
    print("The message Received was $message")
    socket.close()
}