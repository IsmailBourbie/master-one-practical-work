package model

import java.util.*

class PetriNetwork(name: String) : PetriObject(name) {

    internal var places: MutableList<Place> = ArrayList()
    internal var transitions: MutableList<Transition> = ArrayList()
    internal var arcs: MutableList<Arc> = ArrayList()
    internal val markage = mutableMapOf<Place, Int>()

    fun executeSeries(vararg transition: String) : Boolean{
        //TODO add the code
        return false
    }

    fun add(petriObject: PetriObject) {
        when (petriObject) {
            is Arc -> arcs.add(petriObject)
            is Place -> places.add(petriObject)
            is Transition -> transitions.add(petriObject)
        }
    }

    fun transition(name: String): Transition {
        val t = Transition(name)
        transitions.add(t)
        return t
    }

    fun place(name: String): Place {
        val p = Place(name)
        places.add(p)
        markage[p] = 0
        return p
    }

    fun place(name: String, initial: Int): Place {
        val p = Place(name, initial)
        markage[p] = initial
        places.add(p)
        return p
    }

    fun arc(name: String, p: Place, t: Transition): Arc {
        val arc = Arc(name, p, t)
        arcs.add(arc)
        return arc
    }

    fun arc(name: String, t: Transition, p: Place): Arc {
        val arc = Arc(name, t, p)
        arcs.add(arc)
        return arc
    }


    override fun toString(): String {
        val sb = StringBuilder("PetriNetwork")
        sb.append(super.toString()).append(nl)
        sb.append("---Transitions---").append(nl)
        for (t in transitions) {
            sb.append(t).append(nl)
        }
        sb.append("---Places---").append(nl)
        for (p in places) {
            sb.append(p).append(nl)
        }
        return sb.toString()
    }

    companion object {
        private val nl = "\n"
    }
}
