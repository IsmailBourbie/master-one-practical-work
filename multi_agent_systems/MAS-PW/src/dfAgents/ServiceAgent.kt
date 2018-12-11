package dfAgents

import jade.core.*
import java.awt.*
import java.awt.event.*
import javax.swing.*
import jade.domain.DFService
import jade.domain.FIPAException
import jade.domain.FIPAAgentManagement.DFAgentDescription
import jade.domain.FIPAAgentManagement.ServiceDescription

class ServiceAgent : Agent() {


    lateinit var Etiq: JLabel
    lateinit var Etiq2: JLabel
    lateinit var Ts: JTextField
    lateinit var Ns: JTextField
    var flag = false
    internal lateinit var fen: MaFenetre

    internal inner class MaFenetre : JFrame(), ActionListener {

        private val boutonFin: JButton
        private val boutonCalcul: JButton

        init {
            title = "Agent Prestataire: $localName"
            setSize(250, 100)
            val contenu = contentPane
            contenu.layout = FlowLayout()

            Etiq = JLabel("type de service")
            contenu.add(Etiq)
            Ts = JTextField(15)
            contenu.add(Ts)
            Etiq2 = JLabel("Nom de service")
            contenu.add(Etiq2)
            Ns = JTextField(15)
            contenu.add(Ns)
            boutonCalcul = JButton("Register")
            contenu.add(boutonCalcul)
            boutonCalcul.addActionListener(this)
            boutonFin = JButton("Deregister")
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
        val dfd = DFAgentDescription()
        dfd.name = aid
        val sd = ServiceDescription()
        //attendre l’appui sur le bouton
        while (!flag) {
        }
        sd.type = Ts.text
        sd.name = Ns.text
        dfd.addServices(sd)
        try {
            DFService.register(this, dfd)
        } catch (fe: FIPAException) {
            fe.printStackTrace()
        }

        println(" Enregistrement réalisé avec succès")
    } // fin de setup

    override fun takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this)
        } catch (fe: FIPAException) {
            fe.printStackTrace()
        }

        // Close the GUI
        fen.dispose()
        // Printout a dismissal message
        println("Service-agent: " + aid.name + "  terminating.")
    } // fin de takeDown
}// fin de Service
