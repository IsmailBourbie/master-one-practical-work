package dfAgents

import jade.core.AID
import jade.core.Agent
import jade.core.behaviours.TickerBehaviour
import jade.domain.DFService
import jade.domain.FIPAAgentManagement.DFAgentDescription
import jade.domain.FIPAAgentManagement.ServiceDescription
import jade.domain.FIPAException
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.concurrent.Semaphore
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField

class SearcherAgent : Agent() {

    private lateinit var label: JLabel
    private lateinit var textField: JTextField
    private lateinit var window: SearcherWindow
    private lateinit var serviceAgents: MutableList<AID>

    private val semaphore = Semaphore(0)

    internal inner class SearcherWindow : JFrame(), ActionListener {

        private val endButton: JButton
        private val calculateButton: JButton

        init {
            title = "Agent Client: $localName"
            setSize(250, 100)
            val container = contentPane
            container.layout = FlowLayout()
            label = JLabel("Type of service.")
            container.add(label)
            textField = JTextField(15)
            container.add(textField)
            calculateButton = JButton("Search")
            container.add(calculateButton)
            calculateButton.addActionListener(this)
            endButton = JButton("Kill")
            container.add(endButton)
            endButton.addActionListener(this)
            pack()
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === calculateButton)
                semaphore.release()
            else
                doDelete()
        }
    }

    override fun setup() {
        window = SearcherWindow()
        window.isVisible = true
        semaphore.acquire()

        addBehaviour(object : TickerBehaviour(this, 10000) {
            override fun onTick() {

                val template = DFAgentDescription()
                val sd = ServiceDescription()
                sd.type = textField.text
                template.addServices(sd)

                try {
                    val result = DFService.search(myAgent, template)
                    serviceAgents = mutableListOf()
                    println("----------------------- The agents proposing this service Are --------------------------")
                    for (i in result.indices) {
                        serviceAgents.add(result[i].name)
                        println(result[i].name)
                    }
                    if (result.isEmpty())
                        println("No agent propose this service at the moment.")
                } catch (fe: FIPAException) {
                    fe.printStackTrace()
                }
            }
        })
    }

    override fun takeDown() {
        window.dispose()
        println("Service-agent: " + aid.name + " terminating.")
    }
}
