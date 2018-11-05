package mxcsBooks

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.Behaviour
import jade.core.behaviours.TickerBehaviour
import jade.lang.acl.ACLMessage
import jade.lang.acl.MessageTemplate

class BookBuyerAgent : Agent() {

    private lateinit var targetBookTitle: String

    private val sellerAgents = arrayListOf(AID("Seller1", AID.ISLOCALNAME),
            AID("Seller2", AID.ISLOCALNAME))

    override fun setup() {

        val args = arguments
        if (args != null && args.isNotEmpty()) {
            targetBookTitle = args[0] as String
            println("Title Specified was $targetBookTitle.")

            addBehaviour(object : TickerBehaviour(this, 6000) {
                override fun onTick() {
                    myAgent.addBehaviour(RequestPerformer())
                }
            })

        } else {
            println("No Title Specified.")
            doDelete()
        }
        println("Buyer-Agent ${aid.name} is Ready.")
    }

    override fun takeDown() {
        println("Hello ! Buyer-Agent ${aid.name} is Terminating.")
    }

    inner class RequestPerformer : Behaviour() {

        private var bestSeller: AID? = null
        private var bestPrice = Int.MAX_VALUE
        private var step = 0
        private var repliesCount = 0

        private lateinit var messageTemplate: MessageTemplate

        override fun action() {
            when (step) {
                0 -> {
                    val cfpMessage = ACLMessage(ACLMessage.CFP)
                    for (seller in sellerAgents)
                        cfpMessage.addReceiver(seller)
                    cfpMessage.content = targetBookTitle
                    cfpMessage.conversationId = "book-trade"
                    cfpMessage.replyWith = "cfp ${System.currentTimeMillis()}"
                    myAgent.send(cfpMessage)
                    messageTemplate = MessageTemplate.and(
                            MessageTemplate.MatchConversationId("book-trade"),
                            MessageTemplate.MatchReplyWith(cfpMessage.replyWith))
                    step = 1
                }
                1 -> {
                    val received = myAgent.receive(messageTemplate)
                    if (received != null) {
                        if (received.performative == ACLMessage.PROPOSE) {
                            val price = received.content.toInt()
                            if (bestSeller == null || price < bestPrice) {
                                bestPrice = price
                                bestSeller = received.sender
                            }
                        }
                        repliesCount++
                        if (repliesCount >= sellerAgents.size)
                            step = 2
                    } else {
                        block()
                    }
                }

                2 -> {
                    val order = ACLMessage(ACLMessage.ACCEPT_PROPOSAL)
                    order.addReceiver(bestSeller)
                    order.content = targetBookTitle
                    order.conversationId = "book-trade"
                    order.replyWith = "order ${System.currentTimeMillis()}"
                    messageTemplate = MessageTemplate.and(
                            MessageTemplate.MatchConversationId("book-trade"),
                            MessageTemplate.MatchReplyWith(order.replyWith))
                    step = 3
                }
                3 -> {
                    val reply = myAgent.receive(messageTemplate)
                    if (reply != null) {
                        if (reply.performative == ACLMessage.INFORM) {
                            println("$targetBookTitle purchased :D")
                            println("Price : $bestPrice")
                            myAgent.doDelete()
                        }
                        step = 4
                    } else {
                        block()
                    }
                }
            }
        }

        override fun done(): Boolean {
            if (step == 2 && bestSeller == null) {
                println("Attempt failed: $targetBookTitle not available for sale")
            }
            return ((step == 2 && bestSeller == null) || step == 4)
        }
    }
}