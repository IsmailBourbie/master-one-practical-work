package model

import java.util.ArrayList

class Transition(name: String) : PetriObject(name) {

    private val incoming = ArrayList<Arc>()
    private val outgoing = ArrayList<Arc>()

    val isNotConnected: Boolean
        get() = incoming.isEmpty() && outgoing.isEmpty()


    fun canMove(): Boolean {
        var canMove: Boolean

        canMove = !this.isNotConnected

        for (arc in incoming) {
            canMove = canMove and arc.canMove()
        }

        for (arc in outgoing) {
            canMove = canMove and arc.canMove()
        }
        return canMove
    }

    fun move() {
        for (arc in incoming) {
            arc.move()
        }

        for (arc in outgoing) {
            arc.move()
        }
    }

    fun addIncoming(arc: Arc) {
        this.incoming.add(arc)
    }

    fun addOutgoing(arc: Arc) {
        this.outgoing.add(arc)
    }

    override fun toString(): String {
        return super.toString() +
                (if (isNotConnected) " Is Not Connecred " else "") +
                if (canMove()) " Can Move " else ""
    }
}
