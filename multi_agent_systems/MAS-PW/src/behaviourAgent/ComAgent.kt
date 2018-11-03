package behaviourAgent

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.Behaviour
import jade.lang.acl.ACLMessage
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel


class ComAgent : Agent() {

    lateinit var Etiq: JLabel
    lateinit var Etiq2: JLabel

    inner class MaFenetre : JFrame(), ActionListener {

        private val boutonCalcul: JButton

        init {
            title = "Agent Expéditeur"
            setSize(250, 100)
            val contenu = contentPane
            contenu.layout = FlowLayout()

            Etiq = JLabel("Hallo World! My name is $localName")
            contenu.add(Etiq)
            boutonCalcul = JButton("Kill")
            contenu.add(boutonCalcul)
            boutonCalcul.addActionListener(this)
            Etiq2 = JLabel("je vais envoyer un message dans 5 s.")
            contenu.add(Etiq2)
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === boutonCalcul) {
                doDelete()
                this.dispose()
            }
        }
    }

    override fun setup() {
        val fen = MaFenetre()
        fen.isVisible = true
        doWait(5000)
        addBehaviour(EnvoiMessage())
    }

    inner class EnvoiMessage : Behaviour() {

        private var finished = false

        override fun action() {
            val msg = ACLMessage(ACLMessage.INFORM)
            msg.content = "Bonjour je suis l'agent$localName"
            val receiver = AID("Bettaj", AID.ISLOCALNAME)
            msg.addReceiver(receiver)
            send(msg)
            finished = true
        }

        override fun done(): Boolean {
            if (finished) {
                Etiq.text = "Ca y est, le Message est envoyé !!"
                Etiq2.text = ""
            }
            return finished
        }
    }
}


