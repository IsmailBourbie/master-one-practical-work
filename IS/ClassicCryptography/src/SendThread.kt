import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket

class SendThread internal constructor(private val clientSocket: Socket) : Thread() {

    @Synchronized
    override fun run() {
        try {
            val dos = DataOutputStream(clientSocket.getOutputStream())
            var message = ""
            println("Connection Established. Write \"quit\" to exit")
            val br = BufferedReader(InputStreamReader(System.`in`))
            while (true) {
                val line = br.readLine() ?: break
                if (line.equals("quit", ignoreCase = true)) break
                dos.write(line.toByteArray())
                dos.write(13)
                dos.flush()
            }
            dos.close()
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
