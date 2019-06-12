/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connections

import java.sql.*
import java.sql.DriverManager

class ConnectionDatabase {

    lateinit var connection: Connection

    fun connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver")
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)
            println("Was Connected")
        } catch (e: ClassNotFoundException) {
            System.err.println(e.message)
        } catch (e: SQLException) {
            System.err.println(e.message)
        }
    }

    companion object {
        private const val USERNAME = "root"
        private const val PASSWORD = ""
        private const val URL = "jdbc:mysql://127.0.0.1:3306/qdl"
    }
}
