package mxcsBooks

import jade.core.Agent
import jade.core.behaviours.OneShotBehaviour


class BookSellerAgent : Agent() {

    // The catalogue of books for sale (maps the title of a book to its price)
    private var catalogue = mutableMapOf<String, Int>()
    // The GUI by means of which the user can add books in the catalogue

    private var myGui: BookSellerGui? = null

    override fun setup() {

        myGui = BookSellerGui(this)
        myGui?.showGui()

        //addBehaviour()
        //addBehaviour()
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
}