package sockets.client

import sockets.Constants
import java.io.IOException
import java.io.PrintStream
import java.net.Socket
import java.net.UnknownHostException

fun main() {
    var socket: Socket? = null
    try {
        socket = Socket(Constants.IP_ADDRESS, Constants.PORT)
        val outStream = socket.getOutputStream()
        val writer = PrintStream(outStream)
        writer.print(10)
        writer.flush()
    } catch (host: UnknownHostException) {
        println(host.localizedMessage)
    } catch (io: IOException) {
        println(io.localizedMessage)
    } finally {
        print("Number was sent, checkout the server")
        socket?.close()
    }
}