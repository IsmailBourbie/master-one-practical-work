package database

import java.sql.Connection
import java.sql.DriverManager

object Database {

    private val connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/gestion", "root", "")

    private val statement = connection.createStatement()

    fun loginCheck(username: String, password: String): Boolean {

        statement.executeQuery("SELECT * FROM `login` WHERE `ModeDePasse` = '$password' AND `NomUtilisateur` = '$username'")

        return statement.resultSet.next()
    }
}
