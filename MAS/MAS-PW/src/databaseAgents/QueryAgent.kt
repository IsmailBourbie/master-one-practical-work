package databaseAgents

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
import javax.swing.JTextField


class QueryAgent : Agent() {

    lateinit var label: JLabel
    lateinit var textField: JTextField

    internal inner class Window : JFrame(), ActionListener {

        private val callButton: JButton

        init {
            title = "Query Agent"
            setSize(450, 150)
            val content = contentPane
            content.layout = FlowLayout()

            label = JLabel("Hello World! My name is $localName")
            content.add(label)
            callButton = JButton("Send")
            content.add(callButton)
            callButton.addActionListener(this)
            textField = JTextField(30)
            content.add(textField)
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === callButton) {
                doWake()
            }
        }
    }


    override fun setup() {
        val fen = Window()
        fen.isVisible = true
        addBehaviour(SendMessage())
    }

    inner class SendMessage : CyclicBehaviour() {

        override fun action() {
            var person: Person?
            val sendMessages = ACLMessage(ACLMessage.INFORM)
            var receivedMessage: ACLMessage?
            doWait()
            sendMessages.content = textField.text
            val receiver = AID("Ismail", AID.ISLOCALNAME)
            sendMessages.addReceiver(receiver)
            send(sendMessages)
            println("Query Sent: " + sendMessages.content)
            receivedMessage = receive()
            if (receivedMessage != null) {
                println(receivedMessage.content)
                var number = Integer.parseInt(receivedMessage.content)
                println("Number Of Row By QueryAgent is $number")
                if (number == 0)
                    println(" No Data Correspond to the Query")
                else
                    println(" ---------- The Data Received is -----------")
                while (number > 0) {

                    receivedMessage = receive()
                    if (receivedMessage != null) {

                        person = receivedMessage.contentObject as Person

                        println("Row: $number / $person")
                        number--
                    } else
                        block()
                }
                doDelete()
            } else
                block()
        }
    }

}
