package databseAgents

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.CyclicBehaviour
import jade.lang.acl.ACLMessage
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel

class DBAAgent : Agent() {

    private var labelOne: JLabel? = null
    private var labelTwo: JLabel? = null

    internal inner class Windows : JFrame(), ActionListener {

        private val buttonAction: JButton

        init {
            title = "Agent Bettaj"
            setBounds(100, 100, 450, 200)
            val contenu = contentPane
            contenu.layout = FlowLayout()

            labelOne = JLabel("Hello World! My name is $localName")
            labelTwo = JLabel("I'm Waiting For The Query")
            contenu.add(labelOne)
            contenu.add(labelTwo)
            buttonAction = JButton("Kill")
            contenu.add(buttonAction)
            buttonAction.addActionListener(this)
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === buttonAction) {
                doDelete()
                this.dispose()
            }
        }
    }

    override fun setup() {
        val fen = Windows()
        fen.isVisible = true
        addBehaviour(ReceiveMessage())
    }

    inner class ReceiveMessage : CyclicBehaviour() {
        override fun action() {
            doWait(5000)
            val msg: ACLMessage? = receive()
            if (msg != null) {

                labelOne!!.text = msg.content
                println("Received Query: ${msg.content}")
                Extraction.executeQuery(msg.content)

                val msg2 = ACLMessage(ACLMessage.INFORM)
                msg2.content = Extraction.count.toString()
                var receiver = AID("Ali", AID.ISLOCALNAME)
                msg2.addReceiver(receiver)
                send(msg2)

                for (person in Extraction.data) {
                    msg2.contentObject = person
                    receiver = AID("Ali", AID.ISLOCALNAME)
                    msg2.addReceiver(receiver)
                    send(msg2)
                    Extraction.count--
                }
                doDelete()
            } else
                block()
        }
    }
}




