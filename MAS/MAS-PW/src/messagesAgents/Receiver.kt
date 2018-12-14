package messagesAgents

import jade.core.Agent
import jade.core.behaviours.Behaviour

class Receiver : Agent() {

    override fun setup() {
        addBehaviour(ReceivingBehavior())
    }

    inner class ReceivingBehavior : Behaviour() {
        var status = false

        override fun action() {
            val message = receive()
            if (message != null) {
                val content = message.content
                println("The Receiver Message was $content by ${aid.localName}")
                status = false
            } else {
                block()
            }
        }

        override fun done() = status
    }
}