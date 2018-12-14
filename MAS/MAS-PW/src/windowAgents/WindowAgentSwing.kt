package windowAgents

import jade.core.Agent
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel


class WindowAgentSwing : Agent() {

    inner class Window : JFrame() {

        private val label: JLabel
        private val button: JButton

        init {
            title = "Agent Hello World"
            setSize(200, 100)

            val content = contentPane
            content.layout = FlowLayout()

            label = JLabel("Hello World! My name is $localName")
            content.add(label)
            button = JButton("Close")
            content.add(button)

            button.addActionListener {
                if (it.source === button)
                    doDelete()
                this.dispose()
            }
        }
    }

    override fun setup() {
        val window = Window()
        window.isVisible = true
    }
}


