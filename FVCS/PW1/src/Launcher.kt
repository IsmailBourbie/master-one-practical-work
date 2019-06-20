import model.PetriNetwork
import java.util.*

fun main() {

    val pn = PetriNetwork("Hello world")
    val t0 = pn.transition("t0")
    val t1 = pn.transition("t1")
    val t2 = pn.transition("t2")

    val p0 = pn.place("p0")
    val p1 = pn.place("p1")
    val p2 = pn.place("p2", 1)
    val p3 = pn.place("p3", 10, 10)

    val a0 = pn.arc("a1", t0, p0)
    val a1 = pn.arc("a2", p0, t1)
    val a2 = pn.arc("a3", t1, p1)
    val a3 = pn.arc("a4", p1, t2)
    val a4 = pn.arc("a5", t2, p2)
    val a5 = pn.arc("a6", p2, t1)
    val a6 = pn.arc("a8", t1, p3)
    val a7 = pn.arc("a7", p3, t0)

    val patient = Random(10).nextInt(10)
    pn.go(patient)
    println("Number of patient is $patient")
    println()
    println(pn.toString())
    println()
    println(pn.execute(11))
    println(pn.toString())
}