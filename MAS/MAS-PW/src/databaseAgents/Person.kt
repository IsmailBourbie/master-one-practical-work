package databaseAgents

import java.io.*
import java.text.SimpleDateFormat
import java.util.Date


data class Person(private val name: String,
                  private val surname: String,
                  private val birthday: Date) : Serializable {

    private fun dateToString(date: Any) = SimpleDateFormat("dd-mm-yyyy").format(date)
}
