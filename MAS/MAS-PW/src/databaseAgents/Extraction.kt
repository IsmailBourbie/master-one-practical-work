package databaseAgents

import java.sql.DriverManager

object Extraction {

    val data = mutableListOf<Person>()
    var count = 0

    fun executeQuery(query: String) {
        val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dba_dm",
                "root", "")

        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(query)

        while (resultSet.next()) {
            val name = resultSet.getString(2)
            val surname = resultSet.getString(3)
            val birthday = resultSet.getDate(4)

            data.add(Person(name, surname, birthday))
            count++
            println("The Value of Current row is $name | $surname | $birthday")
        }

        println("The Count is $count")
    }
}

