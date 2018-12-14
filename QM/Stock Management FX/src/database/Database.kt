package database

import models.Client
import java.sql.DriverManager
import java.util.*

object Database {

    private val connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/gestion", "root", "")

    private val statement = connection.createStatement()

    var user: String = ""

    fun loginCheck(username: String, password: String): Boolean {

        statement.executeQuery("SELECT * FROM `login` WHERE `ModeDePasse` = '$password' AND `NomUtilisateur` = '$username'")

        return statement.resultSet.next()
    }

    fun addClient(society: String, civility: String, fName: String, lName: String, address: String, codePostal: String, ville: String, country: String, phone: String, mobile: String, fax: String, email: String, type: Int, livreSameAddress: Boolean, factureSameAddress: Boolean, exemptTVA: Boolean, SaisiPar: String, SaisiLe: Date, auteurModif: String, dateModif: Date, observation: String) {
        statement.execute("INSERT INTO `client` (`Societe`, `Civilite`, `NomClient`, `Prenom`, `Adresse`, `CodePostal`, `Ville`, `Pays`, `Telephone`, `Mobile`, `Fax`, `Email`, `Type`, `LivreMemeAdresse`, `FactureMemeAdresse`, `ExemptTVA`, `SaisiPar`, `SaisiLe`, `AuteurModif`, `DateModif`, `Observation`) VALUES ('$society', '$civility', '$fName', '$lName', '$address', '$codePostal', '$ville','$country', '$phone', '$mobile', '$fax', '$email', $type, $livreSameAddress, $factureSameAddress, $exemptTVA, '$SaisiPar', '$SaisiLe', '$auteurModif', '$dateModif', '$observation')")
    }

    fun deleteClient(numClient: Int) {
        statement.executeQuery("DELETE FROM `client` WHERE NumClient == $numClient")
    }

    fun getClient(numClient: Int): Client? {
        statement.executeQuery("SELECT *  FROM `client` WHERE NumClient == $numClient")

        val result = statement.resultSet
        var client: Client? = null
        if (result.next()) {
            client = Client(result.getInt(0), result.getString(1),
                    result.getString(2), result.getString(3),
                    result.getString(4), result.getString(5),
                    result.getString(6), result.getString(7),
                    result.getString(8), result.getString(9),
                    result.getString(10), result.getString(11),
                    result.getString(12), result.getInt(13),
                    result.getBoolean(14), result.getBoolean(15),
                    result.getBoolean(16), result.getString(17),
                    result.getDate(18), result.getString(19),
                    result.getDate(20), result.getString(21))
        }
        return client
    }
}
