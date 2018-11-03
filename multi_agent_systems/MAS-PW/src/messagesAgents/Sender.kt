package messagesAgents

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.Behaviour
import jade.lang.acl.ACLMessage

class Sender : Agent() {

    override fun setup() {
        addBehaviour(SenderBehavior())
    }

    inner class SenderBehavior : Behaviour() {
        var status = false

        override fun action() {
            val message = ACLMessage(ACLMessage.INFORM)
            message.addReceiver(AID("ismail", AID.ISLOCALNAME))
            message.content = "Hello World!"
            send(message)
            println("The Message was Sent By ${aid.localName}")
            status = true
        }

        override fun done() = status

    }
}