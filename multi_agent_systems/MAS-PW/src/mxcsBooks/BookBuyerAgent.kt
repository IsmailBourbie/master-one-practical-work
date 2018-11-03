package mxcsBooks

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.TickerBehaviour

class BookBuyerAgent : Agent() {

    private lateinit var targetBookTitle: String

    private val sellerAgents = arrayListOf(AID("Seller1", AID.ISLOCALNAME),
            AID("Seller2", AID.ISLOCALNAME))

    override fun setup() {

        val args = arguments
        if (args != null && args.isNotEmpty()) {
            targetBookTitle = args[0] as String
            println("Title Specified was $targetBookTitle.")

            addBehaviour(object : TickerBehaviour(this, 60000) {
                override fun onTick() {
                    myAgent.addBehaviour(null)
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
}