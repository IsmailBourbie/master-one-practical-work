package threads

import crypto.CryptoManager
import crypto.VignereAlgorithm
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket
import java.net.UnknownHostException

class ReceiveThread(private val client: Socket) : Thread() {

    @Synchronized
    override fun run() {
        try {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(client.getInputStream()))
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                println("Unable to connect!")
            }

            while (true) {
                val receivedMessage = reader?.readLine() ?: break
                println("Before $receivedMessage")
                println("After ${CryptoManager.decrypt(receivedMessage)}")
            }

            reader!!.close()
        } catch (e: IOException) {
            System.exit(0)
        } finally {
            try {
                client.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
