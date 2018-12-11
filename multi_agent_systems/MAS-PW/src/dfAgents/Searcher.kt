package dfAgents

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.TickerBehaviour
import jade.domain.DFService
import jade.domain.FIPAAgentManagement.DFAgentDescription
import jade.domain.FIPAAgentManagement.ServiceDescription
import jade.domain.FIPAException

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class Searcher : Agent() {

    lateinit var Etiq: JLabel
    lateinit var Ts: JTextField
    internal var flag = false
    private lateinit var fen: MaFenetre
    // The list of agents that propose the service
    private lateinit var ServiceAgents: MutableList<AID>

    internal inner class MaFenetre : JFrame(), ActionListener {

        private val boutonFin: JButton
        private val boutonCalcul: JButton

        init {
            title = "Agent Client: $localName"
            setSize(250, 100)
            val contenu = contentPane
            contenu.layout = FlowLayout()
            Etiq = JLabel("Type of service.")
            contenu.add(Etiq)
            Ts = JTextField(15)
            contenu.add(Ts)
            boutonCalcul = JButton("Search")
            contenu.add(boutonCalcul)
            boutonCalcul.addActionListener(this)
            boutonFin = JButton("Kill")
            contenu.add(boutonFin)
            boutonFin.addActionListener(this)
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === boutonCalcul)
                flag = true
            else
                doDelete()
        }
    } // fin de la classe MaFenetre

    override fun setup() {
        fen = MaFenetre()
        fen.isVisible = true
        while (!flag) {
        }
        // Ajouter un TickerBehaviour pour chercher les agents proposant le service demandé toutes les 10 seconds
        addBehaviour(object : TickerBehaviour(this, 10000) {
            override fun onTick() {
                // Mise à jour de la liste des agents service
                val template = DFAgentDescription()
                val sd = ServiceDescription()
                sd.type = Ts.text
                template.addServices(sd)
                try {
                    val result = DFService.search(myAgent, template)
                    ServiceAgents = mutableListOf<AID>()
                    println("----------------------- Les agents proposant ce service sont------------------- -------")
                    for (i in result.indices) {
                        ServiceAgents.add(result[i].name)
                        println(result[i].name)
                    }
                    if (result.isEmpty())
                        println("No agent propose this service at the moment.")
                } catch (fe: FIPAException) {
                    fe.printStackTrace()
                }

            } // fin de la method onTick
        })
    }// fin de setup

    override fun takeDown() {
        // Close the GUI
        fen.dispose()
        // Printout a dismissal message
        println("Service-agent: " + aid.name + " terminating.")
    } // fin de takeDown
}// fin de la classe searcher
