package controllers

import database.Database
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import java.net.URL
import java.util.*

class FactureController : Initializable {

    @FXML
    lateinit var firstName: TextField
    lateinit var lastName: TextField
    lateinit var address: TextField
    lateinit var pays: TextField
    lateinit var city: TextField
    lateinit var email: TextField
    lateinit var fax: TextField
    lateinit var society: TextField
    lateinit var civility: TextField
    lateinit var codePostal: TextField
    lateinit var numTel: TextField
    lateinit var numMobile: TextField
    lateinit var type: TextField
    lateinit var observation: TextField
    lateinit var livreMmAddr: CheckBox
    lateinit var facMmAddr: CheckBox
    lateinit var exemptTva: CheckBox
    lateinit var newClient: Button
    lateinit var saveClient: Button
    lateinit var editClient: Button
    lateinit var deleteClient: Button


    @FXML
    fun addClient() {
        val society = society.text.trim()
        val civility = civility.text.trim()
        val firstNameVal = firstName.text.trim() // require
        val lastNameVal = lastName.text.trim() // require
        val address = address.text.trim()
        val codePostal = codePostal.text.trim()
        val city = city.text.trim()
        val pays = pays.text.trim()
        val numTel = numTel.text.trim()
        val numMobile = numMobile.text.trim()
        val fax = fax.text.trim()
        val email = email.text.trim()
        val type = type.text.trim().toInt()

        val livreMmAddr = livreMmAddr.isSelected
        val facMmAddr = facMmAddr.isSelected
        val exemptTva = exemptTva.isSelected
        val observation = observation.text.trim()

        if (!validateData(firstNameVal, lastNameVal)) return

        Database.addClient(society, civility, firstNameVal, lastNameVal, address, codePostal, city
                , pays, numTel, numMobile, fax, email, type, livreMmAddr, facMmAddr, exemptTva
                , Database.user, java.sql.Date(Date().time), Database.user, java.sql.Date(Date().time), observation)
    }

    private fun validateData(firstNameVal: String, lastNameVal: String): Boolean {
        if (firstNameVal.isEmpty()) {
            firstName.style = "-fx-border-color: #FF0000"
            firstName.requestFocus()
            return false
        }
        if (lastNameVal.isEmpty()) {
            lastName.style = "-fx-border-color: #FF0000"
            lastName.requestFocus()
            return false
        }
        return true
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }
}