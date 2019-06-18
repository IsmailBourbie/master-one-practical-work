package sockets.server

import sockets.Constants
import java.net.ServerSocket
import java.util.*

fun main() {
    val port = Constants.PORT
    val serverSocket = ServerSocket(port)
    println("Server is listening on $port")
    val socket = serverSocket.accept()
    val scanner = Scanner(socket.getInputStream())
    scanner.useDelimiter("\\A")
    try {
        val number = scanner.nextInt()
        println("The number Received was $number, multiply by 10 is ${number * 10}")
    } catch (number: InputMismatchException) {
        println("The Sender didn't send a well defined Integer")
    } finally {
        socket.close()
    }
}