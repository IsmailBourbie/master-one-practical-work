package dfAgents

import jade.core.Agent
import jade.domain.DFService
import jade.domain.FIPAAgentManagement.DFAgentDescription
import jade.domain.FIPAAgentManagement.ServiceDescription
import jade.domain.FIPAException
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JTextField

class ServiceAgent : Agent() {

    lateinit var labelOne: JLabel
    lateinit var labelTwo: JLabel
    lateinit var textFieldOne: JTextField
    lateinit var textFieldTwo: JTextField
    var flag = false
    private lateinit var serviceWindow: ServiceWindow

    internal inner class ServiceWindow : JFrame(), ActionListener {

        private val endButton: JButton
        private val calculateButton: JButton

        init {
            title = "Agent Provider: $localName"
            setSize(250, 100)
            val container = contentPane
            container.layout = FlowLayout()

            labelOne = JLabel("Type of service.")
            container.add(labelOne)
            textFieldOne = JTextField(15)
            container.add(textFieldOne)
            labelTwo = JLabel("Name of service.")
            container.add(labelTwo)
            textFieldTwo = JTextField(15)
            container.add(textFieldTwo)
            calculateButton = JButton("Register")
            container.add(calculateButton)
            calculateButton.addActionListener(this)
            endButton = JButton("Deregister")
            container.add(endButton)
            endButton.addActionListener(this)
        }

        override fun actionPerformed(e: ActionEvent) {
            if (e.source === calculateButton)
                flag = true
            else
                doDelete()
        }
    }

    override fun setup() {
        serviceWindow = ServiceWindow()
        serviceWindow.isVisible = true
        val dfd = DFAgentDescription()
        dfd.name = aid
        val sd = ServiceDescription()

        while (!flag) {
        }
        sd.type = textFieldOne.text
        sd.name = textFieldTwo.text
        dfd.addServices(sd)
        try {
            DFService.register(this, dfd)
        } catch (fe: FIPAException) {
            fe.printStackTrace()
        }

        println("Registering Successfully !")
    }

    override fun takeDown() {
        try {
            DFService.deregister(this)
        } catch (fe: FIPAException) {
            fe.printStackTrace()
        }
        serviceWindow.dispose()
        println("Service-agent: " + aid.name + "  terminating.")
    }
}
