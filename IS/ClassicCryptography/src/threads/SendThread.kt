package threads

import crypto.CryptoManager
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

class SendThread internal constructor(private val clientSocket: Socket) : Thread() {

    @Synchronized
    override fun run() {
        try {
            val outStream = DataOutputStream(clientSocket.getOutputStream())
            println("Connection Established. Write \"quit\" to exit")
            val reader = BufferedReader(InputStreamReader(System.`in`))
            while (true) {
                val line = reader.readLine() ?: break
                if (line.equals("quit", ignoreCase = true)) break
                val cryptogram = CryptoManager.encrypt(line)
                println("After $cryptogram")
                outStream.write(cryptogram.toByteArray())
                outStream.write(13)
                outStream.flush()
            }
            outStream.close()
        } catch (e: IOException) {
            System.exit(0)
        } finally {
            try {
                clientSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
