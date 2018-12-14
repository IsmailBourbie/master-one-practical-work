package mxcsBooks

import jade.core.Agent
import jade.core.behaviours.CyclicBehaviour
import jade.core.behaviours.OneShotBehaviour
import jade.lang.acl.ACLMessage
import jade.lang.acl.MessageTemplate


class BookSellerAgent : Agent() {

    // The catalogue of books for sale (maps the title of a book to its price)
    private var catalogue = mutableMapOf<String, Int>()
    // The GUI by means of which the user can add books in the catalogue

    private var myGui: BookSellerGui? = null

    override fun setup() {

        myGui = BookSellerGui(this)
        myGui?.showGui()

        addBehaviour(OfferRequestServer())
        addBehaviour(PurchaseRequestServer())
    }

    override fun takeDown() {
        myGui?.dispose()
        println("Seller-agent ${aid.name} terminating.")
    }

    fun updateCatalogue(title: String, parseInt: Int) {
        addBehaviour(object : OneShotBehaviour() {
            override fun action() {
                catalogue[title] = parseInt
            }
        })
    }

    private inner class OfferRequestServer : CyclicBehaviour() {
        override fun action() {
            val templateMessage = MessageTemplate.MatchPerformative(ACLMessage.CFP)
            val received = myAgent.receive()
            if (received != null) {
                val title = received.content
                val reply = received.createReply()
                val price = catalogue[title]
                if (price != null) {
                    reply.performative = ACLMessage.PROPOSE
                    reply.content = price.toString()
                } else {
                    reply.performative = ACLMessage.REFUSE
                    reply.content = "Not Available"
                }
                myAgent.send(reply)
            } else {
                block()
            }
        }
    }

    private inner class PurchaseRequestServer : CyclicBehaviour() {
        override fun action() {
            val templateMessage = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL)
            val received = myAgent.receive(templateMessage)
            if (received != null) {
                val title = received.content
                val reply = received.createReply()
                val price = catalogue.remove(title)
                if (price != null) {
                    reply.performative = ACLMessage.INFORM
                    println("$title was sold to agent ${received.sender.localName}")
                } else {
                    reply.performative = ACLMessage.FAILURE
                    reply.content = "Not Available"
                }
                myAgent.send(reply)
            } else {
                block()
            }
        }
    }
}