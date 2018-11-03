package mxcsBooks

import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent


/**
 * @author Giovanni Caire - TILAB
 */
class BookSellerGui(private val myAgent: BookSellerAgent) : JFrame(myAgent.localName) {

    private val titleField: JTextField
    private val priceField: JTextField

    init {

        var p = JPanel()
        p.layout = GridLayout(2, 2)
        p.add(JLabel("Book title:"))
        titleField = JTextField(15)
        p.add(titleField)
        p.add(JLabel("Price:"))
        priceField = JTextField(15)
        p.add(priceField)
        contentPane.add(p, BorderLayout.CENTER)

        val addButton = JButton("Add")
        addButton.addActionListener {
            try {
                val title = titleField.text.trim { it <= ' ' }
                val price = priceField.text.trim { it <= ' ' }
                myAgent.updateCatalogue(title, Integer.parseInt(price))
                titleField.text = ""
                priceField.text = ""
            } catch (e: Exception) {
                JOptionPane.showMessageDialog(this@BookSellerGui, "Invalid values. " + e.message, "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
        p = JPanel()
        p.add(addButton)
        contentPane.add(p, BorderLayout.SOUTH)

        // Make the agent terminate when the user closes
        // the GUI using the button on the upper right corner
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                myAgent.doDelete()
            }
        })

        isResizable = false
    }

    fun showGui() {
        pack()
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val centerX = screenSize.getWidth().toInt() / 2
        val centerY = screenSize.getHeight().toInt() / 2
        setLocation(centerX - width / 2, centerY - height / 2)
        super.setVisible(true)
    }
}
