import model.PetriNetwork

fun main() {

    val pn = PetriNetwork("Hello world")
    val t1 = pn.transition("t1")
    val t2 = pn.transition("t2")

    val p1 = pn.place("p1", 1)
    val p2 = pn.place("p2")

    val a1 = pn.arc("a1", p1, t1)
    val a2 = pn.arc("a2", t1, p2)
    val a3 = pn.arc("a3", p2, t2)
    val a4 = pn.arc("a4", t2, p1)

    println(pn.toString())
}