package client

import java.io.IOException
import java.io.PrintStream
import java.net.Socket
import java.net.UnknownHostException

fun main() {
    var socket: Socket? = null
    try {
        socket = Socket("localhost", 12345)
        val outStream = socket.getOutputStream()
        val writer = PrintStream(outStream)
        writer.print("Hello world !")
        writer.flush()
    } catch (host: UnknownHostException) {
        println(host.localizedMessage)
    } catch (io: IOException) {
        println(io.localizedMessage)
    } finally {
        socket?.close()
    }
}